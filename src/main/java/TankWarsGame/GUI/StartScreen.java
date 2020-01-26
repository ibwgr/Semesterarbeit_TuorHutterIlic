package TankWarsGame.GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Objects;


public class StartScreen extends Application {

    // VARIABLE for MainWindow tanks, cells one side:
    public static int numberOfTanks;
    public static int numberOfCells;
    public static String modeSelected;

    // launch the application
    public void start(Stage s) {

/*******************************************************************************/
// creating the setup Starting page (Buttons, Boxes, Labels)
/*******************************************************************************/
        // set title for the stage
        s.setTitle("Tank Wars");

        // create TitleLabel "Tankwars"
        Label startGameScreenTitle = new Label("Tank Wars");
        startGameScreenTitle.setStyle("-fx-font-size: 50; -fx-text-fill: #FFF; -fx-font-family: Monospaced");

        // create Labels
        Label labelInstruction = new Label("Here is a small instruction for our TANK WARS. You can choose a number between 1 and 10 to set the number of tanks to place." +
                "There is also a textbox to set the side of the square field where you place your tanks. The minimum number of cells for the side of the square is 5 (25 Cells) and maximum is 10 (100 Cells). ");
        labelInstruction.setPrefSize(500,150);
        labelInstruction.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 16; -fx-font-family: monospace");
        labelInstruction.setWrapText(true);

        // create buttons
        Button buttonPlay = new Button("Play!");
        buttonPlay.setPrefSize(150,40);
        //make button invisible until the numbers(tanks and fieldsize) for the game are confirmed
        buttonPlay.setDisable(true);

        Button buttonConfirm = new Button("Confirm");
        buttonConfirm.setPrefSize(150,40);

        Button buttonCancel = new Button("Cancel");
        buttonCancel.setPrefSize(150,40);

        // create Combobox
//        ObservableList settingGame = FXCollections.observableArrayList(
//                "Multiplayer", "Singleplayer");
        ComboBox<String> comboBoxGameSetting = new ComboBox<>();
        comboBoxGameSetting.getItems().addAll("Singleplayer", "Multiplayer");
        comboBoxGameSetting.setPrefSize(500, 40);
        comboBoxGameSetting.setPromptText("Select a game mode:");

        // create Textfield number of tanks input
        TextField textfieldNumberOfTanks = new TextField();
        textfieldNumberOfTanks.setPrefSize(500, 40);
        //set pre Text in Textfield and style
        textfieldNumberOfTanks.setPromptText("With how many Tanks? 1-10");
        textfieldNumberOfTanks.setAlignment(Pos.CENTER);
        textfieldNumberOfTanks.setStyle("-fx-font-size: 16; -fx-text-fill: #000; -fx-font-family: Monospaced");

        //create textfield fieldSize input
        TextField textfieldSquareSide = new TextField();
        textfieldSquareSide.setPrefSize(500, 40);
        //set pre Text in Textfield and style
        textfieldSquareSide.setPromptText("Set the side of the field (Square): 5-10");
        textfieldSquareSide.setAlignment(Pos.CENTER);
        textfieldSquareSide.setStyle("-fx-font-size: 16; -fx-text-fill: #000; -fx-font-family: Monospaced");

        //create image //TODO NOT ACTIVE
        Image imageTank = new Image("https://img.favpng.com/17/12/5/pixel-art-minecraft-tank-png-favpng-iNcdJV2Zszcm0sKjSzSY8VjX4.jpg");
        ImageView imageViewTank = new ImageView();
        imageViewTank.setImage(imageTank);
        imageViewTank.setFitHeight(200);
        imageViewTank.setFitWidth(300);



/*******************************************************************************/
// creating the Gridpane for the Starting Page
/*******************************************************************************/
        // create Gridpane
        GridPane startGameGrid = new GridPane();
        startGameGrid.setAlignment(Pos.CENTER);

        // Setup for Gridpane
        startGameGrid.setPrefSize(1200, 700);
        startGameGrid.setVgap(20);
        startGameGrid.setHgap(20);

        //set Background image READY FOR WAR
        //TODO ok?
        BackgroundImage backgroundStart = new BackgroundImage(new Image("https://i.ytimg.com/vi/sy2JQr_uGe0/maxresdefault.jpg" ,1200,700,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        //set image on gridpane
       startGameGrid.setBackground(new Background(backgroundStart));


        // add to Gridpane in the Center of the startgamePane
        startGameGrid.getChildren().addAll(startGameScreenTitle, comboBoxGameSetting, buttonCancel, textfieldNumberOfTanks, buttonConfirm, labelInstruction, buttonPlay, textfieldSquareSide);

        //place the objects on the grid pane
        GridPane.setConstraints(startGameScreenTitle, 8, 0);
        GridPane.setHalignment(startGameScreenTitle, HPos.CENTER);

        GridPane.setConstraints(buttonPlay, 0,10);
        GridPane.setHalignment(buttonPlay, HPos.CENTER);

        GridPane.setConstraints(labelInstruction, 8,2);
        GridPane.setHalignment(labelInstruction, HPos.CENTER);

        GridPane.setConstraints(buttonConfirm, 8,6);
        GridPane.setHalignment(buttonConfirm, HPos.CENTER);

        GridPane.setConstraints(comboBoxGameSetting, 8,3);
        GridPane.setHalignment(comboBoxGameSetting, HPos.CENTER);

        GridPane.setConstraints(textfieldNumberOfTanks, 8,4);
        GridPane.setHalignment(textfieldNumberOfTanks, HPos.CENTER);

        GridPane.setConstraints(textfieldSquareSide, 8,5);
        GridPane.setHalignment(textfieldSquareSide, HPos.CENTER);

        GridPane.setConstraints(buttonCancel, 16, 10);
        GridPane.setHalignment(buttonCancel, HPos.CENTER);

/*******************************************************************************/
// DEBUG
/*******************************************************************************/

        //Set gridpane lines true or false (debug)
        startGameGrid.setGridLinesVisible(false);

/*******************************************************************************/
// Creating Buttonevents (Combobox, ButtonPlay, Confirm Fieldsize, Cancel)
/*******************************************************************************/
// you have to select a game mode
        textfieldNumberOfTanks.setDisable(true);
        textfieldSquareSide.setDisable(true);
        comboBoxGameSetting.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //modeselected is used for buttonPlay listener
                modeSelected = t1;
                if(Objects.equals(t1,"Singleplayer")){
                    //Enable number of tanks and fieldsize setting
                    textfieldNumberOfTanks.setDisable(false);
                    textfieldSquareSide.setDisable(false);
                    //set singleplayer instruction
                    labelInstruction.setText("In Singleplayer mode you can change the fieldsize and number of tanks.(size of the field 5-10, number of tanks 1-10)");
            //the number of Tanks confirm done with a label, other way: with do while and break;
                    buttonConfirm.setOnMouseClicked(mouseEvent -> {
                        label:
                        try {
                            textfieldNumberOfTanks.setDisable(false);
                            textfieldSquareSide.setDisable(false);
                            comboBoxGameSetting.setDisable(true);
                            //Setting the Max rules for how many tanks you can place
                            numberOfTanks = Integer.parseInt(textfieldNumberOfTanks.getText());
                            numberOfCells = Integer.parseInt(textfieldSquareSide.getText());
                            if (numberOfTanks <= 10 && numberOfTanks > 0 && numberOfCells <= 10 && numberOfCells >= 5) {
                                buttonPlay.setDisable(false);
                                //Set Change fieldsize to invisible. Only way to change fieldsize ReOpen Game
                //                    textfieldNumberOfTanks.setVisible(false);
                //                    textfieldSquareSide.setVisible(false); //TODO decide how to do it, rade
                                textfieldNumberOfTanks.setText(String.valueOf(numberOfTanks));
                                textfieldNumberOfTanks.setDisable(true);
                                textfieldSquareSide.setText(String.valueOf(numberOfCells));
                                textfieldSquareSide.setDisable(true);
                                buttonConfirm.setVisible(false);

                                labelInstruction.setStyle("-fx-border-color:red; -fx-background-color: lightgray; -fx-font-size: 25; -fx-font-family: monospace");
                                labelInstruction.setText("         !READY FOR WAR!        ");
                            }
                            // output to label when square side to high or to low
                            else if (numberOfCells > 10 || numberOfCells == 0 || numberOfCells < 5) {
                                labelInstruction.setText("HELP: Square side has to be between 5 - 10! Fieldsize smallest: 25Cells Largest: 100Cells");
                            }
                            //Number Of Tanks out of range text in label!
                            else if (numberOfTanks > 10 || numberOfTanks == 0) {
                                //TODO DEFINE OUTPUTLABEL--rade
                                System.out.println("the size of the field can be between 1 and 10!");
                                labelInstruction.setText("number of tanks has to be between 1-10!");
                            } else {
                                labelInstruction.setText("HELP: Must be a number! Number of tanks has to be between 1-10. Size of the square side has to be between 5 and 10.");
                            }
                        } catch (IllegalArgumentException e) {
                            labelInstruction.setText("HELP: Must be a number! Number of tanks has to be between 1-10. Size of the square side has to be between 5 and 10.");
                            break label;
                        }

                    });
                    //after confirmation of how many tanks you want to place the play button appears, with play the main window appears
                }else if (Objects.equals(t1, "Multiplayer")){
                    //disable function, with clear you clear the values that are set if the player sets first singleplayer->values->switch to multiplayer
                    textfieldNumberOfTanks.clear();
                    textfieldSquareSide.clear();

                    textfieldNumberOfTanks.setDisable(true);
                    textfieldSquareSide.setDisable(true);

                    labelInstruction.setText("In Multiplayer mode the fieldsize and number of tanks can't be changed.");

                        buttonConfirm.setOnMouseClicked(mouseEvent -> {
                            buttonConfirm.setVisible(false);
                            labelInstruction.setStyle("-fx-border-color:red; -fx-background-color: lightgray; -fx-font-size: 25; -fx-font-family: monospace");
                            labelInstruction.setText("         !READY FOR WAR!        ");
                            buttonPlay.setDisable(false);

                        });
                } else {
                    //If game mode not selected you cant do anything
                    textfieldNumberOfTanks.clear();
                    textfieldSquareSide.clear();
                    textfieldNumberOfTanks.setDisable(true);
                    textfieldSquareSide.setDisable(true);
                    buttonConfirm.setDisable(true);
                }
            }
        });


/*******************************************************************************/
// Play Button (Start Main Game)
/*******************************************************************************/
            buttonPlay.setOnMouseClicked((event) -> {
                if(Objects.equals(modeSelected,"Singleplayer")) {
                    MainWindow screen = new MainWindow();
                    try {
                        System.out.println("Single");
                        screen.start(s);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (Objects.equals(modeSelected, "Multiplayer")){
                    IpWindow screen = new IpWindow();
                    try {
                        screen.start(s);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                }
            });



/*******************************************************************************/
// Cancel Button (Restart)
/*******************************************************************************/
            buttonCancel.setOnMouseClicked(mouseEvent -> {
                try{
                    start(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


/*******************************************************************************/
// create the scene for StartScreen
/*******************************************************************************/

        // create a scene
        Scene tankWars = new Scene(startGameGrid, 1200, 700);
        // set the scene
        s.setScene(tankWars);
        s.show();
    }
}

