package TankWarsGame.Player;

public class Attack {
    private AttackStatus attackStatus;
    private int horizontalPosition;
    private int verticalPosition;


    /*********************************
     * Constructors
     **/
    public Attack(int horizontalPosition, int verticalPosition){
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        this.attackStatus = AttackStatus.ATTACKING;
    }

    /*********************************
     * Methods
     **/
    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public AttackStatus getAttackStatus() {
        return attackStatus;
    }

    public void setAttackStatus(AttackStatus attackStatus) {
        this.attackStatus = attackStatus;
    }
}
