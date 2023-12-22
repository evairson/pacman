import model.Items.Energizer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnergizerTest {
    private Energizer energizer;
    @BeforeEach
    void setUp() {
        Energizer.itemList.clear();
        energizer = new Energizer();
    }
    @Test
    void testConstructor() {
        assertFalse(energizer.isActive());
        assertEquals(0, energizer.frameActivity);
        assertTrue(Energizer.itemList.contains(energizer));
    }
    @Test
    void testIsOneActiveWithNoActiveEnergizers() {
        assertFalse(Energizer.isOneActive());
    }

    @Test
    void testSetActive() {
        energizer.setActive(true);
        assertTrue(energizer.isActive());
        assertEquals(0, energizer.frameActivity);
    }
    @Test
    void testIsOneActiveWithActiveEnergizer() {
        energizer.setActive(true);
        assertTrue(Energizer.isOneActive());
    }
}