package TankWarsGame.GUI;

import TankWarsGame.Field.Cell;
import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldOccupiedException;
import TankWarsGame.Player.Attack;
import TankWarsGame.Player.OutOfBoundsException;
import TankWarsGame.Player.OwnPlayer;
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


public class MainWindow extends Application {

    /*******************************************************************************/
    // general
    /*******************************************************************************/
    private boolean gameFinished;
    private static int numberOfTanksToPlace;
    private boolean startupDone = false;

    // check if all the tanks have been placed
    private IntegerProperty numberOfPlacedTanks = new SimpleIntegerProperty(0);
    private IntegerProperty finalTanksToPlace = new SimpleIntegerProperty(numberOfTanksToPlace);
    private BooleanProperty tanksPlaced = new SimpleBooleanProperty();

    // main window
    Stage window;
    // scenes
    Scene scene;

    // top reagion
    HBox topRegion = new HBox();

    // centre region
    HBox centreRegion = new HBox();
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
    Field ownMatchfield = new Field(2,2);
    Field opponentMatchfield = new Field(2,2);


    //create player
    OwnPlayer ownPlayer = new OwnPlayer("philipp",ownMatchfield );


    /*******************************************************************************/
    // create own cells
    /*******************************************************************************/
    private Cell createOwnCell(int horizontal, int vertical){
        Cell cell = new Cell();
        cell.setOnMouseClicked(event -> {
            if (!startupDone && cell.getFill() != Color.YELLOW) {
                try {
                    ownPlayer.field.placeTank(horizontal, vertical);
                } catch (FieldOccupiedException fo) {
                }
                numberOfPlacedTanks.set(numberOfPlacedTanks.get() + 1);
                cell.setFill(Color.YELLOW);
                cell.setStroke(Color.BLACK);
            }
        });
        return cell;
    }


    /*******************************************************************************/
    // create opponent cells
    /*******************************************************************************/
    private Cell createOpponentCell(int horizontal, int vertical){
        Cell cell = new Cell();
        cell.setOnMouseClicked(event -> {
            if (cell.getFill() != Color.BLACK && cell.getFill() != Color.RED) {
                Attack attack = new Attack(horizontal, vertical);
                try {
                    // TODO --> attack opponent field and not own field
                    attack = ownPlayer.attackField(attack);
                } catch (OutOfBoundsException oob) {
                }

                // TODO only for test reasons --> delete if not needed anymore
                System.out.println("H:" + horizontal + " V:" + vertical + " " + attack.getAttackStatus() );
                // TODO <-- END of deletable stuff


                switch (attack.getAttackStatus()) {
                    case SUCCESSFUL:
                        cell.setFill(Color.RED);
                        break;
                    case UNSUCCESSFUL:
                        cell.setFill(Color.BLACK);
                }
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
            Cell cellA1 = createOwnCell(0,0);
            Cell cellA2 = createOwnCell(1,0);
            Cell cellB1 = createOwnCell(0,1);
            Cell cellB2 = createOwnCell(1,1);
            ownField.add(cellA1, 0,0);
            ownField.add(cellA2, 1,0);
            ownField.add(cellB1, 0,1);
            ownField.add(cellB2, 1,1);

            ownField.setGridLinesVisible(true);

            // check iff all tanks have been placed
            tanksPlaced.bind(numberOfPlacedTanks.isEqualTo(finalTanksToPlace));
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

        }


        /*********************************
         * opponent field *
         * */
        if ( startupDone ) {

            // TODO --> create virtual opponent

            information.setText("start attacking your opponent");

            // TODO create column, row and cell in a for loop
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
            Cell OpponentCellA1 = createOpponentCell(0,0);
            Cell OpponentcellA2 = createOpponentCell(1,0);
            Cell OpponentcellB1 = createOpponentCell(0,1);
            Cell OpponentcellB2 = createOpponentCell(1,1);
            opponentField.add(OpponentCellA1, 0, 0);
            opponentField.add(OpponentcellA2, 1, 0);
            opponentField.add(OpponentcellB1, 0, 1);
            opponentField.add(OpponentcellB2, 1, 1);

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


        scene = new Scene(mainView, 500, 300);
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





