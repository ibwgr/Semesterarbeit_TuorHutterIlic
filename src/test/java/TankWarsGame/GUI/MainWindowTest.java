package TankWarsGame.GUI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

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
    void getOwnScore_expectGameScore0_assertEqualsTrue() {
        AtomicInteger gameScore = MainWindow.getOwnScore();

        Assertions.assertEquals(0, gameScore.intValue());
    }

    @Test
    void getOpponentScore_expectGameScore0_assertEqualsTrue() {
        AtomicInteger gameScore = MainWindow.getOpponentScore();

        Assertions.assertEquals(0, gameScore.intValue());
    }

    @Test
    void getNumberOfTanksToPlace_assertEqualsFalse() {
        MainWindow.setNumberOfTanksToPlace(0);

        int numberOfTanksToPlace = MainWindow.getNumberOfTanksToPlace();

        Assertions.assertNotEquals(5,numberOfTanksToPlace);
    }

    @Test
    void setNumberOfTanksToPlace_getNumberOfTanksToPlace_assertEqualsTrue() {
        MainWindow.setNumberOfTanksToPlace(5);

        int numberOfTanksToPlace = MainWindow.getNumberOfTanksToPlace();

        Assertions.assertEquals(5,numberOfTanksToPlace);
    }
}