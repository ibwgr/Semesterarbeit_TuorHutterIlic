package TankWarsGame.GUI;

import TankWarsGame.Field.Cell;
import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldOccupiedException;
import TankWarsGame.Field.FieldStatus;
import TankWarsGame.Player.Attack;
import TankWarsGame.Player.OutOfBoundsException;
import TankWarsGame.Player.OwnPlayer;
import TankWarsGame.Player.VirtualOpponent;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;


public class MainWindow extends Application {

    /*******************************************************************************/
    // general
    /*******************************************************************************/
    private boolean gameFinished;
    private static int numberOfTanksToPlace;
    private boolean startupDone = false;
    final int fieldcount = 10; // TODO Rade - Replace 10 with Fx-variable for field size
    AtomicInteger counter1 = new AtomicInteger(0);
    AtomicInteger counter2 = new AtomicInteger(0);

    // check if all the tanks have been placed
    private IntegerProperty numberOfPlacedTanks = new SimpleIntegerProperty(0);
//    private IntegerProperty finalTanksToPlace = new SimpleIntegerProperty(numberOfTanksToPlace);
    private BooleanProperty tanksPlaced = new SimpleBooleanProperty();

    // main window
    Stage window;

    // scenes
    Scene scene;

    // top reagion
    HBox topRegion = new HBox();

    // centre region
    HBox centreRegion = new HBox();
    HBox bottomRegion = new HBox();
    VBox rightCentreRegion = new VBox();
    VBox leftCentreRegion = new VBox();


    // own and opponent gridPane
    GridPane opponentField = new GridPane();
    GridPane ownField = new GridPane();
    GridPane gridpaneTop = new GridPane();
    GridPane gridpaneBottom = new GridPane();




    // set number of tanks
    public static void setNumberOfTanksToPlace(int numberOfTanksToPlace){
        MainWindow.numberOfTanksToPlace = numberOfTanksToPlace;
    }


    //set field size and create fields
    Field ownMatchfield = new Field(fieldcount,fieldcount);
    Field opponentMatchfield = new Field(fieldcount,fieldcount);


    //create player
    OwnPlayer ownPlayer = new OwnPlayer("philipp",ownMatchfield );
    VirtualOpponent bot = new VirtualOpponent("Bot", opponentMatchfield);

