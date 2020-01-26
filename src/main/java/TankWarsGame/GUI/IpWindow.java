package TankWarsGame.GUI;

import TankWarsGame.Field.Cell;
import TankWarsGame.Field.FieldOccupiedException;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpWindow extends Application {
    // main window
    Stage ipwindow;
    // scenes
    Scene scene;

    private Scene createScene(){
        /*******************************************************************************/
        // gridpane
        /*******************************************************************************/
        GridPane gridpaneIpSetting = new GridPane();
        //set Background
        gridpaneIpSetting .setStyle("-fx-background-image: url(https://i.ytimg.com/vi/sy2JQr_uGe0/maxresdefault.jpg); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
        //TODO Doesn't work yet and buttons/textfields function @rade
        //TODO make WINDOW NOT RESIZABLE!!!!!!!!!!!!!!!!!!!!@rade
        gridpaneIpSetting .setVgap(20);
        gridpaneIpSetting .setHgap(10);
        gridpaneIpSetting .setPadding(new Insets (50, 50, 50, 50));
        //DEBUG
        gridpaneIpSetting.setGridLinesVisible(true);

        /*******************************************************************************/
        // buttons, textfields
        /*******************************************************************************/
        TextField textfieldEnemyIp = new TextField();
        textfieldEnemyIp.setPrefSize(500, 40);
        //set pre Text in Textfield and style
        textfieldEnemyIp.setPromptText("Enter enemy IP Adress");

        TextField textfieldOwnIp = new TextField();
        textfieldOwnIp.setPrefSize(500, 40);
        //set pre Text in Textfield and style
        textfieldOwnIp.setPromptText("Your Own Adress will be displayed here");
        try {
            textfieldOwnIp.setText(String.valueOf(InetAddress.getLocalHost()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Label labelEnemyConnect = new Label("Connect with your Enemy:");
        labelEnemyConnect.setStyle("-fx-font-size: 21; -fx-text-fill: #000; -fx-font-family: Monospaced");
        Label labelOwn = new Label("Your IP Adress:");
        labelOwn.setStyle("-fx-font-size: 21; -fx-text-fill: #000; -fx-font-family: Monospaced");

        Button buttonConnect= new Button();
        buttonConnect.setPrefSize(150, 40);
        buttonConnect.setText("Connect");

        Button buttonIpCancel= new Button();
        buttonIpCancel.setPrefSize(150, 40);
        buttonIpCancel.setText("Cancel");

        /*******************************************************************************/
        //gridpane add children and place
        /*******************************************************************************/
        gridpaneIpSetting.getChildren().addAll(textfieldEnemyIp, textfieldOwnIp, labelEnemyConnect, labelOwn, buttonConnect, buttonIpCancel);

        GridPane.setConstraints(labelEnemyConnect, 0,0);
        GridPane.setHalignment(labelEnemyConnect, HPos.LEFT);

        GridPane.setConstraints(textfieldEnemyIp, 0, 1);
        GridPane.setHalignment(textfieldEnemyIp, HPos.CENTER);

        GridPane.setConstraints(labelOwn, 0,2);
        GridPane.setHalignment(labelOwn, HPos.LEFT);

        GridPane.setConstraints(textfieldOwnIp, 0, 3);
        GridPane.setHalignment(textfieldOwnIp, HPos.CENTER);

        GridPane.setConstraints(buttonConnect, 0,5);
        GridPane.setHalignment(buttonConnect, HPos.LEFT);

        GridPane.setConstraints(buttonIpCancel, 3,13);
        GridPane.setHalignment(buttonIpCancel, HPos.CENTER);


        scene = new Scene(gridpaneIpSetting, 800, 600);
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*******************************************************************************/
        // Main Window - show
        /*******************************************************************************/
        ipwindow = primaryStage;
        ipwindow = primaryStage;
        ipwindow.setScene(createScene());
        ipwindow.setTitle("TANK WARS");
        ipwindow.show();
    }

}






