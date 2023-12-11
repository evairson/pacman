import static org.junit.jupiter.api.Assertions.*;

import GhostsAI.RunAwayAI;
import model.Direction;
import org.junit.jupiter.api.Test;
import geometry.IntCoordinates;
import config.MazeConfig;

import java.io.IOException;
import java.util.ArrayList;

public class RunAwayAITest {

    public void testVoisinsLesPlusLoin() throws IOException {
        MazeConfig config = MazeConfig.makeGenericExample(1);
        assert config != null;
        IntCoordinates pacmanPos = config.getPacManPos();
        IntCoordinates ghostPos = config.getBlinkyPos();

        ArrayList<IntCoordinates> result = RunAwayAI.voisins_les_plus_loins(pacmanPos, ghostPos, config);

        
        assertEquals(2, result.size());
        assertTrue(result.contains(new IntCoordinates(2, 3)));
        assertTrue(result.contains(new IntCoordinates(3, 2)));
    }
    
    public void testVoisinsPlusProchesDesPlusLoins() throws IOException {
        MazeConfig config = MazeConfig.makeGenericExample(1);
        assert config != null;
        IntCoordinates pacmanPos = config.getPacManPos();
        IntCoordinates ghostPos = config.getBlinkyPos();

        ArrayList<IntCoordinates> result = RunAwayAI.voisins_plus_proches_des_plus_loins(pacmanPos, ghostPos, config);

        
        assertEquals(3, result.size());
        assertTrue(result.contains(new IntCoordinates(2, 3)));
        assertTrue(result.contains(new IntCoordinates(3, 2)));
        assertTrue(result.contains(new IntCoordinates(3, 3)));
    }

    @Test
    public void testGetDirection() throws IOException {
        MazeConfig config = MazeConfig.makeGenericExample(1);
        assert config != null;
        IntCoordinates pacmanPos = config.getPacManPos();
        IntCoordinates ghostPos = config.getBlinkyPos();

        Direction result = RunAwayAI.getDirection(config, pacmanPos, ghostPos);

        assertEquals(Direction.NORTH, result);
    }
}