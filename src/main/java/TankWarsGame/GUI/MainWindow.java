package TankWarsGame.GUI;

import TankWarsGame.Field.Cell;
import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldOccupiedException;
import TankWarsGame.Player.Attack;
import TankWarsGame.Player.OutOfBoundsException;
import TankWarsGame.Player.OwnPlayer;
import TankWarsGame.Player.VirtualOpponent;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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

    // text fields
    Text information = new Text("START");
    Label labelOwnFiled = new Label("Eigenes Spielfeld");
    Label labelOpponentFiled = new Label("Spielfeld des Gegners");


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
    private Cell createOwnCell(int horizontal, int vertical){
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
            information.setText("place your tanks");

            // TODO create column, row and cell in a for loop
            // create columns
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPrefWidth(50);
            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPrefWidth(50);
            ownField.getColumnConstraints().addAll(column1, column2);

            // create rows
            RowConstraints A = new RowConstraints();
            A.setPrefHeight(50);
            RowConstraints B = new RowConstraints();
            B.setPrefHeight(50);
            ownField.getRowConstraints().addAll(A, B);

            // create cells
            for (int yColumn = 0; yColumn<fieldcount; yColumn++){
                for (int xRow = 0; xRow<fieldcount; xRow++) {
                    Cell cells = createOwnCell(yColumn, xRow);
                    ownField.add(cells, yColumn, xRow);
                }
            }

                // create cells
            /*Cell cellA1 = createOwnCell(0,0);
            Cell cellA2 = createOwnCell(1,0);
            Cell cellB1 = createOwnCell(0,1);
            Cell cellB2 = createOwnCell(1,1);
            ownField.add(cellA1, 0,0);
            ownField.add(cellA2, 1,0);
            ownField.add(cellB1, 0,1);
            ownField.add(cellB2, 1,1);

            */ownField.setGridLinesVisible(true);

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
             * TOP region Layout *
             * */
            topRegion.setPadding((new Insets(15, 12, 15, 12)));
            topRegion.setSpacing(10);
            topRegion.setStyle("-fx-background-color: #a89d32;");
            topRegion.getChildren().addAll(information);


            /*********************************
             * CENTRE reagion *
             * */
            leftCentreRegion.setPadding((new Insets(12, 15, 12, 15)));
            leftCentreRegion.setSpacing(10);
            leftCentreRegion.setStyle("-fx-background-color: white;");
            leftCentreRegion.getChildren().addAll(labelOwnFiled, ownField);   /* add grid pane fields to centre region */

            centreRegion.setPadding((new Insets(12, 15, 12, 15)));
            centreRegion.setSpacing(10);
            centreRegion.setStyle("-fx-background-color: white;");
            centreRegion.getChildren().addAll(leftCentreRegion, rightCentreRegion); /* add grid pane fields to centre region */

            /*********************************
             * BOTTOM reagion *
             * */
            Text forRadeIllic = new Text("Hallo Rade, du findest diesen Abschnitt im MainWindow -> Bottom region!!");
            bottomRegion.setPadding((new Insets (15,12,15,12)));
            bottomRegion.setSpacing(10);
            bottomRegion.setStyle("-fx-background-color: white;");
            bottomRegion.getChildren().addAll(forRadeIllic); /* add grid pane fields to centre region */

        }


        /*********************************
         * opponent field *
         * */
        if ( startupDone ) {

            // TODO --> create virtual opponent - Hutti: Done but at the beginning

            information.setText("start attacking your opponent");

            // TODO create column, row and cell in a for loop - Hutti Done for cells, why for columns and rows?
            // create columns
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPrefWidth(50);
            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPrefWidth(50);

            opponentField.getColumnConstraints().addAll(column1, column2);

            // create rows
            RowConstraints A = new RowConstraints();
            A.setPrefHeight(50);
            RowConstraints B = new RowConstraints();
            B.setPrefHeight(50);
            opponentField.getRowConstraints().addAll(A, B);

            // create cells
            for (int yColumn = 0; yColumn < fieldcount; yColumn++){
                for (int xRow = 0; xRow < fieldcount; xRow++) {
                    Cell cellsOpponent = createOpponentCell(yColumn, xRow);
                    opponentField.add(cellsOpponent, yColumn, xRow);
                }
            }

            /*Cell OpponentCellA1 = createOpponentCell(0,0);
            Cell OpponentcellA2 = createOpponentCell(1,0);
            Cell OpponentcellB1 = createOpponentCell(0,1);
            Cell OpponentcellB2 = createOpponentCell(1,1);
            opponentField.add(OpponentCellA1, 0, 0);
            opponentField.add(OpponentcellA2, 1, 0);
            opponentField.add(OpponentcellB1, 0, 1);
            opponentField.add(OpponentcellB2, 1, 1);
            */

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

            rightCentreRegion.getChildren().addAll(labelOpponentFiled, opponentField);

        }

        /*******************************************************************************/
        // main view - insert all regions
        /*******************************************************************************/
        BorderPane mainView = new BorderPane();
        mainView.setTop(topRegion);
        mainView.setCenter(centreRegion);
        mainView.setBottom(bottomRegion);

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