    /*******************************************************************************/
    // create own cells
    /*******************************************************************************/
    private Cell createOwnCell(int horizontal, int vertical) {
        Cell cell = new Cell();
        cell.setOnMouseClicked(event -> {
            if (!startupDone && cell.getFill() != Color.GREEN) {
                try {
                    ownPlayer.field.placeTank(horizontal, vertical);
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
    private Cell createOpponentCell(int horizontal, int vertical) {
        //AtomicInteger counter1 = new AtomicInteger(0);
        //AtomicInteger counter2 = new AtomicInteger(0);

        Cell cell = new Cell();
        cell.setOnMouseClicked(event -> {
            if (cell.getFill() != Color.BLACK && cell.getFill() != Color.RED) {
                Attack attack = new Attack(horizontal, vertical);
                try {
                    // TODO --> attack opponent field and not own field - Done Hutti
                    attack = bot.attackField(attack);
                } catch (OutOfBoundsException oob) {
                }

                // TODO only for test reasons --> delete if not needed anymore
                System.out.println("You have fired: H:" + horizontal + " V:" + vertical + " " + attack.getAttackStatus() );
                // TODO <-- END of deletable stuff

                switch (attack.getAttackStatus()) {
                    case SUCCESSFUL:
                        cell.setFill(Color.BLUE);
                        counter1.getAndIncrement();
                        break;
                    case UNSUCCESSFUL:
                        cell.setFill(Color.BLACK);
                }

                //Random Attack VirtualOpponent
                int[]virtualAttack = bot.placeRandom(fieldcount);
                Attack attackBot = new Attack(virtualAttack[0], virtualAttack[1]);

                try {
                    attackBot = ownPlayer.attackField(attackBot);
                } catch (OutOfBoundsException oob) {
                }

                ObservableList<Node> childrens = ownField.getChildren();
                for (Node node : childrens)
                    if (ownField.getRowIndex(node) == virtualAttack[0] && ownField.getColumnIndex(node) == virtualAttack[1]) {
                        Cell cell1 = new Cell();
                        cell1 = (Cell)node;
                        if ( cell1.getFill() == Color.GREEN ){
                            cell1.setFill(Color.RED);
                        }else {
                            cell1.setFill(Color.BLACK);
                        }

                        break;
                    }


                switch (attackBot.getAttackStatus()) {
                    case SUCCESSFUL:
                        //cell.setFill(Color.BLUE); TODO color display for attacks of virtual opponent
                        counter2.getAndIncrement();
                        break;
                    case UNSUCCESSFUL:
                        //cell.setFill(Color.BLACK);
                }
                // TODO only for test reasons --> delete if not needed anymore
                System.out.println("Bot has fired: H:" + virtualAttack[0] + " V:" + virtualAttack[1] + " " + attackBot.getAttackStatus() );
                // TODO <-- END of deletable stuff
            }
            System.out.println("Player: " + counter1.intValue() + "/" + StartScreen.numberOfTanks + " Bot: " + counter2.intValue() + "/" + StartScreen.numberOfTanks); //TODO only for test reasons
            if (counter1.intValue() == StartScreen.numberOfTanks){
                System.out.println("You win");
            }
            else if (counter2.intValue() == StartScreen.numberOfTanks){
                System.out.println("You loose");
            }
        });
        return cell;
    }

    /*******************************************************************************/
    // create scene
    /*******************************************************************************/
    private Scene createScene(){

        /*********************************
         * own field *
         * */
        if (!startupDone) {
//            information.setText("place your tanks"); TODO neues Label @mega
            // create cells
            for (int yColumn = 0; yColumn<fieldcount; yColumn++){
                for (int xRow = 0; xRow<fieldcount; xRow++) {
                    Cell cells = createOwnCell(yColumn, xRow);
                    ownField.add(cells, yColumn, xRow);
                }
            }

            ownField.setGridLinesVisible(true);

            // check iff all tanks have been placed
            //!!!!!! StartScreen.numberOfTanks ursprünglich finalTanksToPlace
            // TODO <-- Controll this change!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            tanksPlaced.bind(numberOfPlacedTanks.isEqualTo(StartScreen.numberOfTanks));
            tanksPlaced.addListener((observable, oldValue, newValue) -> {
                // Only if completed
                if (newValue)
                    startupDone = true;

                // TODO only for test reasons --> delete if not needed anymore
                System.out.println("A1 "+ ownPlayer.getFieldStatus(0,0));
                System.out.println("A2 "+ ownPlayer.getFieldStatus(1,0));
                System.out.println("B1 "+ ownPlayer.getFieldStatus(0,1));
                System.out.println("B2 "+ ownPlayer.getFieldStatus(1,1));
                // TODO <-- END of deletable stuff

                window.setScene(createScene());
            });

            /*********************************
             * CENTRE reagion *
             * */
            //Top Bottom and centre region have to be set inside the if (startup) to prevent from being called twice
            leftCentreRegion.setPadding((new Insets(12, 15, 12, 15)));
            leftCentreRegion.setSpacing(10);
            leftCentreRegion.setStyle("-fx-background-color: white;");
            leftCentreRegion.getChildren().addAll(ownField);   /* add grid pane fields to centre region */

            centreRegion.setPadding((new Insets(12, 15, 12, 15)));
            centreRegion.setSpacing(10);
            centreRegion.setStyle("-fx-background-color: white;");
            centreRegion.getChildren().addAll(leftCentreRegion, rightCentreRegion); /* add grid pane fields to centre region */

            /*********************************
             * TOP region Layout *
             * */

            Label labelTopOwnField = new Label(" Own Field ");
            labelTopOwnField.setPrefSize(500,40);
            labelTopOwnField.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 16; -fx-font-family: monospace");
            labelTopOwnField.setWrapText(true);

            Label labelTopEnemyField = new Label(" Enemy Field ");
            labelTopEnemyField.setPrefSize(500,40);
            labelTopEnemyField.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 16; -fx-font-family: monospace");
            labelTopEnemyField.setWrapText(true);

            // Set gridpaneBottom
            gridpaneTop.setPrefSize(400, 50);
            gridpaneTop.setVgap(20);
            gridpaneTop.setHgap(20);

            // add children
            gridpaneTop.getChildren().addAll(labelTopEnemyField, labelTopOwnField);

            // place the objects on the grid pane
            gridpaneTop.setConstraints(labelTopOwnField, 2, 0);
            gridpaneTop.setHalignment(labelTopOwnField, HPos.CENTER);

            gridpaneTop.setConstraints(labelTopEnemyField, 8, 0);
            gridpaneTop.setHalignment(labelTopEnemyField, HPos.CENTER);

            /*********************************
             * TOP region DEBUG *
             * */

            //Set gridpane lines true or false (debug)
            gridpaneTop.setGridLinesVisible(true);

            /*********************************
             * BOTTOM reagion *
             * */
            // create label
            Label labelBottomInfo = new Label("Introduction:" +
                    "1. Set your tanks on the left field. " + "2. Now you can attack the enemy field.");
            labelBottomInfo.setPrefSize(500,150);
            labelBottomInfo.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 16; -fx-font-family: monospace");
            labelBottomInfo.setWrapText(true);

            // create Button
            Button buttonCancelMain = new Button("Cancel");
            buttonCancelMain.setPrefSize(150,40);

            // Set gridpaneBottom
            gridpaneBottom.setPrefSize(100, 100);
            gridpaneBottom.setVgap(20);
            gridpaneBottom.setHgap(20);

            // add children
            gridpaneBottom.getChildren().addAll(labelBottomInfo, buttonCancelMain);

            // place the objects on the grid pane
            gridpaneBottom.setConstraints(labelBottomInfo, 0, 0);
            gridpaneBottom.setHalignment(labelBottomInfo, HPos.CENTER);

            gridpaneBottom.setConstraints(buttonCancelMain, 10, 0);
            gridpaneBottom.setHalignment(buttonCancelMain, HPos.CENTER);
        }

        /*********************************
         * Bottom region DEBUG *
         * */

        //Set gridpane lines true or false (debug)
        gridpaneBottom.setGridLinesVisible(true);

        /*********************************
         * opponent field *
         * */
        if ( startupDone ) {

            // TODO --> create virtual opponent - Hutti: Done but at the beginning

//            information.setText("start attacking your opponent"); TODO new label @rade


            // create cells
            for (int yColumn = 0; yColumn < fieldcount; yColumn++){
                for (int xRow = 0; xRow < fieldcount; xRow++) {
                    Cell cellsOpponent = createOpponentCell(yColumn, xRow);
                    opponentField.add(cellsOpponent, yColumn, xRow);
                }
            }

            //Place tanks randomly on opponent field
            for (int i = 0; i < StartScreen.numberOfTanks; i++) {
                int[] positionTanks = bot.placeRandom(fieldcount);
                try {
                    bot.field.placeTank(positionTanks[0], positionTanks[1]);
                } catch (FieldOccupiedException fo) {
                }
                // TODO only for test reasons, delete after
                //Cell cellsRandoms = createOpponentCell(positionTanks[0], positionTanks[1]);
                //opponentField.add(cellsRandoms, positionTanks[0], positionTanks[1]);
                //cellsRandoms.setFill(Color.RED);
                //cellsRandoms.setStroke(Color.ORANGE);
                // TODO end of deletable test code
            }

            opponentField.setGridLinesVisible(true);


            /*********************************
             * right CENTRE reagion *
             * */
            rightCentreRegion.setPadding((new Insets(12, 15, 12, 15)));
            rightCentreRegion.setSpacing(10);
            rightCentreRegion.setStyle("-fx-background-color: white;");

            rightCentreRegion.getChildren().addAll(opponentField);

        }

        /*******************************************************************************/
        // main view - insert all regions
        /*******************************************************************************/
        BorderPane mainView = new BorderPane();
        //region setting
        mainView.setCenter(centreRegion);
        mainView.setTop(gridpaneTop);
        mainView.setBottom(gridpaneBottom);

        scene = new Scene(mainView, 1200, 800);
        return scene;
    }




    @Override
    public void start(Stage primaryStage) throws Exception {
        /*******************************************************************************/
        // Main Window - show
        /*******************************************************************************/
        window = primaryStage;
        window = primaryStage;
        window.setScene(createScene());
        window.setTitle("TANK WARS");
        window.show();
        }

    }





