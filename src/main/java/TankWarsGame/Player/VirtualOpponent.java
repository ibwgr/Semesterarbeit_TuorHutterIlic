package TankWarsGame.Player;

import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldOccupiedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VirtualOpponent extends Player implements Opponent {
    private Attack attack;
    List<List<Integer>> listRandom;

    /*********************************
     * Constructors
     *
     * @param name
     * @param field*/

    public VirtualOpponent(String name, Field field, int fc) {
        // TODO place tanks randomly on field while creating new opponent - Hutti: Method below placeRandom
        super(name, field);

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
        this.listRandom = lists;
    }

    /*********************************
     * override abstract methods
     */
    @Override
    public Attack getAttack() {
        // TODO implement logic to create random attacks - Hutti: same Method as for place tanks randomly
                 int[] virtualAttack = getRandom();
                   Attack attackBot = new Attack(virtualAttack[0], virtualAttack[1]);
        return attackBot;
    }

    @Override
    public Attack attackField(Attack attack) throws OutOfBoundsException {
        // check if position is within field boundaries
        this.checkIfInBounds(attack.getHorizontalPosition(), attack.getVerticalPosition());
        this.getFieldStatus(attack.getHorizontalPosition(), attack.getVerticalPosition());

        // TODO attack opponent field - Hutti: Done
        return super.field.attackField(attack);
        //return attack;

    }

    public int [] getPosRandom() {

        int index;

        Random random = new Random();
        index = random.nextInt(listRandom.size());
        System.out.println(listRandom.size());

        int[] data = {listRandom.get(index).get(0), listRandom.get(index).get(1)};
        return data;
    }

    public int [] getRandom() {

        int index;

        Random random = new Random();
        index = random.nextInt(listRandom.size());
        System.out.println(listRandom.size());

        int[] data = {listRandom.get(index).get(0), listRandom.get(index).get(1)};
        listRandom.remove(index);
        return data;
    }
}

