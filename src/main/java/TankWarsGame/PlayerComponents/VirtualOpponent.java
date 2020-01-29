package TankWarsGame.PlayerComponents;

import TankWarsGame.Field.Field;
import TankWarsGame.Field.FieldOccupiedException;
import TankWarsGame.GUI.StartScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VirtualOpponent extends Player {
    private Attack attack;
    private int fieldcount;
    List<List<Integer>> listRandom;


    /*********************************
     * Constructors
     *
     * @param name
     * @param field*/

    public VirtualOpponent(String name, Field field, int fieldcount) {
        super(name, field);
        // setAttackOptions -> this makes sure that the tanks could not be placed randomly at the same position
        setAttackOptions(fieldcount);

        for (int i = 0; i < StartScreen.numberOfTanks; i++) {
                int[] positionTanks = this.getRandom();
                try {
                    this.field.placeTank(positionTanks[0], positionTanks[1]);
                } catch (FieldOccupiedException fo) {
                    System.out.println(fo.fillInStackTrace());
                }
            }

        // reset AttackOptions after placing tanks randomly -> this makes sure that not the same cell is attacked twice
        setAttackOptions(fieldcount);
    }


    /*********************************
     * override abstract methods
     */
    @Override
    public Attack getAttack() {
        // create random attack from getRandom() method
        int[] virtualAttack = getRandom();
        Attack attackBot = new Attack(virtualAttack[0], virtualAttack[1]);
        return attackBot;
    }

    @Override
    public Attack attackField(Attack attack) throws OutOfBoundsException {
        // check if position is within field boundaries
        this.checkIfInBounds(attack.getHorizontalPosition(), attack.getVerticalPosition());
        this.getFieldStatus(attack.getHorizontalPosition(), attack.getVerticalPosition());

        //return attack;
        return super.field.attackField(attack);
    }


    /*********************************
     * set attack options
     * this make sure that a cell is attacked only once
     */
    public void setAttackOptions(int fc) {
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
     * getRandom
     * this generates unique random cell positions
     */
    public int [] getRandom() {

        int index;

        Random random = new Random();
        index = random.nextInt(listRandom.size());

        int[] data = {listRandom.get(index).get(0), listRandom.get(index).get(1)};
        listRandom.remove(index);
        return data;
    }
}

