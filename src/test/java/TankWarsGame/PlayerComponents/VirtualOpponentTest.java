package TankWarsGame.PlayerComponents;

import TankWarsGame.Field.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        VirtualOpponent virtualOpponent = new VirtualOpponent("Opponent",mock(Field.class), 6);

        int[] resultArray = virtualOpponent.getRandom();

        Assertions.assertTrue((resultArray[0] >= 0));
        Assertions.assertTrue((resultArray[0] < 6));

    }

       @Test
    void placeRandom_secondIntShouldBeWithin0And6(){
        VirtualOpponent virtualOpponent = new VirtualOpponent("Opponent",mock(Field.class), 6);

        int[] resultArray = virtualOpponent.getRandom();

        Assertions.assertTrue((resultArray[1] >= 0));
        Assertions.assertTrue((resultArray[0] < 6));

    }

    @Test
    void getAttack_returnedAttackShouldReturnValidAttackOptions(){
        VirtualOpponent virtualOpponent = new VirtualOpponent("Opponent",mock(Field.class), 5);

        Attack attack = virtualOpponent.getAttack();
        Assertions.assertTrue((attack.getHorizontalPosition() >= 0));
        Assertions.assertTrue((attack.getHorizontalPosition() <= 5));
        Assertions.assertTrue((attack.getVerticalPosition() >= 0));
        Assertions.assertTrue((attack.getVerticalPosition() <= 5));

    }

    @Test
    void VirtualOppenent_ListOfAttackOptionHasCorrectSize(){
        int fc = 5;

        VirtualOpponent virtualOpponent = new VirtualOpponent("Opponent",mock(Field.class), fc);
        Assertions.assertTrue(((fc * fc) == virtualOpponent.getListRandom().size()));

    }

}