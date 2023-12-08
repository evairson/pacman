import geometry.RealCoordinates;
import geometry.IntCoordinates;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RealCoordinatesTest {

    @Test
    public void testPlus() {
        RealCoordinates coord1 = new RealCoordinates(1.5, 2.5);
        RealCoordinates coord2 = new RealCoordinates(3.0, 4.0);

        RealCoordinates expected = new RealCoordinates(4.5, 6.5);
        RealCoordinates result = coord1.plus(coord2);

        assertEquals(expected.x(), result.x());
        assertEquals(expected.y(), result.y());
    }

    @Test
    public void testTimes() {
        RealCoordinates coord = new RealCoordinates(1.5, 2.5);
        double multiplier = 2;

        RealCoordinates expected = new RealCoordinates(3.0, 5.0);
        RealCoordinates result = coord.times(multiplier);

        assertEquals(expected.x(), result.x());
        assertEquals(expected.y(), result.y());
    }

    @Test
    public void testRound() {
        RealCoordinates coord = new RealCoordinates(1.6, 2.4);
        IntCoordinates expected = new IntCoordinates(2, 2);

        assertEquals(expected, coord.round());
    }

    @Test
    public void testFloorX() {
        RealCoordinates coord = new RealCoordinates(1.6, 2.5);
        RealCoordinates expected = new RealCoordinates(1.0, 2.5);

        assertEquals(expected, coord.floorX());
    }

    @Test
    public void testFloorY() {
        RealCoordinates coord = new RealCoordinates(1.6, 2.5);
        RealCoordinates expected = new RealCoordinates(1.6, 2.0);

        assertEquals(expected, coord.floorY());
    }

    @Test
    public void testCeilX() {
        RealCoordinates coord = new RealCoordinates(1.2, 2.5);
        RealCoordinates expected = new RealCoordinates(2.0, 2.5);

        assertEquals(expected, coord.ceilX());
    }

    @Test
    public void testCeilY() {
        RealCoordinates coord = new RealCoordinates(1.2, 2.3);
        RealCoordinates expected = new RealCoordinates(1.2, 3.0);

        assertEquals(expected, coord.ceilY());
    }

    @Test
    public void testWarp() {
        RealCoordinates coord = new RealCoordinates(-1.0, 11.0);
        int width = 10, height = 10;

        RealCoordinates expected = new RealCoordinates(9.0, 1.0);
        RealCoordinates result = coord.warp(width, height);

        assertEquals(expected.x(), result.x());
        assertEquals(expected.y(), result.y());
    }

    @Test
    public void testDist() {
        RealCoordinates coord1 = new RealCoordinates(1.0, 2.0);
        RealCoordinates coord2 = new RealCoordinates(4.0, 6.0);

        double expected = 5.0; // Distance euclidienne calculée manuellement
        double result = coord1.dist(coord2);

        assertEquals(expected, result);
    }
}
