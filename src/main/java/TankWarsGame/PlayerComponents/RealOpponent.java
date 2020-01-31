package TankWarsGame.PlayerComponents;

import TankWarsGame.Field.Field;
import TankWarsGame.GUI.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class RealOpponent extends Player {
    private Player ownPlayer;
    private String opponentHostAddress;
    private int port;

    public RealOpponent(String name, Field opponentField, Player ownPlayer, String opponentHostAddress, int port){
        super(name, opponentField);
        this.ownPlayer = ownPlayer;
        this.opponentHostAddress = opponentHostAddress;
        this.port = port;
    }

    @Override
    public Attack attackField(Attack attack) throws OutOfBoundsException {
        // check if position is within field boundaries - throw out of bounds exception if not inside of the boundaries
        this.checkIfInBounds(attack.getHorizontalPosition(), attack.getVerticalPosition());

        Attack returnAttack = null;
        try (
                Socket opponentSocket = new Socket(opponentHostAddress, port);
                ObjectOutputStream toServerOpponent = new ObjectOutputStream(opponentSocket.getOutputStream());
                ObjectInputStream fromServerOpponent = new ObjectInputStream(opponentSocket.getInputStream());
        ) {
            toServerOpponent.writeObject(attack);
            toServerOpponent.flush();
            returnAttack = (Attack) fromServerOpponent.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.out.println("attackField(): error while recieving object from opponent");
        }
        return returnAttack;
    }



    @Override
    public Attack getAttack() {
        Attack returnAttack = null;
        try (
                ServerSocket opponentServer = new ServerSocket(port);
                Socket socket = opponentServer.accept();                                  // Client Verbindung akzeptieren
                ObjectInputStream fromOpponent = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream toOpponent = new ObjectOutputStream(socket.getOutputStream());
        ) {
            // first receive Attack from opponent
            Attack recievedAttack;
            recievedAttack = (Attack) fromOpponent.readObject();

            // update attackStatus
            switch (ownPlayer.getFieldStatus(recievedAttack.getHorizontalPosition(),recievedAttack.getVerticalPosition())) {
                case TANK:
                    recievedAttack.setAttackStatus(AttackStatus.SUCCESSFUL);
                    break;
                case DESTROYED_TANK:
                    recievedAttack.setAttackStatus(AttackStatus.SUCCESSFUL);
                    break;
                default:
                    recievedAttack.setAttackStatus(AttackStatus.UNSUCCESSFUL);
            }

            // return updated attack to opponent
            toOpponent.writeObject(recievedAttack);
            toOpponent.flush();
            returnAttack = recievedAttack;

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.out.println("getAttack(): error while recieving object from opponent");
        }
        return returnAttack;
        // return attack to own player to perform Attack on field

    }
}
