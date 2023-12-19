import GhostsAI.BlinkyAI;
import GhostsAI.InkyAI;
import config.MazeConfig;
import geometry.IntCoordinates;
import model.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

class InkyAITest {

    private Random createMockRandom(int seed) {
        return new Random(seed);
    }

    private Direction getRevertDirection(Direction dir){
        switch(dir){
            case NORTH -> { return Direction.SOUTH; }
            case SOUTH -> { return Direction.NORTH; }
            case WEST -> { return Direction.EAST; }
            case EAST -> { return Direction.WEST; }
            default -> { return Direction.NONE; }
        }
    }
    @Test
    void testgetDirection() {
        MazeConfig config = MazeConfig.mockExample();
        Random mockRandom = createMockRandom(1200254678);
        Direction pacDir = Direction.EAST;
        // DIRECTION SUD
        // définir blinkyDirection :
        IntCoordinates pacPos = new IntCoordinates(0, 0);
        IntCoordinates ghostPos = new IntCoordinates(0, 2);
        Direction tempDir = BlinkyAI.getDirection(config, pacPos, ghostPos);
        InkyAI.setRd(mockRandom);
        Direction result = InkyAI.getDirection(config, pacPos, ghostPos, pacDir);
        assertEquals(getRevertDirection(tempDir), result, "Inky devrait aller au SUD si BlinkyAI retourne le NORD");

        //DIRECTION NORD
        //définir blinkyDirection :
        pacPos = new IntCoordinates(0, 2);
        ghostPos = new IntCoordinates(0, 0);
        tempDir = BlinkyAI.getDirection(config, pacPos, ghostPos);
        mockRandom = createMockRandom(1200254678);
        InkyAI.setRd(mockRandom);
        result = InkyAI.getDirection(config, pacPos, ghostPos, pacDir);
        assertEquals(getRevertDirection(tempDir), result, "Inky devrait aller au NORD si BlinkyAI retourne le SUD");

        //DIRECTION EAST
        //définir blinkyDirection :
        pacPos = new IntCoordinates(0, 0);
        ghostPos = new IntCoordinates(2, 0);
        tempDir = BlinkyAI.getDirection(config, pacPos, ghostPos);
        mockRandom = createMockRandom(1200254678);
        InkyAI.setRd(mockRandom);
        result = InkyAI.getDirection(config, pacPos, ghostPos, pacDir);
        assertEquals(getRevertDirection(tempDir), result, "Inky devrait aller à l'EAST si BlinkyAI retourne l'WEST'");

        //DIRECTION WEST
        //définir blinkyDirection :
        pacPos = new IntCoordinates(2, 0);
        ghostPos = new IntCoordinates(0, 0);
        tempDir = BlinkyAI.getDirection(config, pacPos, ghostPos);
        mockRandom = createMockRandom(1200254678);
        InkyAI.setRd(mockRandom);
        result = InkyAI.getDirection(config, pacPos, ghostPos, pacDir);
        assertEquals(getRevertDirection(tempDir), result, "Inky devrait aller à l'WEST si BlinkyAI retourne l'EAST");

        //DIRECTION pinky
        //définir blinkyDirection :
        pacPos = new IntCoordinates(2, 0);
        ghostPos = new IntCoordinates(0, 0);
        tempDir = BlinkyAI.getDirection(config, pacPos, ghostPos);
        mockRandom = createMockRandom(0);
        InkyAI.setRd(mockRandom);
        result = InkyAI.getDirection(config, pacPos, ghostPos, pacDir);
        assertEquals(tempDir, result, "Inky devrait aller au NONE si BlinkyAI retourne NONE");
    }
}
