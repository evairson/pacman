import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import model.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class MazeConfigTest {

    @Test
    public void testTxtToMaze() throws IOException {
        MazeConfig config = MazeConfig.makeGenericExample(1);

        assertNotNull(config);
        // Peut-être considérer d'autres assertions basées sur le contenu attendu du fichier testMap.txt ?? difficile pour le moment
    }

    @Test
    public void testGetCell() {
        MazeConfig config = MazeConfig.mockExample();

        assertNotNull(config);
        Cell cell = config.getCell(new IntCoordinates(0, 0));
        assertNotNull(cell);
        assertTrue(!cell.northWall() && !cell.westWall() && !cell.eastWall() && !cell.southWall());
    }

    @Test
    public void testIsGameComplete() {
        boolean result = MazeConfig.isGameComplete();
        assertTrue(result);
    }

    @Test
    public void testResetItems() {
        MazeConfig config = MazeConfig.mockExample();
        // Supposons que le labyrinthe contient des items actifs initialement
        config.resetItems();

        // Vérifiez que tous les items dans toutes les cellules sont inactifs
        for (int y = 0; y < config.getHeight(); y++) {
            for (int x = 0; x < config.getWidth(); x++) {
                Cell cell = config.getCell(new IntCoordinates(x, y));
                if (cell.initialItem() != null) {
                    assertFalse(cell.initialItem().isActive());
                }
            }
        }
    }

    @Test
    void testIsWarpForAllDirections() {
        MazeConfig config = MazeConfig.mockExample();

        // Test pour NORTH
        Cell northCell = config.getCell(new IntCoordinates(0, 0)); // Exemple de cellule en haut sans mur nord
        assertTrue(config.isWarp(northCell, Direction.NORTH));

        // Test pour SOUTH
        Cell southCell = config.getCell(new IntCoordinates(0, 5)); // Exemple de cellule en bas sans mur sud
        assertTrue(config.isWarp(southCell, Direction.SOUTH));

        // Test pour WEST
        Cell westCell = config.getCell(new IntCoordinates(0, 0)); // Exemple de cellule à gauche sans mur ouest
        assertTrue(config.isWarp(westCell, Direction.WEST));

        // Test pour EAST
        Cell eastCell = config.getCell(new IntCoordinates(5, 0)); // Exemple de cellule à droite sans mur est
        assertTrue(config.isWarp(eastCell, Direction.EAST));

        // Test pour NONE
        assertFalse(config.isWarp(northCell, Direction.NONE));
    }
}
