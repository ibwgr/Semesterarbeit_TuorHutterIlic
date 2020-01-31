package TankWarsGame.Field;

import TankWarsGame.PlayerComponents.Attack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {


    /*********************************
     * getFieldStatus
     **/
    @Test
    void createField_fieldSice10_getHorizontalSize_equalsShouldBeTrue() {
        Field filed = new Field(10,10);

        int horizontalFieldSice = filed.getHorizontalSize();

        Assertions.assertEquals(10,horizontalFieldSice);
    }

        @Test
    void createField_fieldSice5_getHorizontalSize_equalsShouldBeTrue() {
        Field filed = new Field(5,5);

        int horizontalFieldSice = filed.getHorizontalSize();

        Assertions.assertEquals(5,horizontalFieldSice);
    }

    void createField_fieldSice10_getVerticalSize_equalsShouldBeTrue() {
        Field filed = new Field(10,10);

        int verticalFieldSice = filed.getVerticalSize();

        Assertions.assertEquals(10,verticalFieldSice);
    }

        @Test
    void createField_fieldSice5_getVerticalSize_equalsShouldBeTrue() {
        Field filed = new Field(5,5);

        int verticalFieldSice = filed.getVerticalSize();

        Assertions.assertEquals(5,verticalFieldSice);
    }

    /*********************************
     * getFieldStatus
     **/
    @Test
    void createField_getFirstFieldStatus_equalsShouldBeTrue() {
        Field filed = new Field(2,2);

        FieldStatus resultFieldStatus = filed.getFieldStatus(0,0);

        Assertions.assertEquals(FieldStatus.EMPTY,resultFieldStatus);
    }

    @Test
    void createField_getLastFieldStatus_equalsShouldBeTrue() {
        Field filed = new Field(2,2);

        FieldStatus resultFieldStatus = filed.getFieldStatus(1,1);

        Assertions.assertEquals(FieldStatus.EMPTY,resultFieldStatus);
    }

    /*********************************
     * placeTank
     **/
    @Test
    void placeTankAtFirstFieldPos_getFirstFieldStatus_equalsShouldBeTrue() {
        Field filed = new Field(2,2);
        try {
            filed.placeTank(0,0);
        } catch (FieldOccupiedException e) {
            Assertions.fail("placeTank has thrown FieldOccupiedException");
        }

        FieldStatus resultFieldStatus = filed.getFieldStatus(0,0);

        Assertions.assertEquals(FieldStatus.TANK,resultFieldStatus);
    }

    @Test
    void placeTankAtLastFieldPos_getLastFieldStatus_equalsShouldBeTrue() {
        Field filed = new Field(2,2);
        try {
            filed.placeTank(1,1);
        } catch (FieldOccupiedException e) {
            Assertions.fail("placeTank has thrown FieldOccupiedException");
        }

        FieldStatus resultFieldStatus = filed.getFieldStatus(1,1);

        Assertions.assertEquals(FieldStatus.TANK,resultFieldStatus);
    }


    @Test
    void TRY_CATCH_PlaceTwoTanksAtSamePosition_throwsFieldOccupiedException() {
        Field filed = new Field(2,2);
        try {
            filed.placeTank(1,1);
        } catch (FieldOccupiedException e) {
            Assertions.fail("placeTank has thrown FieldOccupiedException");
        }

        try {
            filed.placeTank(1,1);
            Assertions.fail("assertion faild because FieldOccupiedException exception was not thrown when place to tanks at same place ");
        } catch (FieldOccupiedException e) {
            Assertions.assertEquals("field is already occupied by a tank", e.getMessage());
        }
    }

    /*********************************
     * attackField
     **/
    @Test
    void attackEmptyField_getFieldStatus_equalsShouldBeTrue() {
        Field filed = new Field(2,2);
        Attack attack = new Attack(1,1);
        filed.attackField(attack);

        FieldStatus resultFieldStatus = filed.getFieldStatus(1,1);

        Assertions.assertEquals(FieldStatus.ATTACKED,resultFieldStatus);
    }

    @Test
    void attackEmptyField_getFieldStatus_equalsShouldBeFalse() {
        Field filed = new Field(2,2);
        Attack attack = new Attack(0,0);
        filed.attackField(attack);

        FieldStatus resultFieldStatus = filed.getFieldStatus(0,0);

        Assertions.assertNotEquals(FieldStatus.EMPTY,resultFieldStatus);
    }

    @Test
    void attackOccupiedField_getFieldStatus_equalsShouldBeTrue() throws FieldOccupiedException {
        Field filed = new Field(2,2);
        filed.placeTank(1,1);
        Attack attack = new Attack(1,1);
        filed.attackField(attack);

        FieldStatus resultFieldStatus = filed.getFieldStatus(1,1);

        Assertions.assertEquals(FieldStatus.DESTROYED_TANK,resultFieldStatus);
    }

    @Test
    void attackOccupiedField_getFieldStatus_equalsShouldBeFalse() throws FieldOccupiedException {
        Field filed = new Field(5,5);
        filed.placeTank(0,0);
        Attack attack = new Attack(0,0);
        filed.attackField(attack);

        FieldStatus resultFieldStatus = filed.getFieldStatus(0,0);

        Assertions.assertNotEquals(FieldStatus.ATTACKED,resultFieldStatus);
    }




}