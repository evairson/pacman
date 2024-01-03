import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Items.PacManGhost;

class PacManGhostTest {

    private PacManGhost fakeEnergizer;

    @BeforeEach
    void setUp() {
        fakeEnergizer = new PacManGhost();
    }

    @Test
    void testConstructor() {
        assertFalse(fakeEnergizer.isActive());
        assertNotNull(fakeEnergizer.getUrl());
    }

    @Test
    void testSetActive() {
        fakeEnergizer.setActive(true);
        assertTrue(fakeEnergizer.isActive());
        fakeEnergizer.setActive(false);
        assertFalse(fakeEnergizer.isActive());
    }

    @Test
    void testIsOneActive() {
        PacManGhost anotherPacManGhost = new PacManGhost();
        anotherPacManGhost.setActive(true);
        assertTrue(PacManGhost.isOneActive());
        anotherPacManGhost.setActive(false);
        assertFalse(PacManGhost.isOneActive());
    }
}
