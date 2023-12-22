import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Items.FakeEnergizer;

class FakeEnergizerTest {

    private FakeEnergizer fakeEnergizer;

    @BeforeEach
    void setUp() {
        fakeEnergizer = new FakeEnergizer();
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
        FakeEnergizer anotherFakeEnergizer = new FakeEnergizer();
        anotherFakeEnergizer.setActive(true);
        assertTrue(FakeEnergizer.isOneActive());
        anotherFakeEnergizer.setActive(false);
        assertFalse(FakeEnergizer.isOneActive());
    }
}
