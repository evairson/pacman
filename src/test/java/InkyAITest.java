import GhostsAI.InkyAI;
import GhostsAI.BlinkyAI;
import GhostsAI.PinkyAI;
import geometry.IntCoordinates;
import model.Direction;
import config.MazeConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class InkyAITest {

    /*@Test
    public void testGetDirection() {
        MazeConfig config = MazeConfig.mockExample();
        IntCoordinates pacPos = new IntCoordinates(0,0); // Position de Pac-Man
        IntCoordinates ghostPos = new IntCoordinates(0, 3); // Position d'Inky
        Direction pacDir = Direction.EAST;

        Direction direction = InkyAI.getDirection(config, pacPos, ghostPos, pacDir);

        // Vérifie que la direction renvoyée est l'une des directions possibles
        Set<Direction> possibleDirections = new HashSet<>();
        possibleDirections.add(Direction.NORTH);
        possibleDirections.add(Direction.SOUTH);
        possibleDirections.add(Direction.EAST);
        possibleDirections.add(Direction.WEST);
        possibleDirections.add(Direction.NONE);

        assertTrue(possibleDirections.contains(direction));

        IntCoordinates ghostBlin = new IntCoordinates(0,4);
        IntCoordinates ghostPink = new IntCoordinates(0,2);

        // Test si la direction renvoyée correspond à celle de Blinky ou Pinky dans certains cas
        Direction directionBlinky = BlinkyAI.getDirection(config, pacPos, ghostBlin);
        Direction finalDir;
        switch(directionBlinky){
            case NORTH -> { finalDir = Direction.SOUTH; }
            case SOUTH -> { finalDir = Direction.NORTH; }
            case WEST -> { finalDir = Direction.EAST; }
            case EAST -> { finalDir = Direction.WEST; }
            default -> { finalDir = Direction.NONE; }
        }
        Direction directionPinky = PinkyAI.getDirection(config, pacPos, ghostPink, pacDir);
        assertTrue(direction.equals(finalDir) || direction.equals(directionPinky));
    }*/
}
