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

    public List<List<Integer>> createAttackOptions(int fc) {

        int[][] attackOptions;
        attackOptions = new int[fc * fc][2];
        int c = 0;

        for (int m = 0; m < fc; m++) {
            for (int n = 0; n < fc; n++) {
                attackOptions[c][0] = m;
                attackOptions[c++][1] = n;
                if (c == (fc * fc)) {
                    break;
                }
            }
        }

        List<List<Integer>> lists = new ArrayList<>();
        for (int[] options : attackOptions) {
            List<Integer> list = new ArrayList<>();
            for (int i : options) {
                list.add(i);
            }
            lists.add(list);
        }
        return lists;
    }

    /*********************************
     * getter and setter methods
     **/
    public FieldStatus getFieldStatus(int horizontalPosition, int verticalPosition) {
        return field.getFieldStatus(horizontalPosition, verticalPosition);
    }


}
