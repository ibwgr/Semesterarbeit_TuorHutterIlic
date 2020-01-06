package TankWarsGame.GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;


public class StartScreen extends Application {

    public static int numberOfTanks;

    // launch the application
    public void start(Stage s) {

/*******************************************************************************/
// creating the setup Starting page (Buttons, Boxes, Labels)
/*******************************************************************************/
        // set title for the stage
        s.setTitle("Tank Wars");

        // create TitleLabel "Tankwars" with White color
        Label startGameScreenTitle = new Label("Tank Wars");
        startGameScreenTitle.setFont(Font.font("Monospaced",50));
        startGameScreenTitle.setTextFill(Color.web("#000000"));

        // create Labels
        Label labelInstruction = new Label("Please write the number of tanks to Play with ");
        labelInstruction.setPrefSize(150,200);

        // create buttons
        Button buttonPlay = new Button("Play!");
        buttonPlay.setPrefSize(150,40);

        Button buttonNumber = new Button("Confirm");
        buttonPlay.setPrefSize(150,40);

        Button buttonCancel = new Button("Cancel");
        buttonCancel.setPrefSize(150,40);

        // create Combobox
        ComboBox comboBoxGameSetting = new ComboBox();
        comboBoxGameSetting.setPrefSize(150, 40);

        // create Textfield and read Int
        TextField textfieldNumberOfTanks = new TextField();
        textfieldNumberOfTanks.setPrefSize(150, 40);
        //set pre Text in Textfield
        textfieldNumberOfTanks.setPromptText("With how many Tanks? 1-10");


/*******************************************************************************/
// creating the Gridpane for the Starting Page
/*******************************************************************************/
        // create Gridpane
        GridPane startGameGrid = new GridPane();
        startGameGrid.setAlignment(Pos.CENTER);

        // Setup for Gridpane
        startGameGrid.setPrefSize(1200, 700);
        startGameGrid.setVgap(20);

        // add to Gridpane in the Center of the startgamePane
        startGameGrid.getChildren().addAll(startGameScreenTitle, comboBoxGameSetting, buttonCancel, textfieldNumberOfTanks, buttonNumber, labelInstruction);

        //Die Objekte auf dem Gridpane verteilen
        GridPane.setConstraints(startGameScreenTitle, 0, 0);
        GridPane.setHalignment(startGameScreenTitle, HPos.CENTER);

        GridPane.setConstraints(buttonPlay, 0,1);
        GridPane.setHalignment(buttonPlay, HPos.CENTER);
        //TODO not this label. this label already stads in the textbox
        GridPane.setConstraints(labelInstruction, 0,2);
        GridPane.setHalignment(labelInstruction, HPos.CENTER);

        GridPane.setConstraints(buttonNumber, 0,5);
        GridPane.setHalignment(buttonNumber, HPos.CENTER);

        GridPane.setConstraints(comboBoxGameSetting, 0,3);
        GridPane.setHalignment(comboBoxGameSetting, HPos.CENTER);

        GridPane.setConstraints(textfieldNumberOfTanks, 0,4);
        GridPane.setHalignment(textfieldNumberOfTanks, HPos.CENTER);

        GridPane.setConstraints(buttonCancel, 0, 13);
        GridPane.setHalignment(buttonCancel, HPos.CENTER);

/*******************************************************************************/
// set colors and style
/*******************************************************************************/

        // set Font to Monospaced
        startGameGrid.setStyle("-fx-font-family: Monospaced");

        startGameScreenTitle.setStyle("-fx-text-fill: #FFF");
        //set background color
        startGameGrid.setStyle("-fx-background-color: #000;");

        //Set gridpane lines true or false (debug)
        startGameGrid.setGridLinesVisible(true);

/*******************************************************************************/
// Creating Buttonevents (ButtonPlay, Confirm Fieldsize, Cancel)
/*******************************************************************************/

        //the number of Tanks confirm done with a label, other way: with do while and break;
        //TODO -> LabelOutput when not number!!!
        buttonNumber.setOnMouseClicked(mouseEvent -> {
            //Setting the Max rules for how many tanks you can place
            label: try {
                numberOfTanks = Integer.parseInt(textfieldNumberOfTanks.getText());
                if (numberOfTanks <= 10 && numberOfTanks > 0) {
                    startGameGrid.getChildren().add(buttonPlay);
                    //Set Change fieldsize to invisible. Only way to change fieldsize ReOpen Game
                    //TODO ADD LABEL that you have to reopen game for changing the fieldsize --rade
                    textfieldNumberOfTanks.setVisible(false);
                    buttonNumber.setVisible(false);
                }
                else if (numberOfTanks > 10 || numberOfTanks == 0){
                    //TODO DEFINE OUTPUTLABEL--rade
                    System.out.println("the size of the field can be between 1 and 10!");
                }
                else {
                    // TODO DEFINE OUPUTLABEL --rade
                    System.out.println("the size of the field can be between 1 and 10!");
                }
            } catch (IllegalArgumentException e) {
                    // TODO DEFINE OUTPUTLABEL --rade
                System.out.println("Maybe you wrote a letter instead of a number");
                break label;
            }

        });
        //after confirmation of how many tanks you want to place the play button appears, with play the main window appears

            buttonPlay.setOnMouseClicked((event) -> {
                MainWindow screen = new MainWindow();
                try {
                    screen.start(s);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

/*******************************************************************************/
// Combobox
/*******************************************************************************/


//TODO Create Combobox / FLAG for Single or Multiplayer mode


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

