import model.Items.ItemTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTestTest {

    private ItemTest itemTest;

    @BeforeEach
    void setUp() {
        ItemTest.itemList.clear();
        itemTest = new ItemTest();
    }

    @Test
    void testConstructor() {
        assertFalse(itemTest.isActive());
        assertEquals(0, itemTest.frameActivity);
        assertTrue(ItemTest.itemList.contains(itemTest));
    }

    @Test
    void testSetActive() {
        itemTest.setActive(true);
        assertTrue(itemTest.isActive());
        assertEquals(0, itemTest.frameActivity);
    }

    @Test
    void testIsOneActiveWithNoActiveItems() {
        assertFalse(ItemTest.isOneActive());
    }

    @Test
    void testIsOneActiveWithActiveItem() {
        itemTest.setActive(true);
        assertTrue(ItemTest.isOneActive());
    }

    @Test
    void testToString() {
        assertEquals("itemtest", itemTest.toString());
    }
}
