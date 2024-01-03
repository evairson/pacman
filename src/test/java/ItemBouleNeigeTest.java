import model.Items.ItemBouleNeige;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemBouleNeigeTest {

    private ItemBouleNeige itemBouleNeige;

    @BeforeEach
    void setUp() {
        itemBouleNeige = new ItemBouleNeige();
    }

    @Test
    void testConstructor() {
        assertTrue(itemBouleNeige.isCollectable());
        assertNotNull(itemBouleNeige.getUrl());
    }

    @Test
    void testSetActive() {
        itemBouleNeige.setActive(true);
        assertTrue(itemBouleNeige.isActive());

    }
}
