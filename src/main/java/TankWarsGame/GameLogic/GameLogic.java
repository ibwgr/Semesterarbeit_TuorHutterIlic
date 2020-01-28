package TankWarsGame.GameLogic;

import TankWarsGame.GUI.MainWindow;
import TankWarsGame.GUI.StartScreen;
import TankWarsGame.Player.Attack;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameLogic extends Thread {
    private boolean gameRunning;
    public static GameSequencer gameSequencer;

    public GameLogic(){
        this.gameRunning = true;
    }


    public void run() {


        while (gameRunning){

            switch (gameSequencer){

                case INIT:
                    break;


                case CHECK_IF_OPPONENT_IS_WAITING_FOR_CONNECTION:
                    System.out.println("check if opponent is waiting for connection");
                    try (
                            Socket opponentSocket = new Socket(MainWindow.opponentHostAddress, MainWindow.opponentPort);
                            PrintWriter toServerOpponent = new PrintWriter(opponentSocket.getOutputStream(),true);
                    ) {
                        toServerOpponent.println("try connection");
                        System.out.println("connection to opponent established, change to own turn");
                        MainWindow.setOpponentTurn(false);
                        gameSequencer = GameSequencer.OWN_TURN;

                    } catch ( IOException e) {
                        System.out.println("connection to opponent not established, change to WAIT_UNTIL_OPPONENT_CONNECTS");
                        gameSequencer = GameSequencer.WAIT_UNTIL_OPPONENT_CONNECTS;
                    }
                    break;

                case WAIT_UNTIL_OPPONENT_CONNECTS:
                    try (
                            ServerSocket opponentServer = new ServerSocket(MainWindow.ownPort);
                            Socket socket = opponentServer.accept();                                  // Client Verbindung akzeptieren
                            BufferedReader fromServerOpponent = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ) {
                        String text = fromServerOpponent.readLine();
                        System.out.println("received connection from opponent");
                        gameSequencer = GameSequencer.SET_OPPONENT_TURN;

                    } catch ( IOException e) {
                        System.out.println("something went wrong, this shouldn't happen. --> gameSequencer case WAIT_UNTIL_OPPONENT_CONNECTS");
                    }
                    break;


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
                    if (!MainWindow.getOpponentPlayerTurn()) {
                        System.out.println("wait opponent turn is done");
                        gameSequencer = GameSequencer.CHECK_IF_LOST_AFTER_OPPONENT_TURN;
                    }
                    // nothing to do, just wait
                    break;

                case CHECK_IF_LOST_AFTER_OPPONENT_TURN:
                    MainWindow.setOpponentTurn(false);
                    System.out.println("check if lost after opponent turn");
                    if (MainWindow.getOpponentScore().intValue() == StartScreen.numberOfTanks) {
                        System.out.println("You loose");
                        gameSequencer = GameSequencer.GAME_OWER;
                    }else{
                        gameSequencer = GameSequencer.OWN_TURN;
                    }

                    break;


                case GAME_OWER:
                    System.out.println("game is over");
                    gameRunning = false;
                    break;
            }

        }

    }

}
