package TankWarsGame.Player;

import TankWarsGame.Field.Field;

public class VirtualOpponent extends Player {
    private Attack attack;

    /*********************************
     * Constructors
     *
     * @param name
     * @param field*/
    public VirtualOpponent(String name, Field field) {
        // TODO place tanks randomly on field while creating new opponent
        super(name, field);
    }


    /*********************************
     * override abstract methods
     */
    public Attack getAttack() {
        // TODO implement logic to create random attacks
        return null;
    }

    @Override
    public Attack attackField(Attack attack) throws OutOfBoundsException {
        // check if position is within field boundaries
        this.checkIfInBounds(attack.getHorizontalPosition(), attack.getVerticalPosition());


        // TODO attack opponent field
        return attack;

    }
}
