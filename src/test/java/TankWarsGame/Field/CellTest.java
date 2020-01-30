package TankWarsGame.Field;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CellTest {
    @Test
    void createCell_getFillColour_EqualsShoudBeTrue() {
        Cell cell = new Cell();

        Paint resultColor = cell.getFill();

        Assertions.assertEquals(Color.TRANSPARENT,resultColor);
    }

}