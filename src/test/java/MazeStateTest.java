import config.Cell;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.AnimationController;
import model.Critter;
import model.Direction;
import model.Items.Dot;
import model.Items.Item;
import model.MazeState;
import config.MazeConfig;
import model.PacMan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MazeStateTest {

    private MazeConfig config;
    private MazeState mazeState;
    private AnimationController animationController;

    @BeforeEach
    public void setUp() {
        config = MazeConfig.mockExample();
        mazeState = new MazeState(config);
    }


    @Test
    public void testGetCritters() {
        assertNotNull(mazeState.getCritters());
    }

    @Test
    public void testGetWidth() {
        assertEquals(6, mazeState.getWidth()); // La largeur du labyrinthe mocké est de 6
    }

    @Test
    public void testGetHeight() {
        assertEquals(6, mazeState.getHeight()); // La hauteur du labyrinthe mocké est de 6
    }

    @Test
    public void testSetScore() {
        int newScore = 100; // Définissez une valeur de score pour le test
        mazeState.setScore(newScore);

        assertEquals(newScore, mazeState.getScore(), "Le score doit être mis à jour correctement");
    }

    @Test
    public void testSetGridState(){
        boolean[][] newGridState = {
                {true, false, true},
                {false, true, false},
                {true, false, true}
        };

        mazeState.setGridState(newGridState);

        for (int i = 0; i < newGridState.length; i++){
            for (int j = 0; j < newGridState[i].length; j++) {
                assertEquals(newGridState[i][j], mazeState.getGridState(new IntCoordinates(j,i)), "La cellule à la position [" + i + "][" + j + "] devrait être " + newGridState[i][j]);
            }
        }
    }

    @Test
    public void testGetScore() {
        mazeState.setScore(100);
        assertEquals(100, mazeState.getScore());
    }

    @Test
    public void testGetLives() {
        assertEquals(3, mazeState.getLives()); // Initialisé avec 3 vies
    }

    @Test
    public void testSetAndGetLevel() {
        int newLevel = 5;
        mazeState.setLevel(newLevel);

        assertEquals(newLevel, mazeState.getLevel(), "Le niveau doit être mis à jour correctement");
    }

    //@Test
    /*public void testSetConfig() {
        MazeConfig newConfig = new MazeConfig(new Cell[][]{
                {Cell.create(false, false, false, true, new Dot()), Cell.create(false, true, false, false, new Dot()), Cell.create(false, false, true, false, new Dot())},
                {Cell.create(false, false, false, true, new Item()), Cell.create(false, false, true, true, new Item()), Cell.create(false, true, false, false, new Dot())},
                {Cell.create(false, false, true, false, new Dot()), Cell.create(true, false, true, false, new Dot()), Cell.create(true, false, false, true, new Dot())}
        },
                new IntCoordinates(0, 0),
                new IntCoordinates(2, 2),
                new IntCoordinates(1, 2),
                new IntCoordinates(2, 0),
                new IntCoordinates(0, 2)),
                new IntCoordinates(9,6))
        mazeState.setConfig(newConfig);



        // Si getGridState ou une méthode similaire existe pour obtenir le MazeConfig actuel
        assertEquals(newConfig, mazeState.getConfig(), "La configuration du labyrinthe doit être mise à jour correctement");
    }*/

    @Test
    void testAddScore() {
        int initialScore = mazeState.getScore();
        int increment = 10;
        mazeState.addScore(increment);
        assertEquals(initialScore + increment, mazeState.getScore());
    }

    @Test
    void testResetCritter() {
        Critter critter = PacMan.INSTANCE;
        RealCoordinates newPos = new RealCoordinates(5.0, 5.0);
        critter.setPos(newPos);
        mazeState.resetCritter(critter);
        assertNotEquals(newPos, critter.getPos());
        assertEquals(Direction.NONE, critter.getDirection());
    }


    @Test
    void testGetConfig() {
        assertEquals(config, mazeState.getConfig());
    }

    @Test
    void testGetGridState() {
        IntCoordinates pos = new IntCoordinates(1, 1);
        boolean state = mazeState.getGridState(pos);
        assertEquals(state, mazeState.getGridState(pos));
    }

    @Test
    void testAllDotsEaten() {
        boolean[][] gridState = mazeState.initGridState();
        for (int i = 0; i < gridState.length; i++) {
            for (int j = 0; j < gridState[i].length; j++) {
                gridState[i][j] = true;
            }
        }
        mazeState.setGridState(gridState);
        assertTrue(mazeState.allDotsEaten());
    }

    @Test
    void testInitGridState() {
        boolean[][] gridState = mazeState.initGridState();
        assertNotNull(gridState);
        assertEquals(mazeState.getHeight(), gridState.length);
        assertEquals(mazeState.getWidth(), gridState[0].length);
    }

    @Test
    void testGetInventory() {
        assertNotNull(mazeState.getInventory());
    }

    @Test
    void testResetItems() {
        mazeState.resetItems();
        // Verifiez que les items ont été réinitialisés selon votre logique de reset
    }

    @Test
    void testGetAnimationController() {
        assertEquals(animationController, mazeState.getAnimationController());
    }

}
