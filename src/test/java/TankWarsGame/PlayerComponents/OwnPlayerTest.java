package TankWarsGame.PlayerComponents;

import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class OwnPlayerTest {

    /*********************************
     * attackField
     *
     * not needed to bo tested at this point. This method uses only other methods, and those methods will be tested separately
     **/


    /*********************************
     * checkIfInBounds
     **/
    @Test
    void TRY_CATCH_CheckIfInBounds_isOutOfHorizontalHighBound_throwsOutOfBoundsException() {
        Field field = mock(Field.class);
        when(field.getHorizontalSize()).thenReturn(5);
        when(field.getVerticalSize()).thenReturn(5);

        Player player = new OwnPlayer("ich", field);
        try {
            player.checkIfInBounds(6, 4);
            Assertions.fail("No OutOfBoundsException was thrown");
        } catch (OutOfBoundsException e) {
            Assertions.assertEquals("attack is out of field boundaries", e.getMessage());
        }
    }

    @Test
    void TRY_CATCH_CheckIfInBounds_isOutOfHorizontalLowBound_throwsOutOfBoundsException() {
        Field field = mock(Field.class);
        when(field.getHorizontalSize()).thenReturn(5);
        when(field.getVerticalSize()).thenReturn(5);

        Player player = new OwnPlayer("ich", field);
        try {
            player.checkIfInBounds(-1, 4);
            Assertions.fail("No OutOfBoundsException was thrown");
        } catch (OutOfBoundsException e) {
            Assertions.assertEquals("attack is out of field boundaries", e.getMessage());
        }
    }

    @Test
    void TRY_CATCH_CheckIfInBounds_isOutOfVerticalHighBound_throwsOutOfBoundsException() {
        Field field = mock(Field.class);
        when(field.getHorizontalSize()).thenReturn(5);
        when(field.getVerticalSize()).thenReturn(5);

        Player player = new OwnPlayer("ich", field);
        try {
            player.checkIfInBounds(2, 8);
            Assertions.fail("No OutOfBoundsException was thrown");
        } catch (OutOfBoundsException e) {
            Assertions.assertEquals("attack is out of field boundaries", e.getMessage());
        }
    }

    @Test
    void TRY_CATCH_CheckIfInBounds_isOutOfVerticalLowBound_throwsOutOfBoundsException() {
        Field field = mock(Field.class);
        when(field.getHorizontalSize()).thenReturn(5);
        when(field.getVerticalSize()).thenReturn(5);

        Player player = new OwnPlayer("ich", field);
        try {
            player.checkIfInBounds(2, -1);
            Assertions.fail("No OutOfBoundsException was thrown");
        } catch (OutOfBoundsException e) {
            Assertions.assertEquals("attack is out of field boundaries", e.getMessage());
        }
    }

    @Test
    void TRY_CATCH_CheckIfInBounds_isOutOfAllBoundaries_throwsOutOfBoundsException() {
        Field field = mock(Field.class);
        when(field.getHorizontalSize()).thenReturn(5);
        when(field.getVerticalSize()).thenReturn(5);

        Player player = new OwnPlayer("ich", field);
        try {
            player.checkIfInBounds(-1, 6);
            Assertions.fail("No OutOfBoundsException was thrown");
        } catch (OutOfBoundsException e) {
            Assertions.assertEquals("attack is out of field boundaries", e.getMessage());
        }
    }

    @Test
    void TRY_CATCH_CheckIfInBounds_isInsideBoundaries_doesNotThrowOutOfBoundsException() {
        Field field = mock(Field.class);
        when(field.getHorizontalSize()).thenReturn(5);
        when(field.getVerticalSize()).thenReturn(5);

        Player player = new OwnPlayer("ich", field);
        try {
            player.checkIfInBounds(0, 4);

        } catch (OutOfBoundsException e) {
            Assertions.fail("OutOfBoundsException was thrown");
            Assertions.assertEquals("attack is out of field boundaries", e.getMessage());
        }
    }


    /*********************************
     * getFieldStatus
     **/
    @Test
    void FieldStatusTANK_getFieldStatusFromPlayer_EqualsShouldBeTrue() {
        Field field = mock(Field.class);
        when(field.getFieldStatus(anyInt(), anyInt())).thenReturn(FieldStatus.TANK);

        Player player = new OwnPlayer("ich", field);
        FieldStatus resultFieldStatus = player.getFieldStatus(2, 2);

        Assertions.assertEquals(FieldStatus.TANK, resultFieldStatus);
        verify(field, times(1)).getFieldStatus(anyInt(), anyInt());
    }

    @Test
    void FieldStatusTANK_getFieldStatusFromPlayer_EqualsShouldBeFalse() {
        Field field = mock(Field.class);
        when(field.getFieldStatus(anyInt(), anyInt())).thenReturn(FieldStatus.TANK);

        Player player = new OwnPlayer("ich", field);
        FieldStatus resultFieldStatus = player.getFieldStatus(2, 2);

        Assertions.assertNotEquals(FieldStatus.ATTACKED, resultFieldStatus);
        verify(field, times(1)).getFieldStatus(anyInt(), anyInt());
    }

    @Test
    void FieldStatusEMPTY_getFieldStatusFromPlayer_EqualsShouldBeTrue() {
        Field field = mock(Field.class);
        when(field.getFieldStatus(anyInt(), anyInt())).thenReturn(FieldStatus.EMPTY);

        Player player = new OwnPlayer("ich", field);
        FieldStatus resultFieldStatus = player.getFieldStatus(2, 2);

        Assertions.assertEquals(FieldStatus.EMPTY, resultFieldStatus);
        verify(field, times(1)).getFieldStatus(anyInt(), anyInt());
    }

    @Test
    void FieldStatusEMPTY_getFieldStatusFromPlayer_EqualsShouldBeFalse() {
        Field field = mock(Field.class);
        when(field.getFieldStatus(anyInt(), anyInt())).thenReturn(FieldStatus.EMPTY);

        Player player = new OwnPlayer("ich", field);
        FieldStatus resultFieldStatus = player.getFieldStatus(2, 2);

        Assertions.assertNotEquals(FieldStatus.TANK, resultFieldStatus);
        verify(field, times(1)).getFieldStatus(anyInt(), anyInt());
    }

    @Test
    void FieldStatusATTACKED_getFieldStatusFromPlayer_EqualsShouldBeTrue() {
        Field field = mock(Field.class);
        when(field.getFieldStatus(anyInt(), anyInt())).thenReturn(FieldStatus.ATTACKED);

        Player player = new OwnPlayer("ich", field);
        FieldStatus resultFieldStatus = player.getFieldStatus(2, 2);

        Assertions.assertEquals(FieldStatus.ATTACKED, resultFieldStatus);
        verify(field, times(1)).getFieldStatus(anyInt(), anyInt());
    }

    @Test
    void FieldStatusATTACKED_getFieldStatusFromPlayer_EqualsShouldBeFalse() {
        Field field = mock(Field.class);
        when(field.getFieldStatus(anyInt(), anyInt())).thenReturn(FieldStatus.ATTACKED);

        Player player = new OwnPlayer("ich", field);
        FieldStatus resultFieldStatus = player.getFieldStatus(2, 2);

        Assertions.assertNotEquals(FieldStatus.DESTROYED_TANK, resultFieldStatus);
        verify(field, times(1)).getFieldStatus(anyInt(), anyInt());
    }

    @Test
    void FieldStatusDESTROYED_TANK_getFieldStatusFromPlayer_EqualsShouldBeTrue() {
        Field field = mock(Field.class);
        when(field.getFieldStatus(anyInt(), anyInt())).thenReturn(FieldStatus.DESTROYED_TANK);

        Player player = new OwnPlayer("ich", field);
        FieldStatus resultFieldStatus = player.getFieldStatus(2, 2);

        Assertions.assertEquals(FieldStatus.DESTROYED_TANK, resultFieldStatus);
        verify(field, times(1)).getFieldStatus(anyInt(), anyInt());
    }

    @Test
    void FieldStatusDESTROYED_TANK_getFieldStatusFromPlayer_EqualsShouldBeFalse() {
        Field field = mock(Field.class);
        when(field.getFieldStatus(anyInt(), anyInt())).thenReturn(FieldStatus.DESTROYED_TANK);

        Player player = new OwnPlayer("ich", field);
        FieldStatus resultFieldStatus = player.getFieldStatus(2, 2);

        Assertions.assertNotEquals(FieldStatus.TANK, resultFieldStatus);
        verify(field, times(1)).getFieldStatus(anyInt(), anyInt());
    }

}