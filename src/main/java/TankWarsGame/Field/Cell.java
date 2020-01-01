package TankWarsGame.Field;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    public Cell(){
        super(50, 50);
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
}
