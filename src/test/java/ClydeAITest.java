import GhostsAI.ClydeAI;
import geometry.IntCoordinates;
import model.Direction;
import config.MazeConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class ClydeAITest {

    /*@Test
    public void testGetRandomDir() {
        Set<Direction> directions = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            directions.add(ClydeAI.getRandomDir());
        }

        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
        assertTrue(directions.contains(Direction.WEST));
        // Vérifie que toutes les directions possibles sont couvertes
    }

    @Test
    public void testIsInNode() {
        MazeConfig config = MazeConfig.mockExample();
        IntCoordinates pipePosition = new IntCoordinates(0,1);
        IntCoordinates intersectionPosition = new IntCoordinates(0,0);

        assertFalse(ClydeAI.isInNode(config, pipePosition));
        assertTrue(ClydeAI.isInNode(config, intersectionPosition));
    }

    @Test
    public void testGetDirection() {
        MazeConfig config = MazeConfig.mockExample();
        IntCoordinates intersectionPosition = new IntCoordinates(0,0);
        IntCoordinates pipePosition = new IntCoordinates(0,1);

        Direction defaultDir = Direction.NORTH;
        Set<Direction> directions = new HashSet<>();
        directions.add(Direction.EAST);
        directions.add(Direction.NORTH);
        directions.add(Direction.SOUTH);
        directions.add(Direction.WEST);
        boolean testIfInDirections = directions.contains(ClydeAI.getDirection(config, intersectionPosition, defaultDir));
        assertTrue(testIfInDirections);
        // Pipe
        assertEquals(defaultDir, ClydeAI.getDirection(config, pipePosition, defaultDir));
    }*/

}
