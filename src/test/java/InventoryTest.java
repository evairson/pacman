import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Items.Inventory;
import model.Items.Item;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void testConstructor() {
        assertFalse(inventory.isFull());
    }

    @Test
    void testAddAndIsFull() {
        for (int i = 0; i < 4; i++) {
            inventory.add(new Item());
        }
        assertTrue(inventory.isFull());
    }

    @Test
    void testRemove() {
        Item item = new Item();
        inventory.add(item);
        inventory.remove(0);
        assertFalse(inventory.isFull());
    }

    @Test
    void testToString() {
        Item item = new Item();
        inventory.add(item);
        assertNotNull(inventory.toString());
    }

    @Test
    void testUseItem() {
        Item item = new Item();
        inventory.add(item);
        inventory.useItem(0);
        assertTrue(item.isActive());
    }

    @Test
    void testGetNth() {
        Item item = new Item();
        inventory.add(item);
        assertEquals(item, inventory.getNth(0));
        assertNotNull(inventory.getNth(3));
    }
}
