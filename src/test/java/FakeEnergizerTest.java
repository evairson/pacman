import model.Items.FakeEnergizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FakeEnergizerTest {

    private FakeEnergizer itemTest;

    @BeforeEach
    void setUp() {
        FakeEnergizer.itemList.clear();
        itemTest = new FakeEnergizer();
    }

    @Test
    void testConstructor() {
        assertFalse(itemTest.isActive());
        assertEquals(0, itemTest.frameActivity);
        assertTrue(FakeEnergizer.itemList.contains(itemTest));
    }

    @Test
    void testSetActive() {
        itemTest.setActive(true);
        assertTrue(itemTest.isActive());
        assertEquals(0, itemTest.frameActivity);
    }

    @Test
    void testIsOneActiveWithNoActiveItems() {
        assertFalse(FakeEnergizer.isOneActive());
    }

    @Test
    void testIsOneActiveWithActiveItem() {
        itemTest.setActive(true);
        assertTrue(FakeEnergizer.isOneActive());
    }

    @Test
    void testToString() {
        assertEquals("itemtest", itemTest.toString());
    }
}
