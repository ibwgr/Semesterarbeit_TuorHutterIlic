package TankWarsGame.PlayerComponents;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AttackTest {

    @Test
    void CreateAttack_getHorizontalPosition_equalsShouldBeTrue() {
        Attack attack = new Attack(3, 0);

        int resultHorizontalPosition = attack.getHorizontalPosition();

        Assertions.assertEquals(3,resultHorizontalPosition);
    }

    @Test
    void CreateAttack_getHorizontalPosition_equalsShouldBeFalse() {
        Attack attack = new Attack(0, 3);

        int resultHorizontalPosition = attack.getHorizontalPosition();

        Assertions.assertNotEquals(3,resultHorizontalPosition);
    }

    @Test
    void CreateAttack_getVerticalPosition_equalsShouldBeTrue() {
        Attack attack = new Attack(3, 0);

        int resultVerticalPosition = attack.getVerticalPosition();

        Assertions.assertEquals(0,resultVerticalPosition);
    }

    @Test
    void CreateAttack_getVerticalPosition_equalsShouldBeFalse() {
        Attack attack = new Attack(0, 5);

        int resultVerticalPosition = attack.getVerticalPosition();

        Assertions.assertNotEquals(0,resultVerticalPosition);
    }

    @Test
    void CreateAttack_getAttackStatus_equalsShouldBeTrue() {
        Attack attack = new Attack(0, 5);

        AttackStatus resultAttackStatus = attack.getAttackStatus();

        Assertions.assertEquals(AttackStatus.ATTACKING,resultAttackStatus);
    }

    @Test
    void CreateAttack_getAttackStatus_equalsShouldBeFalse() {
        Attack attack = new Attack(0, 5);

        AttackStatus resultAttackStatus = attack.getAttackStatus();

        Assertions.assertNotEquals(AttackStatus.SUCCESSFUL,resultAttackStatus);
        Assertions.assertNotEquals(AttackStatus.UNSUCCESSFUL,resultAttackStatus);
    }

    @Test
    void setAttackStatus_getAttackStatus_equalsShouldBeTrue() {
        Attack attack = new Attack(0, 5);
        attack.setAttackStatus(AttackStatus.SUCCESSFUL);

        AttackStatus resultAttackStatus = attack.getAttackStatus();

        Assertions.assertEquals(AttackStatus.SUCCESSFUL,resultAttackStatus);
    }

    @Test
    void setAttackStatus_getAttackStatus_equalsShouldBeFalse() {
        Attack attack = new Attack(0, 5);
        attack.setAttackStatus(AttackStatus.UNSUCCESSFUL);

        AttackStatus resultAttackStatus = attack.getAttackStatus();

        Assertions.assertNotEquals(AttackStatus.SUCCESSFUL,resultAttackStatus);
        Assertions.assertNotEquals(AttackStatus.ATTACKING,resultAttackStatus);
    }
}