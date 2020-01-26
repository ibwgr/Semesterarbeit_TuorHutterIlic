package TankWarsGame.GameLogic;

import TankWarsGame.GUI.MainWindow;
import TankWarsGame.GUI.StartScreen;

public class GameLogic extends Thread {
    private boolean gameRunning;
    public static GameSequencer gameSequencer;

    public GameLogic(){
        this.gameRunning = true;
    }


    public void run() {


        while (gameRunning){

            switch (gameSequencer){
                case OWN_TURN:
                    // do nothing
                    break;

                case CHECK_IF_WON_AFTER_OWN_TURN:
                    if (MainWindow.getOwnScore().intValue() == StartScreen.numberOfTanks) {
                        System.out.println("You win");
                        gameSequencer = GameSequencer.GAME_OWER;
                    }else{
                        gameSequencer = GameSequencer.SET_OPPONENT_TURN;
                    }

                    break;


                case SET_OPPONENT_TURN:
                     MainWindow.setOpponentTurn(true);
                     gameSequencer = GameSequencer.WAIT_OPPONENT_TURN_IS_DONE;
                     break;

                case WAIT_OPPONENT_TURN_IS_DONE:
                    // nothing to do, just wait
                    break;

                case CHECK_IF_LOST_AFTER_OPPONENT_TURN:
                    if (MainWindow.getOpponentScore().intValue() == StartScreen.numberOfTanks) {
                        System.out.println("You loose");
                        gameSequencer = GameSequencer.GAME_OWER;
                    }else{
                        gameSequencer = GameSequencer.OWN_TURN;
                    }

                    break;


                case GAME_OWER:
                    gameRunning = false;
                    break;
            }

        }

    }

}
