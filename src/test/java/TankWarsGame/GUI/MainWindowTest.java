package TankWarsGame.GUI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainWindowTest {

    @Test
    void getOpponentPlayerTurn_assertEqualsTrue() {
        boolean opponentPlayerTurn = MainWindow.getOpponentPlayerTurn();

        Assertions.assertEquals(false, opponentPlayerTurn);
    }


    @Test
    void setOpponentTurnTrue_getOpponentPlayerTrurn_assertEqualsTrue() {
        MainWindow.setOpponentTurn(true);

        boolean opponentPlayerTurn = MainWindow.getOpponentPlayerTurn();

        Assertions.assertEquals(true, opponentPlayerTurn);
    }


    @Test
    void setOpponentTurnFalse_getOpponentPlayerTrurn_assertEqualsTrue() {
        MainWindow.setOpponentTurn(true);
        Assertions.assertEquals(true, MainWindow.getOpponentPlayerTurn());
        MainWindow.setOpponentTurn(false);

        boolean opponentPlayerTurn = MainWindow.getOpponentPlayerTurn();

        Assertions.assertEquals(false, MainWindow.getOpponentPlayerTurn());
    }



    @Test
    void getOwnScore() {
    }

    @Test
    void getOpponentScore() {
    }

    @Test
    void setNumberOfTanksToPlace() {
    }
}