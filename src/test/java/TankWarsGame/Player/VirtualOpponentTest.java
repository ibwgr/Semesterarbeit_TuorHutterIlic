package TankWarsGame.Player;

import TankWarsGame.Field.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class VirtualOpponentTest {

    @Test
    void getAttack() {
        //TODO some test
    }

    @Test
    void attackField() {
        //TODO some test
    }

    @Test
    void placeRandom_firstIntShouldWithin0And6(){
        VirtualOpponent virtualOpponent = new VirtualOpponent("Opponent",mock(Field.class));

        int[] resultArray = virtualOpponent.placeRandom(6);

        Assertions.assertTrue((resultArray[0] >= 0));
        Assertions.assertTrue((resultArray[0] < 6));

    }

       @Test
    void placeRandom_secondIntShouldBeWithin0And6(){
        VirtualOpponent virtualOpponent = new VirtualOpponent("Opponent",mock(Field.class));

        int[] resultArray = virtualOpponent.placeRandom(6);

        Assertions.assertTrue((resultArray[1] >= 0));
           Assertions.assertTrue((resultArray[0] < 6));

    }



}