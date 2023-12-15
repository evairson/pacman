import geometry.Noeud;
import geometry.IntCoordinates;
import config.MazeConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class NoeudTest {

    @Test
    public void testGetVoisins() {
        // Configuration initiale
        MazeConfig config = MazeConfig.mockExample();
        Noeud noeud = new Noeud(new IntCoordinates(1, 1), null);

        // Obtenir les voisins
        ArrayList<Noeud> voisins = noeud.getVoisins(config);

        assertNotNull(voisins);
    }

    @Test
    public void testCompareParHeuristique() {
        Noeud noeud1 = new Noeud(new IntCoordinates(0, 0), null);
        noeud1.setHeuristique(10);
        Noeud noeud2 = new Noeud(new IntCoordinates(1, 1), null);
        noeud2.setHeuristique(5);

        assertTrue(noeud1.compareParHeuristique(noeud2) < 0);
    }

    @Test
    public void testManhattanDistance() {
        Noeud noeud1 = new Noeud(new IntCoordinates(0, 0), null);
        Noeud noeud2 = new Noeud(new IntCoordinates(3, 4), null);

        int distance = noeud1.manhattanDistance(noeud2);

        assertEquals(7, distance);
    }
}
