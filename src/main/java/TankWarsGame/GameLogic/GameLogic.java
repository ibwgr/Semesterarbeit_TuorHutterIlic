package TankWarsGame.GameLogic;

import TankWarsGame.GUI.MainWindow;
import TankWarsGame.GUI.StartScreen;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameLogic extends Thread {

    private boolean gameRunning;
    public static GameSequencer gameSequencer;


    /*********************************
     * Constructor
     * set gameRunning to true while creating a gameLogic Thread
     **/
    public GameLogic(){
        this.gameRunning = true;
    }


    /*********************************
     * GameLogic run() Method
     **/
    public void run() {

        while (gameRunning){

            switch (gameSequencer){

                case INIT:
                    // initial state at startup
                    break;


                case CHECK_IF_OPPONENT_IS_WAITING_FOR_CONNECTION:
                    // try to connect to opponent
                    try (
                            Socket opponentSocket = new Socket(MainWindow.opponentHostAddress, MainWindow.Port);
                            PrintWriter toServerOpponent = new PrintWriter(opponentSocket.getOutputStream(),true);
                    ) {
                        // if no error occurred, the connection to the opponent could be established, game starts with own turn
                        MainWindow.setOpponentTurn(false);
                        gameSequencer = GameSequencer.OWN_TURN;

                    } catch ( IOException e) {
                        // if an error occur, the connection to the opponent could not be established, we have to wait for connection of the opponent and game starts with opponent turn
                        gameSequencer = GameSequencer.WAIT_UNTIL_OPPONENT_CONNECTS;
                    }
                    break;


                case WAIT_UNTIL_OPPONENT_CONNECTS:
                    // wait until connection from opponent has been accepted
                    try (
                            ServerSocket opponentServer = new ServerSocket(MainWindow.Port);
                            Socket socket = opponentServer.accept();
                            BufferedReader fromServerOpponent = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ) {
                        // as soon connection has been accepted, start game with opponent turn
                        String text = fromServerOpponent.readLine();
                        gameSequencer = GameSequencer.SET_OPPONENT_TURN;

                    } catch ( IOException e) {
                        System.out.println("something went wrong, this shouldn't happen. --> gameSequencer case WAIT_UNTIL_OPPONENT_CONNECTS");
                        e.printStackTrace();
                    }
                    break;


                case OWN_TURN:
                    // nothing to do, just wait until own turn is done
                    break;


                case CHECK_IF_WON_AFTER_OWN_TURN:
                    // check if game is finished after own turn
                    if (MainWindow.getOwnScore().intValue() == StartScreen.numberOfTanks) {
                        // game is finished, stop gameSequencer Thread
                        System.out.println("you win");
                        gameSequencer = GameSequencer.GAME_OVER;
                    }else{
                        gameSequencer = GameSequencer.SET_OPPONENT_TURN;
                    }
                    break;


                case SET_OPPONENT_TURN:
                    // set opponent turn on main window
                    MainWindow.setOpponentTurn(true);
                    gameSequencer = GameSequencer.WAIT_OPPONENT_TURN_IS_DONE;
                    break;


                case WAIT_OPPONENT_TURN_IS_DONE:
                    // wait until opponent turn is not active in main window
                    if (!MainWindow.getOpponentPlayerTurn()) {
                        gameSequencer = GameSequencer.CHECK_IF_LOST_AFTER_OPPONENT_TURN;
                    }
                    break;


                case CHECK_IF_LOST_AFTER_OPPONENT_TURN:
                    // check if game is finished after opponent turn
                    if (MainWindow.getOpponentScore().intValue() == StartScreen.numberOfTanks) {
                        // game is finished, stop gameSequencer Thread
                        System.out.println("you lose");
                        gameSequencer = GameSequencer.GAME_OVER;
                    }else{
                        gameSequencer = GameSequencer.OWN_TURN;
                    }
                    break;


                case GAME_OVER:
                    // game is over, stop while loop which also ends this Thread
                    System.out.println("game is over");
                    gameRunning = false;
                    break;
            }

        }

    }

}
