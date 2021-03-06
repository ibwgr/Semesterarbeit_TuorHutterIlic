package TankWarsGame.GUI;

import TankWarsGame.Field.Cell;
import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldOccupiedException;
import TankWarsGame.GameLogic.GameLogic;
import TankWarsGame.GameLogic.GameSequencer;
import TankWarsGame.PlayerComponents.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

import static TankWarsGame.GUI.MusicPlayer.playMusic;


public class MainWindow extends Application {

    /*******************************************************************************/
    // general properties
    /*******************************************************************************/
    private static int numberOfTanksToPlace;                                    // number of tanks to place
    private static AtomicInteger ownGameScore = new AtomicInteger(0);
    private static AtomicInteger opponentGameScore = new AtomicInteger(0);
    public static int modeSelect;                                               // 0 = singleplayer , 1 = multiplayer Player
    public static String opponentHostAddress;                                   //server address
    public static int port = 63211;;                                            // Port-number
    public static String string;



    private boolean startupDone = false;

    final int fieldcount = StartScreen.numberOfCells;


    // BooleanProperty to check if all the tanks have been placed
    private IntegerProperty numberOfPlacedTanks = new SimpleIntegerProperty(0);
    private BooleanProperty tanksPlaced = new SimpleBooleanProperty();

    // own and opponent gridPane
    private GridPane opponentField = new GridPane();
    private GridPane ownField = new GridPane();

    //set field size and create fields
    Field ownMatchfield = new Field(fieldcount,fieldcount);
    Field opponentMatchfield = new Field(fieldcount,fieldcount);

    //create player
    OwnPlayer ownPlayer = new OwnPlayer("Master",ownMatchfield );
    Player opponentPlayer;
    //VirtualOpponent bot = new VirtualOpponent("Bot", opponentMatchfield, fieldcount);

    // Define a variable to store the opponentPlayerTurn property
    private static BooleanProperty opponentPlayerTurn = new SimpleBooleanProperty();
    public static final boolean getOpponentPlayerTurn(){return opponentPlayerTurn.get();}
    public static final void setOpponentTurn(boolean value){opponentPlayerTurn.set(value);}
    // main window
    Stage window;


    /*******************************************************************************/
    // general properties getter and setter methods
    /*******************************************************************************/
    public static AtomicInteger getOwnScore(){
        return ownGameScore;
    }
    public static AtomicInteger getOpponentScore(){
        return opponentGameScore;
    }

    // set number of tanks
    public static void setNumberOfTanksToPlace(int numberOfTanksToPlace){
        MainWindow.numberOfTanksToPlace = numberOfTanksToPlace;
    }

    public static int getNumberOfTanksToPlace(){
        return MainWindow.numberOfTanksToPlace;
    }

    /*******************************************************************************/
    // create own cells
    /*******************************************************************************/
    private Cell createOwnCell(int horizontal, int vertical) {
        Cell cell = new Cell();
        cell.setOnMouseClicked(event -> {
            if (!startupDone && cell.getFill() != Color.GREEN) {
                try {
                    ownPlayer.field.placeTank(horizontal, vertical);
                    playMusic("./sounds/place.wav");
                } catch (FieldOccupiedException fo) {
                }
                numberOfPlacedTanks.set(numberOfPlacedTanks.get() + 1);
                cell.setFill(Color.GREEN);
                cell.setStroke(Color.ORANGE);
            }
        });
        return cell;
    }


