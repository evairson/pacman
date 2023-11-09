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

        // Ajoutez vos assertions ici en fonction des résultats attendus
        // Par exemple :
        assertEquals(3, result.size());
        assertTrue(result.contains(new IntCoordinates(2, 3)));
        assertTrue(result.contains(new IntCoordinates(3, 2)));
        assertTrue(result.contains(new IntCoordinates(3, 3)));
    }
}