package TankWarsGame.Player;
import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    public String name;
    public Field field;

    public Player(){

    }
    /*********************************
     * Constructors
     **/
    public Player(String name, Field field){
        this.name = name;
        this.field = field;
    }


    /*********************************
     * abstract methods
     **/
    public abstract Attack attackField(Attack attack) throws OutOfBoundsException;
    public abstract Attack getAttack();

    /*********************************
     * methods
     **/
    public void checkIfInBounds(int horizontal, int vertical) throws OutOfBoundsException {
        if (    ( horizontal < field.getHorizontalSize() && vertical < field.getVerticalSize() )
            &&  ( horizontal >= 0 && vertical >= 0 )){
            //position is inside of the boundaries --> nothing to do
        } else{
            OutOfBoundsException outOfBoundsException = new OutOfBoundsException();
            throw outOfBoundsException;
        }
    }

    /*********************************
     * getter and setter methods
     **/
    public FieldStatus getFieldStatus(int horizontalPosition, int verticalPosition) {
        return field.getFieldStatus(horizontalPosition, verticalPosition);
    }


}
