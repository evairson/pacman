import geometry.IntCoordinates;
import model.Direction;
import model.PacMan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import model.Ghost;
import geometry.RealCoordinates;
import config.MazeConfig;

public class GhostTest {

    @Test
    public void testIsCentered() {
        // Création d'une instance de Ghost
        Ghost ghost = Ghost.BLINKY; // Utiliser n'importe quel fantôme ici

        // Cas de test 1: Position centrée
        ghost.setPos(new RealCoordinates(5.0, 5.0));
        assertTrue(ghost.isCentered(), "Le fantôme devrait être considéré comme centré");

        // Cas de test 2: Position non centrée en x
        ghost.setPos(new RealCoordinates(5.3, 5.0));
        assertFalse(ghost.isCentered(), "Le fantôme ne devrait pas être considéré comme centré");

        // Cas de test 3: Position non centrée en y
        ghost.setPos(new RealCoordinates(5.0, 5.7));
        assertFalse(ghost.isCentered(), "Le fantôme ne devrait pas être considéré comme centré");

        // Cas de test 4: Position non centrée en x et y
        ghost.setPos(new RealCoordinates(5.3, 5.7));
        assertFalse(ghost.isCentered(), "Le fantôme ne devrait pas être considéré comme centré");
    }

    @Test
    public void testGetAndSetPos() {
        Ghost ghost = Ghost.BLINKY;
        RealCoordinates testPos = new RealCoordinates(3.0, 4.0);

        ghost.setPos(testPos);
        RealCoordinates retrievedPos = ghost.getPos();

        assertEquals(testPos, retrievedPos, "La position définie et récupérée doit être la même");
    }

    @Test
    public void testGetAndSetDirection() {
        Ghost ghost = Ghost.BLINKY;
        Direction testDirection = Direction.NORTH;

        ghost.setDirection(testDirection);
        Direction retrievedDirection = ghost.getDirection();

        assertEquals(testDirection, retrievedDirection, "La direction définie et récupérée doit être la même");
    }

    @Test
    public void testGetSpeed() {
        Ghost ghost = Ghost.BLINKY;
        double expectedSpeed = 1.3;

        double speed = ghost.getSpeed();
        assertEquals(1.9500000000000002, speed, "La vitesse devrait être égale à la vitesse de base");

        PacMan.INSTANCE.setEnergized(true);
        expectedSpeed *= 1.5;
        speed = ghost.getSpeed();
        assertEquals(expectedSpeed, speed, "La vitesse devrait augmenter quand PacMan est energized");
    }

    @Test
    public void testIsEnergized() {
        Ghost.setAllEnergizedValue(true);
        assertTrue(Ghost.BLINKY.isEnergized(), "Le fantôme devrait être energized");

        Ghost.setAllEnergizedValue(false);
        assertFalse(Ghost.BLINKY.isEnergized(), "Le fantôme ne devrait pas être energized");
    }

    @Test
    public void testIsGoingToCenter() {
        Ghost ghost = Ghost.BLINKY;
        ghost.setPos(new RealCoordinates(3.2, 4.2));

        ghost.setDirection(Direction.NORTH);
        assertTrue(ghost.isGoingToCenter(), "Le fantôme devrait se diriger vers le centre");

        ghost.setDirection(Direction.SOUTH);
        assertFalse(ghost.isGoingToCenter(), "Le fantôme ne devrait pas se diriger vers le centre");

        ghost.setDirection(Direction.EAST);
        assertFalse(ghost.isGoingToCenter(), "Le fantôme ne devrait pas se diriger vers le centre");

        ghost.setDirection(Direction.WEST);
        assertTrue(ghost.isGoingToCenter(), "Le fantôme ne devrait pas se diriger vers le centre");
    }

    @Test
    public void testCurrCellRAndI() {
        Ghost ghost = Ghost.BLINKY;
        ghost.setPos(new RealCoordinates(3.2, 4.7));

        RealCoordinates expectedCurrCellR = new RealCoordinates(3, 5);
        IntCoordinates expectedCurrCellI = new IntCoordinates(3, 5);

        assertEquals(expectedCurrCellR, ghost.currCellR(), "Les coordonnées arrondies doivent être correctes");
        assertEquals(expectedCurrCellI, ghost.currCellI(), "Les coordonnées entières doivent être correctes");
    }

    @Test
    public void testTpToCenter() {
        Ghost ghost = Ghost.BLINKY;
        ghost.setDirection(Direction.NORTH);
        ghost.setPos(new RealCoordinates(3.01, 4.01));

        ghost.tpToCenter();
        RealCoordinates expectedPos = new RealCoordinates(3, 4);

        assertEquals(expectedPos, ghost.getPos(), "Le fantôme doit être téléporté au centre");
    }

    @Test
    public void testIsCenteredDir() {
        Ghost ghost = Ghost.BLINKY;
        ghost.setPos(new RealCoordinates(3.0, 4.5));

        assertFalse(ghost.isCenteredDir(Direction.EAST), "Doit être centré en y pour la direction Est");
        assertTrue(ghost.isCenteredDir(Direction.NORTH), "Ne doit pas être centré en x pour la direction Nord");
    }


    @Test
    public void testGetNextPos() {
        // Création du mock de MazeConfig
        MazeConfig mazeConfig = MazeConfig.mockExample();

        // Création et configuration d'un fantôme
        Ghost ghost = Ghost.BLINKY;
        ghost.setPos(new RealCoordinates(1.0, 1.0));
        ghost.setDirection(Direction.EAST);

        // Définir un intervalle de temps pour le déplacement (en nanosecondes)
        long deltaTns = 1000000; // 1 milliseconde

        // Calcul de la position attendue
        RealCoordinates expectedPos = new RealCoordinates(1.0 + ghost.getSpeed() * deltaTns * 1E-9, 1.0);

        // Appel de getNextPos et vérification du résultat
        RealCoordinates newPos = ghost.getNextPos(deltaTns, Direction.EAST, mazeConfig);
        assertEquals(expectedPos.x(), newPos.x(), "La position x du fantôme doit être mise à jour correctement");
        assertEquals(expectedPos.y(), newPos.y(), "La position y du fantôme doit rester la même");
    }

}
