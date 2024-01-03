import misc.Debug;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DebugTest { // fort inutile mais intéressant pour l'apprentissage des pratiques de test unitaires.
    // bien que la pratique de faire des tests unitaires sur des sorties de console, ne soit vraiment pas courante...

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testOut() {
        String testMessage = "Test message";
        Debug.out(testMessage);
        assertTrue(outContent.toString().contains(">> DEBUG >> " + testMessage));
    }
}
