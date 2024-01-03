import geometry.IntCoordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Items.BouleNeige;
import geometry.RealCoordinates;
import model.Direction;
import config.MazeConfig;

class BouleNeigeTest {

    private BouleNeige bouleNeige;
    private MazeConfig mockConfig;

    @BeforeEach
    void setUp() {
        bouleNeige = BouleNeige.INSTANCE;
        mockConfig = MazeConfig.mockExample();
        bouleNeige.setPos(new RealCoordinates(0, 0));
    }

    @Test
    void testIsActive() {
        assertFalse(bouleNeige.isActive());
        bouleNeige.lancer();
        assertTrue(bouleNeige.isActive());
        bouleNeige.detruire();
        assertFalse(bouleNeige.isActive());
    }

    @Test
    void testSetDirection() {
        bouleNeige.setDirection(Direction.EAST);
        assertEquals(Direction.EAST, bouleNeige.getDirection());
    }

    @Test
    void testIsCentered() {
        bouleNeige.setPos(new RealCoordinates(3.0, 3.0));
        assertTrue(bouleNeige.isCentered());
    }

    @Test
    void testIsGoingToCenter() {
        bouleNeige.setPos(new RealCoordinates(3.02, 3.02));
        bouleNeige.setDirection(Direction.NORTH);
        assertTrue(bouleNeige.isGoingToCenter());

        bouleNeige.setPos(new RealCoordinates(3.02, 2.98));
        bouleNeige.setDirection(Direction.SOUTH);
        assertTrue(bouleNeige.isGoingToCenter());

        bouleNeige.setPos(new RealCoordinates(2.98, 2.98));
        bouleNeige.setDirection(Direction.EAST);
        assertTrue(bouleNeige.isGoingToCenter());

        bouleNeige.setPos(new RealCoordinates(3.02, 2.98));
        bouleNeige.setDirection(Direction.WEST);
        assertTrue(bouleNeige.isGoingToCenter());
    }

    @Test
    void testTpToCenter() {
        bouleNeige.setPos(new RealCoordinates(3.01, 3.01));
        bouleNeige.setDirection(Direction.NORTH);
        bouleNeige.tpToCenter();
        assertEquals(new RealCoordinates(3, 3), bouleNeige.getPos());
    }

    @Test
    void testIsCenteredDir() {
        bouleNeige.setPos(new RealCoordinates(3.0, 3.0));
        assertTrue(bouleNeige.isCenteredDir(Direction.NORTH));
    }

    @Test
    void testGetCurrentCellR() {
        bouleNeige.setPos(new RealCoordinates(3.2, 3.6));
        assertEquals(new RealCoordinates(3, 4), bouleNeige.currCellR());
    }

    @Test
    void testGetCurrentCellI() {
        bouleNeige.setPos(new RealCoordinates(3.2, 3.6));
        assertEquals(new IntCoordinates(3, 4), bouleNeige.currCellI());
    }

    @Test
    void testGetNextPos() {
        bouleNeige.setPos(new RealCoordinates(3.0, 3.0));
        bouleNeige.setDirection(Direction.EAST);
        RealCoordinates nextPos = bouleNeige.getNextPos(1000000000, Direction.EAST, mockConfig);  // 1 second time delta
        assertNotNull(nextPos);
        assertEquals(new RealCoordinates(3.0 + bouleNeige.getSpeed(), 3.0), nextPos);
    }

    @Test
    void testMoveNorthWithoutWall() {
        bouleNeige.setPos(new RealCoordinates(1, 1));
        bouleNeige.setDirection(Direction.NORTH);
        RealCoordinates expectedPos = bouleNeige.getNextPos(1_000_000_000L, Direction.NORTH, mockConfig);
        assertNull(expectedPos);
    }

    @Test
    void testMoveWithWallBlocking() {
        // Assuming the mock MazeConfig has walls at specific positions.
        bouleNeige.setPos(new RealCoordinates(0, 0));
        bouleNeige.setDirection(Direction.WEST);
        RealCoordinates nextPos = bouleNeige.getNextPos(1_000_000_000L, Direction.WEST, mockConfig);
        assertEquals(new RealCoordinates(5.4,0), nextPos);
    }

    @Test
    void testMoveWhenNotCentered() {
        bouleNeige.setPos(new RealCoordinates(1.1, 1));
        bouleNeige.setDirection(Direction.NORTH);
        RealCoordinates nextPos = bouleNeige.getNextPos(1_000_000_000L, Direction.NORTH, mockConfig);
        assertEquals(new RealCoordinates(1.1, 1), nextPos);
    }

    @Test
    void testIsFakeEnergized() {
        assertFalse(bouleNeige.isFakeEnergized());
    }

    @Test
    void testIsEnergized() {
        assertFalse(bouleNeige.isEnergized());
    }

    @Test
    void testGetSpeed() {
        assertEquals(5.0, bouleNeige.getSpeed());
    }
}
