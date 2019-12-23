package TankWarsGame.Tank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TankTest {

    @Test
    void CreateShip_returnLength() {
        Tank tank = new Tank(5);

        int resultLength = tank.getLength();

        Assertions.assertEquals(5, resultLength);
    }



    @Test
    void CreateShipSetLegnth_getLengt_returnsCorrectLength() {
        Tank tank = new Tank(5);
        tank.setLegnth(3);

        int resultLength = tank.getLength();

        Assertions.assertEquals(3,resultLength);
    }
}