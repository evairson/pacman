
import gui.CritterGraphicsFactory;
import org.testfx.framework.junit5.ApplicationTest;
import gui.GraphicsUpdater;
import model.*;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Objects;

public class CritterGraphicsFactoryTest extends ApplicationTest {

    CritterGraphicsFactory factory = new CritterGraphicsFactory(1.0);
    @Test
    public void testSetImgPacman() {
        PacMan pacman = new PacMan();
        pacman.setDirection(Direction.EAST);

        String expectedUrl = Objects.requireNonNull(CritterGraphicsFactory.class.getResource("pacman-droite-rond.png")).toString();
        String actualUrl = factory.setImgPacman(pacman);

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testMakeGraphics() {
        PacMan pacman = new PacMan();

        GraphicsUpdater updater = factory.makeGraphics(pacman);
        Node node = updater.getNode();

        interact(() -> { // utilisé pour s'assurer que les assertions sont exécutées dans le thread de l'application JavaFX
            assertNotNull(node);
            assertTrue(node instanceof ImageView);
        });
    }

    @Test
    public void testGetDirectionString() {
        PacMan pacman = new PacMan();
        pacman.setDirection(Direction.EAST);
        assertEquals("droite", factory.getDirectionString(pacman));

        pacman.setDirection(Direction.WEST);
        assertEquals("gauche", factory.getDirectionString(pacman));

        pacman.setDirection(Direction.NORTH);
        assertEquals("haut", factory.getDirectionString(pacman));

        pacman.setDirection(Direction.SOUTH);
        assertEquals("bas", factory.getDirectionString(pacman));

        pacman.setDirection(Direction.NONE);
        assertEquals("droite", factory.getDirectionString(pacman)); // Cas par défaut
    }

    @Test
    public void testSetImgGhostNE() {
        assertEquals("ghost-blinky-", factory.setImgGhostNE(Ghost.BLINKY));
        assertEquals("ghost-clyde-", factory.setImgGhostNE(Ghost.CLYDE));
        assertEquals("ghost-inky-", factory.setImgGhostNE(Ghost.INKY));
        assertEquals("ghost-pinky-", factory.setImgGhostNE(Ghost.PINKY));
    }

    @Test
    public void testSetImgGhost() {
        String imgGhostNE = "ghost-blinky-";
        Ghost ghost = Ghost.BLINKY;
        ghost.setDirection(Direction.EAST);
        ghost.setEnergized(false);
        assertEquals(Objects.requireNonNull(CritterGraphicsFactory.class.getResource(imgGhostNE + "droite1.png")).toString(),
                factory.setImgGhost(ghost, 0, imgGhostNE));

        ghost.setEnergized(true);
        assertEquals(Objects.requireNonNull(CritterGraphicsFactory.class.getResource("ghost-blue1.png")).toString(),
                factory.setImgGhost(ghost, 0, imgGhostNE));
    }

    @Test
    public void testGetNumGhost() {
        assertEquals(0, factory.getNumGhost(Ghost.BLINKY));
        assertEquals(1, factory.getNumGhost(Ghost.CLYDE));
        assertEquals(2, factory.getNumGhost(Ghost.INKY));
        assertEquals(3, factory.getNumGhost(Ghost.PINKY));
    }


}

