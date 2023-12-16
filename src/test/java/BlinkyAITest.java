import GhostsAI.BlinkyAI;
import geometry.IntCoordinates;
import model.Direction;
import config.MazeConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlinkyAITest {

    //@Test
    /*public void testWhichDir() {
        assertEquals(Direction.NORTH, BlinkyAI.whichDir(new IntCoordinates(1, 1), new IntCoordinates(1, 0)));
        assertEquals(Direction.SOUTH, BlinkyAI.whichDir(new IntCoordinates(1, 1), new IntCoordinates(1, 2)));
        assertEquals(Direction.EAST, BlinkyAI.whichDir(new IntCoordinates(1, 1), new IntCoordinates(2, 1)));
        assertEquals(Direction.WEST, BlinkyAI.whichDir(new IntCoordinates(1, 1), new IntCoordinates(0, 1)));
        assertEquals(Direction.NONE, BlinkyAI.whichDir(new IntCoordinates(1, 1), new IntCoordinates(2, 2)));
    }

    @Test
    public void testGetDirection() {
        MazeConfig config = MazeConfig.mockExample();
        IntCoordinates ghostPos = new IntCoordinates(0, 5);
        IntCoordinates pacPos = new IntCoordinates(0, 0);

        Direction direction = BlinkyAI.getDirection(config, pacPos, ghostPos);

        assertEquals(direction, Direction.NORTH);
    }*/

}
