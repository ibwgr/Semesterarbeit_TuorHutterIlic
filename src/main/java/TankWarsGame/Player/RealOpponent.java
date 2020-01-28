package TankWarsGame.Player;

import TankWarsGame.Field.Field;
import TankWarsGame.GUI.MainWindow;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import sun.applet.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class RealOpponent extends Player {
    private Player ownPlayer;

    public RealOpponent(String name, Field opponentField, Player ownPlayer){
        super(name, opponentField);
        this.ownPlayer = ownPlayer;
    }

    @Override
    public Attack attackField(Attack attack) throws OutOfBoundsException {
        System.out.println(MainWindow.opponentHostAddress.toString());
        System.out.println(MainWindow.opponentPort);
        Attack returnAttack = null;
        try (
                Socket opponentSocket = new Socket(MainWindow.opponentHostAddress, MainWindow.opponentPort);
                ObjectOutputStream toServerOpponent = new ObjectOutputStream(opponentSocket.getOutputStream());
                ObjectInputStream fromServerOpponent = new ObjectInputStream(opponentSocket.getInputStream());
        ) {
            System.out.println("socket done");
            System.out.println("from done");
            System.out.println("try Attack");
            toServerOpponent.writeObject(attack);
            toServerOpponent.flush();
            System.out.println("object sendet");
            returnAttack = (Attack) fromServerOpponent.readObject();
            System.out.println("object received");
            System.out.println(returnAttack);

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.out.println("attackField(): error while recieving object from opponent");
        }
        return returnAttack;
    }



    @Override
    public Attack getAttack() {
        Attack returnAttack = null;
        System.out.println(MainWindow.ownPort);
        try (
                ServerSocket opponentServer = new ServerSocket(MainWindow.ownPort);
                Socket socket = opponentServer.accept();                                  // Client Verbindung akzeptieren
                ObjectInputStream fromOpponent = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream toOpponent = new ObjectOutputStream(socket.getOutputStream());
        ) {


            System.out.println("client verbindung steht, game wurde gestartet");

            // first receive Attack from opponent
            Attack recievedAttack;
            recievedAttack = (Attack) fromOpponent.readObject();
            System.out.println("objekt erhalten");

            // update attackStatus
            switch (this.ownPlayer.getFieldStatus(recievedAttack.getHorizontalPosition(),recievedAttack.getVerticalPosition())) {
                case TANK:
                    recievedAttack.setAttackStatus(AttackStatus.SUCCESSFUL);
                    break;
                case DESTROYED_TANK:
                    recievedAttack.setAttackStatus(AttackStatus.SUCCESSFUL);
                    break;
                default:
                    recievedAttack.setAttackStatus(AttackStatus.UNSUCCESSFUL);
            }
            System.out.println("objektupdate" + recievedAttack);
            // return updated attack to opponent
            toOpponent.writeObject(recievedAttack);
            toOpponent.flush();

            System.out.println("Objekt zurückgesendet");
            returnAttack = recievedAttack;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.out.println("getAttack(): error while recieving object from opponent");
        }
        return returnAttack;
        // return attack to own player to perform Attack on field

    }
}
