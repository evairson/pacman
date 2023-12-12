import model.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void testToString() {
        assertEquals("none", Direction.NONE.toString());
        assertEquals("north", Direction.NORTH.toString());
        assertEquals("east", Direction.EAST.toString());
        assertEquals("south", Direction.SOUTH.toString());
        assertEquals("west", Direction.WEST.toString());
    }
}
