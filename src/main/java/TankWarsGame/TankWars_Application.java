package TankWarsGame;

import TankWarsGame.GUI.MainWindow;

import static TankWarsGame.GUI.MusicPlayer.playMusicContinous;
import static javafx.application.Application.launch;
import TankWarsGame.GUI.StartScreen;

public class TankWars_Application {

    public static void main(String[] args) {
        /***********************************************/
        // launch window >>> DemoFrame
        System.out.println("start game");

        MainWindow.setNumberOfTanksToPlace(StartScreen.numberOfTanks);
//      MainWindow.setNumberOfTanksToPlace(2);--->alt
        playMusicContinous("./sounds/theme.wav");
        launch(StartScreen.class);


    }
}