    /*******************************************************************************/
    // create opponent cells
    /*******************************************************************************/
    private Cell createOpponentCell(int horizontal, int vertical, Label label) {

        Cell cell = new Cell();
        cell.setOnMouseClicked(event -> {
            if (        (cell.getFill() != Color.BLACK && cell.getFill() != Color.RED)
                    &&  !opponentPlayerTurn.get()
                    &&  opponentField.isGridLinesVisible()
                    &&  ( GameLogic.gameSequencer == GameSequencer.OWN_TURN )) {
                Attack attack = new Attack(horizontal, vertical);
                // attack opponent
                try {
                    attack = opponentPlayer.attackField(attack);
                    playMusic("./sounds/shot.wav");
                } catch (OutOfBoundsException oob) {
                }


                switch (attack.getAttackStatus()) {
                    case SUCCESSFUL:
                        cell.setFill(Color.BLUE);
                        ownGameScore.getAndIncrement();
                        label.setText(String.valueOf(ownGameScore.intValue()) + (" / ") + StartScreen.numberOfTanks);
                        break;
                    case UNSUCCESSFUL:
                        cell.setFill(Color.BLACK);
                }
                if (ownGameScore.intValue() == StartScreen.numberOfTanks) {
                    playMusic("./sounds/winner.wav");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("You WIN!");
                    alert.showAndWait();
                    window.close();
                }

                GameLogic.gameSequencer = GameSequencer.CHECK_IF_WON_AFTER_OWN_TURN;
            }
        });
        return cell;
    }



