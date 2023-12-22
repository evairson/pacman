import model.Items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.image.ImageView;

class ItemTest {

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
    }

    @Test
    void testConstructor() {
        assertFalse(item.isCollectable());
    }

    @Test
    void testSetAndGetUrl() {
        String testUrl = "testUrl";
        item.url = testUrl;
        assertEquals(testUrl, item.getUrl());
    }

    @Test
    void testSetAndGetImage() {
        ImageView testImage = new ImageView();
        item.setImage(testImage);
        assertEquals(testImage, item.getImage());
    }

    @Test
    void testSetAndGetCollectable() {
        item.setCollectable(true);
        assertTrue(item.isCollectable());

        item.setCollectable(false);
        assertFalse(item.isCollectable());
    }

    @Test
    void testSetAndGetActive() {
        item.setActive(true);
        assertTrue(item.isActive());

        item.setActive(false);
        assertFalse(item.isActive());
    }

    @Test
    void testToString() {
        assertEquals("a", item.toString());
    }
}
