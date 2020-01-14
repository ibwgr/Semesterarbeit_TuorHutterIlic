package TankWarsGame.Tank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TankTest {

    @Test
    void CreateTank_returnHorizontalPosition_equalsShouldBeTrue() {
        Tank tank = new Tank(5,0);

        int resultHorizontalPosition = tank.getHorizontalPosition();

        Assertions.assertEquals(5, resultHorizontalPosition);
    }

    @Test
    void CreateTank_returnHorizontalPosition_equalsShouldBeFalse() {
        Tank tank = new Tank(5,0);

        int resultHorizontalPosition = tank.getHorizontalPosition();

        Assertions.assertNotEquals(0, resultHorizontalPosition);
    }

    @Test
    void CreateTankSetHorizontalPosition_getHorizontalPosition_equalsShouldBeTrue() {
        Tank tank = new Tank(5,0);
        tank.setHorizontalPosition(3);

        int resultHorizontalPosition = tank.getHorizontalPosition();

        Assertions.assertEquals(3,resultHorizontalPosition);
    }

    @Test
    void CreateTankSetHorizontalPosition_getHorizontalPosition_equalsShouldBeFalse() {
        Tank tank = new Tank(5,0);
        tank.setHorizontalPosition(3);

        int resultHorizontalPosition = tank.getHorizontalPosition();

        Assertions.assertNotEquals(1,resultHorizontalPosition);
    }

    @Test
    void CreateTank_returnVerticalPosition_equalsShouldBeTrue() {
        Tank tank = new Tank(0,5);

        int resultVerticalPosition = tank.getVerticalPosition();

        Assertions.assertEquals(5, resultVerticalPosition);
    }

    @Test
    void CreateTank_returnVerticalPosition_equalsShouldBeFalse() {
        Tank tank = new Tank(3,5);

        int resultVerticalPosition = tank.getVerticalPosition();

        Assertions.assertNotEquals(3, resultVerticalPosition);
    }

    @Test
    void CreateTankSetVerticalPosition_getVerticalPosition_equalsShouldBeTrue() {
        Tank tank = new Tank(5,0);
        tank.setVerticalPosition(3);

        int resultVerticalPosition = tank.getVerticalPosition();

        Assertions.assertEquals(3,resultVerticalPosition);
    }

    @Test
    void CreateTankSetVerticalPosition_getVerticalPosition_equalsShouldBeFalse() {
        Tank tank = new Tank(5,0);
        tank.setHorizontalPosition(3);

        int resultVerticalPosition = tank.getVerticalPosition();

        Assertions.assertNotEquals(5,resultVerticalPosition);
    }


}