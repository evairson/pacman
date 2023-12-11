import static org.junit.jupiter.api.Assertions.*;

import GhostsAI.RunAwayAI;
import model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import geometry.IntCoordinates;
import config.MazeConfig;

import java.io.IOException;
import java.util.ArrayList;

public class RunAwayAITest {

    private MazeConfig config;

    @BeforeEach
    public void setUp() {
        config = MazeConfig.mockExample();
    }

    @Test
    public void testVoisinsLesPlusLoin() throws IOException {
        IntCoordinates pacmanPos = new IntCoordinates(0,0);
        IntCoordinates ghostPos = new IntCoordinates(0,3);

        ArrayList<IntCoordinates> result = RunAwayAI.voisins_les_plus_loins(pacmanPos, ghostPos, config);

        
        assertEquals(1, result.size());
        assertTrue(result.contains(new IntCoordinates(0, 4)));
    }
    
    @Test
    public void testVoisinsPlusProchesDesPlusLoins() throws IOException {
        IntCoordinates pacmanPos = new IntCoordinates(0,0);
        IntCoordinates ghostPos = new IntCoordinates(0,3);

        ArrayList<IntCoordinates> result = RunAwayAI.voisins_plus_proches_des_plus_loins(pacmanPos, ghostPos, config);

        
        assertEquals(1, result.size());
        assertTrue(result.contains(new IntCoordinates(0, 4)));

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