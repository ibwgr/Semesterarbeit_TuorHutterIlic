package TankWarsGame;

import TankWarsGame.GUI.MainWindow;
import static javafx.application.Application.launch;

public class TankWars_Application {
    public static void main(String[] args) {

        MainWindow.setNumberOfTanksToPlace(2);


        /***********************************************/
        // launch window >>> DemoFrame
        System.out.println("start game");
        launch(MainWindow.class);

    }
}
