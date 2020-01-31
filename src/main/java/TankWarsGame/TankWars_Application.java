package TankWarsGame;

import TankWarsGame.GUI.MainWindow;

import static TankWarsGame.GUI.MusicPlayer.playMusic;
import static TankWarsGame.GUI.MusicPlayer.playMusicContinous;
import static javafx.application.Application.launch;
import TankWarsGame.GUI.StartScreen;

public class TankWars_Application {

    public static void main(String[] args) {
        /***********************************************/
        // launch window >>> DemoFrame
        MainWindow.setNumberOfTanksToPlace(StartScreen.numberOfTanks);
        playMusicContinous("./sounds/theme.wav");
        launch(StartScreen.class);

    }
}
