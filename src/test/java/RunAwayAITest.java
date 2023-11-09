import static org.junit.Assert.*;
import org.junit.Test;
import geometry.IntCoordinates;
import config.MazeConfig;
import model.Cell;

public class RunAwayAITest {
    public void testVoisinsLesPlusLoin() {
        MazeConfig config = MazeConfig.makeExample1();
        IntCoordinates pacmanPos = config.getPacManPos();
        IntCoordinates ghostPos = config.getBlinkyPos();

        ArrayList<IntCoordinates> result = RunAwayAI.voisins_les_plus_loins(pacmanPos, ghostPos, config);

        
        assertEquals(2, result.size());
        assertTrue(result.contains(new IntCoordinates(2, 3)));
        assertTrue(result.contains(new IntCoordinates(3, 2)));
    }
    
    public void testVoisinsPlusProchesDesPlusLoins() {
        MazeConfig config = makeExample1();
        IntCoordinates pacmanPos = config.pacManPos;
        IntCoordinates ghostPos = config.blinkyPos;

        ArrayList<IntCoordinates> result = RunAwayAI.voisins_plus_proches_des_plus_loins(pacmanPos, ghostPos, config);

        
        assertEquals(3, result.size());
        assertTrue(result.contains(new IntCoordinates(2, 3)));
        assertTrue(result.contains(new IntCoordinates(3, 2)));
        assertTrue(result.contains(new IntCoordinates(3, 3)));
    }

    @Test
    public void testGetDirection() {
        MazeConfig config = makeExample1();
        IntCoordinates pacmanPos = config.pacManPos;
        IntCoordinates ghostPos = config.blinkyPos;

        Direction result = RunAwayAI.getDirection(config, pacmanPos, ghostPos);

        assertEquals(Direction.UP, result);
    }
}