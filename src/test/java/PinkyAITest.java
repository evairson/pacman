import GhostsAI.PinkyAI;
import GhostsAI.BlinkyAI;
import geometry.IntCoordinates;
import model.Direction;
import config.MazeConfig;
import config.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PinkyAITest {

    @Test
    public void testGetFrontCell() {
        MazeConfig config = MazeConfig.mockExample();

        // Supposons que les coordonnées de départ soient (1, 1)
        IntCoordinates coordinates = new IntCoordinates(0, 0);

        // Direction NORTH
        IntCoordinates frontCellNorth = PinkyAI.getFrontCell(coordinates, Direction.NORTH, config);
        // Assertions pour vérifier si frontCellNorth est correct
        // ...

        // Direction SOUTH
        IntCoordinates frontCellSouth = PinkyAI.getFrontCell(coordinates, Direction.SOUTH, config);
        // Assertions pour vérifier si frontCellSouth est correct
        // ...

        // Direction EAST
        IntCoordinates frontCellEast = PinkyAI.getFrontCell(coordinates, Direction.EAST, config);
        // Assertions pour vérifier si frontCellEast est correct
        // ...

        // Direction WEST
        IntCoordinates frontCellWest = PinkyAI.getFrontCell(coordinates, Direction.WEST, config);
        // Assertions pour vérifier si frontCellWest est correct
        // ...

    }

    @Test
    public void testGetDirection() {
        MazeConfig config = MazeConfig.mockExample();
        IntCoordinates pacPos = new IntCoordinates(0, 3); // Position de Pac-Man
        IntCoordinates ghostPos = new IntCoordinates(3, 0); // Position de Pinky
        Direction pacDir = Direction.NORTH;

        Direction direction = PinkyAI.getDirection(config, pacPos, ghostPos, pacDir);

        // Vérifie que la direction est l'une des directions possibles
        assertTrue(direction == Direction.NORTH || direction == Direction.SOUTH || direction == Direction.EAST || direction == Direction.WEST || direction == Direction.NONE);

        // Vérifiez la logique spécifique de Pinky en fonction des positions de Pac-Man et de Pinky
        IntCoordinates targetPos = PinkyAI.getFrontCell(pacPos, pacDir, config);
        Direction expectedDirection = BlinkyAI.getDirection(config, targetPos, ghostPos);
        assertEquals(expectedDirection, direction);
    }

}