    /*******************************************************************************/
    // create scene
    /*******************************************************************************/
    private Scene createScene(){
        /*********************************
         * CENTRE reagion *
         * */
        VBox leftCentreRegion = new VBox();
        leftCentreRegion.setPadding((new Insets(12, 15, 12, 15)));
        leftCentreRegion.setSpacing(10);
        leftCentreRegion.getChildren().addAll(ownField);   /* add grid pane fields to centre region */

        VBox rightCentreRegion = new VBox();
        rightCentreRegion.setPadding((new Insets(12, 15, 12, 100)));
        rightCentreRegion.setSpacing(10);
        rightCentreRegion.getChildren().addAll(opponentField);

        //set center region
        HBox centreRegion = new HBox();
        centreRegion.setPadding((new Insets(12, 15, 12, 15)));
        centreRegion.setSpacing(10);
        centreRegion.setAlignment(Pos.CENTER);
        centreRegion.getChildren().addAll(leftCentreRegion, rightCentreRegion); /* add grid pane fields to centre region */


        /*********************************
         * TOP region Layout *
         * */
        // create label
        Label labelTopOwnField = new Label(" Own Field ");
        labelTopOwnField.setPrefSize(500,40);
        labelTopOwnField.setAlignment(Pos.CENTER);
        labelTopOwnField.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 16; -fx-font-family: monospace");
        labelTopOwnField.setWrapText(true);

        Label labelTopEnemyField = new Label(" Enemy Field ");
        labelTopEnemyField.setPrefSize(500,40);
        labelTopEnemyField.setAlignment(Pos.CENTER);
        labelTopEnemyField.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 16; -fx-font-family: monospace");
        labelTopEnemyField.setWrapText(true);

        // Set gridpaneTop
        GridPane gridPaneTop = new GridPane();
        gridPaneTop.setAlignment(Pos.CENTER);
        gridPaneTop.setPrefSize(400, 50);
        gridPaneTop.setVgap(20);
        gridPaneTop.setHgap(20);

        // add children
        gridPaneTop.getChildren().addAll(labelTopEnemyField, labelTopOwnField);

        // place the objects on the grid pane
        gridPaneTop.setConstraints(labelTopOwnField, 0, 0);
        gridPaneTop.setHalignment(labelTopOwnField, HPos.CENTER);

        gridPaneTop.setConstraints(labelTopEnemyField, 8, 0);
        gridPaneTop.setHalignment(labelTopEnemyField, HPos.CENTER);

        //Set gridpane lines true or false (debug)
        gridPaneTop.setGridLinesVisible(false);


        /*********************************
         * BOTTOM reagion *
         * */
        // create label
        Label labelBottomInfo = new Label("Introduction:" + "1. Set your tanks on the left field. " + "2. Now you can attack the enemy field.");
        labelBottomInfo.setPrefSize(500,120);
        labelBottomInfo.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 16; -fx-font-family: monospace");
        labelBottomInfo.setWrapText(true);

        // create Button
        Button buttonCancelMain = new Button("Cancel");
        buttonCancelMain.setPrefSize(150,40);
        // invisible Button
        Button buttonInvisible = new Button("");
        buttonInvisible.setPrefSize(150,40);
        buttonInvisible.setVisible(false);

        // create textfield
        Label labelHitCounterOwn = new Label("Your Tanks destroyed");
        labelHitCounterOwn.setPrefSize(300, 40);
        //set pre Text in Textfield and style
        labelHitCounterOwn.setAlignment(Pos.CENTER);
        labelHitCounterOwn.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 14; -fx-font-family: monospace");

        Label labelHitCounterEnemy = new Label("Enemy tanks destroyed");
        labelHitCounterEnemy.setPrefSize(300, 40);
        //set pre Text in Textfield and style
        labelHitCounterEnemy.setAlignment(Pos.CENTER);
        labelHitCounterEnemy.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 14; -fx-font-family: monospace");

        // Set gridpaneBottom
        GridPane gridpaneBottom = new GridPane();
        gridpaneBottom.setAlignment(Pos.CENTER);
        gridpaneBottom.setPrefSize(100, 200);
        gridpaneBottom.setHgap(20);
        gridpaneBottom.setPadding(new Insets(20));

        // add children
        gridpaneBottom.getChildren().addAll(labelBottomInfo, buttonCancelMain, labelHitCounterOwn, labelHitCounterEnemy);

        // place the objects on the grid pane
        gridpaneBottom.setConstraints(labelBottomInfo, 9, 0);
        gridpaneBottom.setHalignment(labelBottomInfo, HPos.CENTER);

        gridpaneBottom.setConstraints(buttonCancelMain, 17, 1);
        gridpaneBottom.setHalignment(buttonCancelMain, HPos.RIGHT);

        gridpaneBottom.setConstraints(labelHitCounterOwn, 0, 0);
        gridpaneBottom.setValignment(labelHitCounterOwn, VPos.TOP);
        gridpaneBottom.setHalignment(labelHitCounterEnemy, HPos.CENTER);

        gridpaneBottom.setConstraints(labelHitCounterEnemy, 17, 0);
        gridpaneBottom.setValignment(labelHitCounterEnemy, VPos.TOP);
        gridpaneBottom.setHalignment(labelHitCounterEnemy, HPos.CENTER);

        //Set gridpane lines true or false (debug)
        gridpaneBottom.setGridLinesVisible(false);
        /*********************************
         * own field *
         * */
//       Informationoutput
        labelBottomInfo.setText("Place your tanks! " + "Place " + StartScreen.numberOfTanks + " tanks.");
        labelBottomInfo.setAlignment(Pos.CENTER);


        // create cells
        for (int yColumn = 0; yColumn<fieldcount; yColumn++){
            for (int xRow = 0; xRow<fieldcount; xRow++) {
                Cell cells = createOwnCell(yColumn, xRow);
                ownField.add(cells, yColumn, xRow);
            }
        }

        ownField.setStyle("-fx-background-color: white;");
        ownField.setGridLinesVisible(true);


        /*********************************
         * opponent field *
         * */
        // create cells
        for (int yColumn = 0; yColumn < fieldcount; yColumn++){
            for (int xRow = 0; xRow < fieldcount; xRow++) {
                Cell cellsOpponent = createOpponentCell(yColumn, xRow, labelHitCounterEnemy);
                opponentField.add(cellsOpponent, yColumn, xRow);
            }
        }


        /*********************************
         * check iff all own tanks have been placed
         * show opponent field as soon all own tanks have been placed
         * */
        tanksPlaced.bind(numberOfPlacedTanks.isEqualTo(StartScreen.numberOfTanks));
        tanksPlaced.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                startupDone = true;
                //      Informationoutput
                labelBottomInfo.setText("Now, you can attack the Enemy field on the right side!");
                labelBottomInfo.setAlignment(Pos.CENTER);

                // single player
                if ( modeSelect == 0 ) {
                    GameLogic.gameSequencer = GameSequencer.OWN_TURN;
                    opponentPlayerTurn.set(false);
                }
                // multi player
                else {
                    GameLogic.gameSequencer = GameSequencer.CHECK_IF_OPPONENT_IS_WAITING_FOR_CONNECTION;
                    opponentPlayerTurn.set(false);
                }

            }
            opponentField.setStyle("-fx-background-color: white;");
            opponentField.setGridLinesVisible(true);
        });


        /*********************************
         * opponent turn
         * */
        opponentPlayerTurn.addListener((observable, oldValue, newValue) -> {
            if ( getOpponentPlayerTurn()){
//                    int[] virtualAttack = opponentPlayer.getRandom();
//                    Attack attackBot = new Attack(virtualAttack[0], virtualAttack[1]);

                    Attack attackBot = opponentPlayer.getAttack();

                    try {
                        attackBot = ownPlayer.attackField(attackBot);
                        playMusic("./sounds/shot.wav");
                    } catch (OutOfBoundsException oob) {
                    }

                ObservableList<Node> childrens = ownField.getChildren();
                for (Node node : childrens)
                    if ((ownField.getRowIndex(node) ==  attackBot.getVerticalPosition()) && (ownField.getColumnIndex(node) == attackBot.getHorizontalPosition())) {
                        Cell cell1 = new Cell();
                        cell1 = (Cell) node;
                        if (cell1.getFill() == Color.GREEN) {
                            cell1.setFill(Color.RED);
                            Platform.runLater(()->{
                                labelHitCounterOwn.setText(String.valueOf(opponentGameScore.intValue()) + (" / ") + StartScreen.numberOfTanks);
                            });

                        } else {
                            cell1.setFill(Color.BLACK);
                        }

                        break;
                    }

                switch (attackBot.getAttackStatus()) {
                    case SUCCESSFUL:
                        opponentGameScore.getAndIncrement();
                        break;
                    case UNSUCCESSFUL:
                        // not successful do not increment game score of opponent
                }
                if (opponentGameScore.intValue() == StartScreen.numberOfTanks) {
                    System.out.println("You loose");
                    //Platformrun later sonst gibt es thread exception
                    Platform.runLater(()->{
                        playMusic("./sounds/looser.wav");
                        Alert alertLose = new Alert(Alert.AlertType.INFORMATION);
                        alertLose.setTitle("Information Dialog");
                        alertLose.setHeaderText(null);
                        alertLose.setContentText("You LOSE!");
                        alertLose.showAndWait();
                        window.close();

                    });
                }
                opponentPlayerTurn.set(false);
            }
        });




        /*********************************
         * Cancel Button *
         * */
        buttonCancelMain.setOnMouseClicked(mouseEvent -> {

            GameLogic.gameSequencer = GameSequencer.GAME_OVER;      // stop gameLogic Thread
            ownField = null;
            ownGameScore.set(0);
            opponentGameScore.set(0);
            startupDone = false;
            opponentPlayerTurn = new SimpleBooleanProperty();

            StartScreen startScreen = new StartScreen();

            try{
                startScreen.start(window);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        /*******************************************************************************/
        // main view - insert all regions
        /*******************************************************************************/
        BorderPane mainView = new BorderPane();
        //set Background
        BackgroundImage backgroundMain = new BackgroundImage(new Image("File:images/backgroundWar.jpg" ,1200,800,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        //set image on gridpane
        mainView.setBackground(new Background(backgroundMain));

        //region setting
        mainView.setCenter(centreRegion);
        mainView.setTop(gridPaneTop);
        mainView.setBottom(gridpaneBottom);
        mainView.getBottom().prefHeight(250);

        // start game logic thread
        GameLogic game = new GameLogic();
        game.setDaemon(true);
        game.start();

        Scene scene;
        scene = new Scene(mainView, 1200, 800);
        return scene;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        GameLogic.gameSequencer = GameSequencer.INIT;

        // single player
        if ( modeSelect == 0 ) {
            opponentPlayer = new VirtualOpponent("Bot", opponentMatchfield, fieldcount);

        }
        // multi player
        else{
            opponentPlayer = new RealOpponent("gegner", opponentMatchfield, ownPlayer, opponentHostAddress, port);
        }

        window = primaryStage;
        window.setScene(createScene());
        window.setTitle("TANK WARS");
        window.setResizable(false);
        window.show();
        }

    }
