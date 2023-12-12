import geometry.AStar;
import geometry.IntCoordinates;
import config.MazeConfig;
import geometry.Noeud;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStarTest {

    @Test
    public void testShortestPath() {
        // Initialisation du labyrinthe (MazeConfig)
        MazeConfig mazeConfig = MazeConfig.mockExample();

        // Définition des points de départ et d'arrivée
        IntCoordinates start = new IntCoordinates(0, 0); // Exemple de coordonnées de départ
        IntCoordinates goal = new IntCoordinates(0, 4); // Exemple de coordonnées de destination

        // Calcul du chemin le plus court
        ArrayList<IntCoordinates> path = AStar.shortestPath(start, goal, mazeConfig);

        // Vérification du chemin
        assertNotNull(path, "Le chemin ne devrait pas être null");
        assertFalse(path.isEmpty(), "Le chemin ne devrait pas être vide");
        // Vous pouvez ajouter plus de vérifications pour vous assurer que le chemin est correct
    }

    @Test
    public void testConvertToArray() {
        // On crée un chemin de nœuds en connectant manuellement quelques Noeuds
        Noeud start = new Noeud(new IntCoordinates(0, 0), null);
        Noeud middle = new Noeud(new IntCoordinates(1, 1), start);
        Noeud end = new Noeud(new IntCoordinates(2, 2), middle);

        // On utilise convertToArray pour convertir le chemin en liste de coordonnées
        ArrayList<IntCoordinates> path = AStar.convertToArray(end, start, new ArrayList<>());

        // On vérifie que la liste de coordonnées est correcte
        assertEquals(2, path.size());
        assertTrue(path.contains(new IntCoordinates(1, 1)));
        assertTrue(path.contains(new IntCoordinates(2, 2)));
    }

    @Test
    public void testQueueContains() {
        LinkedList<Noeud> queue = new LinkedList<>();
        Noeud node1 = new Noeud(new IntCoordinates(0, 0), null);
        Noeud node2 = new Noeud(new IntCoordinates(1, 1), null);
        queue.add(node1);

        assertTrue(AStar.queueContains(queue, node1));
        assertFalse(AStar.queueContains(queue, node2));
    }

    @Test
    public void testQueueContainsAndSmaller() {
        PriorityQueue<Noeud> queue = new PriorityQueue<>(AStar.comparator);
        Noeud node1 = new Noeud(new IntCoordinates(0, 0), null);
        node1.setCout(1);
        node1.setHeuristique(2);
        Noeud node2 = new Noeud(new IntCoordinates(0, 0), null);
        node2.setCout(1);
        node2.setHeuristique(3);
        queue.add(node1);

        assertFalse(AStar.queueContainsAndSmaller(queue, node1));
        assertTrue(AStar.queueContainsAndSmaller(queue, node2));
    }

}
