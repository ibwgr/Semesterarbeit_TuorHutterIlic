package TankWarsGame.GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;



public class StartScreen extends Application {

    // VARIABLE for MainWindow tanks, cells one side:
    public static int numberOfTanks;
    public static int numberOfCells;

    // launch the application
    public void start(Stage s) {

/*******************************************************************************/
// creating the setup Starting page (Buttons, Boxes, Labels)
/*******************************************************************************/
        // set title for the stage
        s.setTitle("Tank Wars");

        // create TitleLabel "Tankwars"
        Label startGameScreenTitle = new Label("Tank Wars");
        startGameScreenTitle.setFont(Font.font("Monospaced",50));
        startGameScreenTitle.setStyle("-fx-font-family: Monospaced");
        startGameScreenTitle.setStyle("-fx-text-fill: #FFF");

        // create Labels
        Label labelInstruction = new Label("Here is a small instruction for our TANK WARS. You can choose a number between 1 and 10 to set the number of tanks to place." +
                "There is also a textbox to set the side of the square field where you place your tanks. The minimum number of cells for the side of the square is 5 (25 Cells) and maximum is 10 (100 Cells). ");
        labelInstruction.setPrefSize(500,100);
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
        ComboBox comboBoxGameSetting = new ComboBox();
        comboBoxGameSetting.setPrefSize(150, 40);

        // create Textfield number of tanks input
        TextField textfieldNumberOfTanks = new TextField();
        textfieldNumberOfTanks.setPrefSize(150, 40);
        //set pre Text in Textfield
        textfieldNumberOfTanks.setPromptText("With how many Tanks? 1-10");

        //create textfield fieldSize input
        TextField textfieldSquareSide = new TextField();
        textfieldSquareSide.setPrefSize(150, 40);
        //set pre Text in Textfield
        textfieldSquareSide.setPromptText("Set the side of the field (Square): 5-10");

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
        BackgroundImage startScreenTank = new BackgroundImage(new Image("https://i.ytimg.com/vi/sy2JQr_uGe0/maxresdefault.jpg" ,1200,700,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        //set image on gridpane
       startGameGrid.setBackground(new Background(startScreenTank));


        // add to Gridpane in the Center of the startgamePane
        startGameGrid.getChildren().addAll(startGameScreenTitle, comboBoxGameSetting, buttonCancel, textfieldNumberOfTanks, buttonConfirm, labelInstruction, buttonPlay, textfieldSquareSide);

        //Die Objekte auf dem Gridpane verteilen
        GridPane.setConstraints(startGameScreenTitle, 8, 0);
        GridPane.setHalignment(startGameScreenTitle, HPos.CENTER);

        GridPane.setConstraints(buttonPlay, 0,12);
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

        //TODO NOT ACTIVE
//        GridPane.setConstraints(imageViewTank, 8,8);
//        GridPane.setHalignment(imageViewTank, HPos.CENTER);

        GridPane.setConstraints(buttonCancel, 16, 12);
        GridPane.setHalignment(buttonCancel, HPos.CENTER);

/*******************************************************************************/
// DEBUG
/*******************************************************************************/

        //Set gridpane lines true or false (debug)
        startGameGrid.setGridLinesVisible(false);

/*******************************************************************************/
// Creating Buttonevents (ButtonPlay, Confirm Fieldsize, Cancel)
/*******************************************************************************/

        //the number of Tanks confirm done with a label, other way: with do while and break;
        buttonConfirm.setOnMouseClicked(mouseEvent -> {
            //Setting the Max rules for how many tanks you can place
            label: try {
                numberOfTanks = Integer.parseInt(textfieldNumberOfTanks.getText());
                numberOfCells = Integer.parseInt(textfieldSquareSide.getText());
                if (numberOfTanks <= 10 && numberOfTanks > 0 && numberOfCells <=10 && numberOfCells >= 5) {
                    buttonPlay.setDisable(false);
                    //Set Change fieldsize to invisible. Only way to change fieldsize ReOpen Game
//                    textfieldNumberOfTanks.setVisible(false);
//                    textfieldSquareSide.setVisible(false); //TODO decide how to do it, rade
                    textfieldNumberOfTanks.setText(String.valueOf(numberOfTanks));
                    textfieldNumberOfTanks.setDisable(true);
                    textfieldSquareSide.setText(String.valueOf(numberOfCells));
                    textfieldSquareSide.setDisable(true);
                    buttonConfirm.setVisible(false);

                    labelInstruction.setStyle("-fx-border-color:deepskyblue; -fx-background-color: lightgray; -fx-font-size: 25; -fx-font-family: monospace");
                    labelInstruction.setText("         !READY FOR WAR!        ");
                    //set Background image READY FOR WAR
                    BackgroundImage startScreenReady = new BackgroundImage(new Image("https://i.ytimg.com/vi/sy2JQr_uGe0/maxresdefault.jpg" ,1200,700,false,true),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
                    //set image on gridpane
                    startGameGrid.setBackground(new Background(startScreenReady));
                }
                // output to label when square side to high or to low
                else if (numberOfCells > 10 || numberOfCells == 0 || numberOfCells < 5){
                    labelInstruction.setText("HELP: Square side has to be between 5 - 10! Fieldsize smallest: 25Cells Largest: 100Cells");
                }
                //Number Of Tanks out of range text in label!
                else if (numberOfTanks > 10 || numberOfTanks == 0){
                    //TODO DEFINE OUTPUTLABEL--rade
                    System.out.println("the size of the field can be between 1 and 10!");
                    labelInstruction.setText("number of tanks has to be between 1-10!");
                }
                else {
                    labelInstruction.setText("HELP: Must be a number! Number of tanks has to be between 1-10. Size of the square side has to be between 5 and 10.");
                }
            } catch (IllegalArgumentException e) {
                labelInstruction.setText("HELP: Must be a number! Number of tanks has to be between 1-10. Size of the square side has to be between 5 and 10.");
                break label;
            }

        });
        //after confirmation of how many tanks you want to place the play button appears, with play the main window appears


/*******************************************************************************/
// Play Button (Start Main Game)
/*******************************************************************************/
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
// Cancel Button (Restart)
/*******************************************************************************/
    buttonCancel.setOnMouseClicked(mouseEvent -> {
        try{
            start(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

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

