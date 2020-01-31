package TankWarsGame.PlayerComponents;

import TankWarsGame.Field.Field;

public class OwnPlayer extends Player {

    /*********************************
     * Constructors
     *
     * @param name
     * name of the player
     * @param field
     * own game field, indicates where the tanks are placed on the field*/
    public OwnPlayer(String name, Field field) {
        super(name, field);
    }




    @Override
    public Attack attackField(Attack attack) throws OutOfBoundsException {
        // check if position is within field boundaries
        this.checkIfInBounds(attack.getHorizontalPosition(), attack.getVerticalPosition());

        return super.field.attackField(attack);
    }


    @Override
    public Attack getAttack() {
        Attack attack = new Attack(0,0);
        return attack;
    }
}
