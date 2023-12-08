import config.Cell;
import model.Items.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testCreateCell() {
        Item item = new Item();
        Cell cell = Cell.create(true, false, true, false, item);

        assertTrue(cell.northWall());
        assertFalse(cell.eastWall());
        assertTrue(cell.southWall());
        assertFalse(cell.westWall());
        assertEquals(item, cell.initialItem());
    }

    @Test
    public void testIsPipe() {
        // Test pour une cellule qui est un tuyau horizontal
        Cell horizontalPipe = Cell.create(true, false, true, false, new Item()); // Remplacez par une instance appropriée de Item
        assertTrue(horizontalPipe.isPipe());

        // Test pour une cellule qui est un tuyau vertical
        Cell verticalPipe = Cell.create(false, true, false, true, new Item()); // Remplacez par une instance appropriée de Item
        assertTrue(verticalPipe.isPipe());

        // Test pour une cellule qui n'est pas un tuyau
        Cell nonPipe = Cell.create(true, true, false, false, new Item()); // Remplacez par une instance appropriée de Item
        assertFalse(nonPipe.isPipe());
    }
}
