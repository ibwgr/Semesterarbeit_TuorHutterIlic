package TankWarsGame.Field;

public class FieldOccupiedException extends Exception {
    FieldOccupiedException(){super("field is already occupied by a tank");}
}
