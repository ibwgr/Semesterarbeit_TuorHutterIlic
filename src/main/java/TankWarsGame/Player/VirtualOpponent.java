package TankWarsGame.Player;

import TankWarsGame.Field.Field;

import java.util.Random;

public class VirtualOpponent extends Player implements Opponent {
    private Attack attack;

    /*********************************
     * Constructors
     *
     * @param name
     * @param field*/
    public VirtualOpponent(String name, Field field) {
        // TODO place tanks randomly on field while creating new opponent - Hutti: Method below placeRandom
        super(name, field);
    }

    /*********************************
     * override abstract methods
     */
    @Override
    public Attack getAttack() {
        // TODO implement logic to create random attacks - Hutti: same Method as for place tanks randomly
        return null;
    }

    @Override
    public Attack attackField(Attack attack) throws OutOfBoundsException {
        // check if position is within field boundaries
        this.checkIfInBounds(attack.getHorizontalPosition(), attack.getVerticalPosition());


        // TODO attack opponent field - Hutti: Done
        return super.field.attackField(attack);
        //return attack;

    }

    public int [] placeRandom(int fieldcount){
        Random random = new Random();
        int randomHorizontal;
        int randomVertical;

        randomHorizontal = random.nextInt(fieldcount);
        randomVertical = random.nextInt(fieldcount);
        int [] a = {randomHorizontal, randomVertical};

        return a;

    }
}
