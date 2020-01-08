package TankWarsGame.Tank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TankTest {

    @Test
    void CreateTank_returnHorizontalPosition() {
        Tank tank = new Tank(5,0);

        int resultHorizontalPosition = tank.getHorizontalPosition();

        Assertions.assertEquals(5, resultHorizontalPosition);
    }



    @Test
    void CreateTankSetHorizontalPosition_getHorizontalPosition_returnsCorrectHorizontalPosition() {
        Tank tank = new Tank(5,0);
        tank.setHorizontalPosition(3);

        int resultHorizontalPosition = tank.getHorizontalPosition();

        Assertions.assertEquals(3,resultHorizontalPosition);
    }
}