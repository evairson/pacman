import geometry.IntCoordinates;
import geometry.RealCoordinates;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntCoordinatesTest {

    @Test
    public void testToRealCoordinates() {
        IntCoordinates intCoords = new IntCoordinates(2, 3);
        double scale = 1.5;

        RealCoordinates expectedRealCoords = new RealCoordinates(2 * scale, 3 * scale);
        RealCoordinates actualRealCoords = intCoords.toRealCoordinates(scale);

        assertEquals(expectedRealCoords.x(), actualRealCoords.x());
        assertEquals(expectedRealCoords.y(), actualRealCoords.y());
    }

    @Test
    public void testEquals() {
        IntCoordinates coord1 = new IntCoordinates(4, 5);
        IntCoordinates coord2 = new IntCoordinates(4, 5);
        IntCoordinates coord3 = new IntCoordinates(5, 4);

        assertTrue(coord1.equals(coord2));
        assertFalse(coord1.equals(coord3));
    }
}
