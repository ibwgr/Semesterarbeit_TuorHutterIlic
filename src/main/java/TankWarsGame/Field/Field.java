package TankWarsGame.Field;

import TankWarsGame.PlayerComponents.Attack;
import TankWarsGame.PlayerComponents.AttackStatus;

public class Field {
    private FieldStatus[][] field;


    /*********************************
     * Constructor
     **/
    public Field(int numberOfFieldsHorizontal, int numberOfFieldsVertical){
        // field
        field = new FieldStatus[numberOfFieldsHorizontal][numberOfFieldsVertical];

        // set all field status empty
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[i].length; j++){
                field[i][j] = FieldStatus.EMPTY;
            }
        }
    }


    /*********************************
     * methods
     **/
    public FieldStatus getFieldStatus(int horizontal, int vertical){
        return field[horizontal][vertical];
    }

    public void placeTank(int horizontal, int vertical) throws FieldOccupiedException{
        if (this.field[horizontal][vertical] == FieldStatus.EMPTY) {
            this.field[horizontal][vertical] = FieldStatus.TANK;
        } else {
            FieldOccupiedException fieldOccupiedException = new FieldOccupiedException();
            throw fieldOccupiedException;
        }
    }

    public Attack attackField(Attack attack){
        switch (field[attack.getHorizontalPosition()][attack.getVerticalPosition()]){
            case EMPTY:
                attack.setAttackStatus(AttackStatus.UNSUCCESSFUL);
                field[attack.getHorizontalPosition()][attack.getVerticalPosition()] = FieldStatus.ATTACKED;
                break;

            case TANK:
                attack.setAttackStatus(AttackStatus.SUCCESSFUL);
                field[attack.getHorizontalPosition()][attack.getVerticalPosition()] = FieldStatus.DESTROYED_TANK;
                break;
        }
        return attack;
    }


    public int getHorizontalSize(){
        return field.length;
    }

    public int getVerticalSize(){
        return field[0].length;
    }

}
