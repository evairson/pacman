import model.Direction;
import geometry.RealCoordinates;
import geometry.IntCoordinates;
import model.PacMan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacManTest {

    @Test
    void testGetSpeed() {
        PacMan pacman = new PacMan();
        assertEquals(3.0, pacman.getSpeed(), "Vitesse doit être de 3 quand il n'est pas sous Xanax");

        pacman.setEnergized(true);
        assertEquals(3.5, pacman.getSpeed(), "3.5 sous xanax");
    }

    @Test
    void testSetFakeEnergized() {
        PacMan pacman = new PacMan();
        assertFalse(pacman.isFakeEnergized());

        pacman.setFakeEnergized(true);
        assertTrue(pacman.isFakeEnergized());
    }

    @Test
    void testCurrCellR() {
        PacMan pacman = new PacMan();
        pacman.setPos(new RealCoordinates(3.7, 2.3));
        assertEquals(new RealCoordinates(4, 2), pacman.currCellR());
    }

    @Test
    void testCurrCellI() {
        PacMan pacman = new PacMan();
        pacman.setPos(new RealCoordinates(3.7, 2.3));
        assertEquals(new IntCoordinates(4, 2), pacman.currCellI());
    }

    @Test
    void testIsCenteredDir() {
        PacMan pacman = new PacMan();
        pacman.setPos(new RealCoordinates(3, 3.5));
        assertFalse(pacman.isCenteredDir(Direction.EAST));
        assertTrue(pacman.isCenteredDir(Direction.NORTH));
    }

    @Test
    void testIsCentered() {
        PacMan pacman = new PacMan();
        pacman.setPos(new RealCoordinates(3.5, 3.5));
        assertFalse(pacman.isCentered());

        pacman.setPos(new RealCoordinates(3, 3));
        assertTrue(pacman.isCentered());
    }
}
