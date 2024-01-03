import static org.junit.jupiter.api.Assertions.*;

import GhostsAI.RunAwayAI;
import model.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import geometry.IntCoordinates;
import config.MazeConfig;

import java.util.ArrayList;

public class RunAwayAITest {

    /*private MazeConfig config;

    @BeforeEach
    public void setUp() {
        config = MazeConfig.mockExample();
    }

    @Test
    public void testVoisinsLesPlusLoin() {
        IntCoordinates pacmanPos = new IntCoordinates(0,0);
        IntCoordinates ghostPos = new IntCoordinates(0,3);

        ArrayList<IntCoordinates> result = RunAwayAI.voisins_les_plus_loins(pacmanPos, ghostPos, config);

        
        assertEquals(1, result.size());
        assertTrue(result.contains(new IntCoordinates(0, 4)));
    }
    
    @Test
    public void testVoisinsPlusProchesDesPlusLoins() {
        IntCoordinates pacmanPos = new IntCoordinates(0,0);
        IntCoordinates ghostPos = new IntCoordinates(0,3);

        ArrayList<IntCoordinates> result = RunAwayAI.voisins_plus_proches_des_plus_loins(pacmanPos, ghostPos, config);

        
        assertEquals(1, result.size());
        assertTrue(result.contains(new IntCoordinates(0, 4)));

    }

    @Test
    public void testGetDirection() {
        IntCoordinates pacmanPos = new IntCoordinates(0,0);
        IntCoordinates ghostPos = new IntCoordinates(0,3);
        Direction result = RunAwayAI.getDirection(config, pacmanPos, ghostPos);

        assertEquals(Direction.SOUTH, result);
    }*/
}