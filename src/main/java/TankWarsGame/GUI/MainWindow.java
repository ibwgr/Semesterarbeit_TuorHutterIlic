package TankWarsGame.GUI;

import TankWarsGame.Field.Cell;
import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldOccupiedException;
import TankWarsGame.Field.FieldStatus;
import TankWarsGame.Player.*;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

import static TankWarsGame.GUI.Music.playMusic;


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
    VirtualOpponent bot = new VirtualOpponent("Bot", opponentMatchfield, fieldcount);

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
                    playMusic("./sounds/shot.wav");
                } catch (OutOfBoundsException oob) {
                }

                // TODO only for test reasons --> delete if not needed anymore
                System.out.println("You have fired: H:" + horizontal + " V:" + vertical + " " + attack.getAttackStatus());
                // TODO <-- END of deletable stuff

                switch (attack.getAttackStatus()) {
                    case SUCCESSFUL:
                        cell.setFill(Color.BLUE);
                        counter1.getAndIncrement();
                        break;
                    case UNSUCCESSFUL:
                        cell.setFill(Color.BLACK);
                }

                    int[] virtualAttack = bot.getRandom();
                    Attack attackBot = new Attack(virtualAttack[0], virtualAttack[1]);

                    try {
                        attackBot = ownPlayer.attackField(attackBot);
                        playMusic("./sounds/shot.wav");
                    } catch (OutOfBoundsException oob) {
                    }

                    ObservableList<Node> childrens = ownField.getChildren();
                    for (Node node : childrens)
                        if (ownField.getRowIndex(node) == virtualAttack[1] && ownField.getColumnIndex(node) == virtualAttack[0]) {
                            Cell cell1 = new Cell();
                            cell1 = (Cell) node;
                            if (cell1.getFill() == Color.GREEN) {
                                cell1.setFill(Color.RED);
                            } else {
                                cell1.setFill(Color.BLACK);
                            }

                            break;
                        }

                    switch (attackBot.getAttackStatus()) {
                        case SUCCESSFUL:
                            //cell.setFill(Color.BLUE);
                            counter2.getAndIncrement();
                            break;
                        case UNSUCCESSFUL:
                            //cell.setFill(Color.BLACK);
                    }

                    // TODO only for test reasons --> delete if not needed anymore
                    System.out.println("Bot has fired: H:" + virtualAttack[0] + " V:" + virtualAttack[1] + " " + attackBot.getAttackStatus());
                    // TODO <-- END of deletable stuff
                }
                System.out.println("Player: " + counter1.intValue() + "/" + StartScreen.numberOfTanks + " Bot: " + counter2.intValue() + "/" + StartScreen.numberOfTanks); //TODO only for test reasons
                if (counter1.intValue() == StartScreen.numberOfTanks) {
                    System.out.println("You win");
                    playMusic("./sounds/winner.wav");
                } else if (counter2.intValue() == StartScreen.numberOfTanks) {
                    System.out.println("You loose");
                    playMusic("./sounds/looser.wav");
                }
           // }

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


            // create cells
            for (int yColumn = 0; yColumn < fieldcount; yColumn++){
                for (int xRow = 0; xRow < fieldcount; xRow++) {
                    Cell cellsOpponent = createOpponentCell(yColumn, xRow);
                    opponentField.add(cellsOpponent, yColumn, xRow);
                }
            }

            //Place tanks randomly on opponent field
            for (int i = 0; i < StartScreen.numberOfTanks; i++) {
                int[] positionTanks = bot.getRandom();
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





