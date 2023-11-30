package model;

/**
 * Cette classe représente l'état du labyrinthe.
 * Elle contient les informations suivantes :
 * - la configuration du labyrinthe (cf. {@link config.MazeConfig})
 * - la gestion des collisions entre les différents éléments du labyrinthe
 * - le score du joueur
 * - le nombre de vies restantes
 * - la position initiale de chaque élément du labyrinthe
 */

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.AnimationController;

import gui.CellGraphicsFactory;
import model.Items.Energizer;
import model.Items.FakeEnergizer;
import model.Items.Item;
import model.Items.ItemTest;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

import static model.Ghost.*;

public final class MazeState {

    private AnimationController animationController;
    private MazeConfig config;
    private final int height;
    private final int width;

    private boolean[][] gridState;

    private final List<Critter> critters;
    private int score;

    private int level = 1;
    private final Map<Critter, RealCoordinates> initialPos;
    private int lives = 3;

    public MazeState(MazeConfig config) {
        this.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, Ghost.CLYDE, BLINKY, INKY, PINKY);
        gridState = initGridState();
        initialPos = Map.of(
                PacMan.INSTANCE, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0)
        );
        resetCritters();
    }

    public List<Critter> getCritters() {
        return critters;
    }

    public double getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setConfig(MazeConfig config) {
        this.config = config;
    }

    public void setAnimationController(AnimationController animationController) {
        this.animationController = animationController;
    }

    public void update(long deltaTns) {

        /**
         * Reponsable de mettre à jour l'état du jeu.
         * Cette méthode est appelée à chaque frame.
         * Elle gère les collisions entre les différents éléments du labyrinthe.
         * Elle met à jour la position de chaque élément du labyrinthe.
         * Elle met à jour le score du joueur.
         * Elle met à jour le nombre de vies restantes.
         *
         *
         * BEAUCOUP DE CHOSES À FAIRE :
         * 1. Intégrer JavaFX pour afficher le score, le nombre de vies restantes, etc.
         * 2. Afficher un écran de fin de jeu, plutôt que d'appeler un vilain System.exit(0)
         *    message de fin de jeu + permettre au joueur de recommencer ou de quitter le jeu.
         *    (cf. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html)
         *    (cf. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html)
         * 3. déléguer certaines responsabilités à d'autres méthodes ?
         */

        for (Critter critter : critters) {
            critter.tpToCenter();
            if (critter == PacMan.INSTANCE) {
                Direction nextDir = ((PacMan) critter).getNextDir();
                if (PacMan.INSTANCE.canSetDirection(nextDir, this.config)) {
                    critter.setPos(critter.getNextPos(deltaTns, nextDir, this.config));
                    critter.setDirection(nextDir);
                } else {
                    critter.setPos(critter.getNextPos(deltaTns, critter.getDirection(), this.config));
                }
            } else {
                var nextDir = ((Ghost) critter).getNextDir(this.config, PacMan.INSTANCE.currCellI(), PacMan.INSTANCE.getDirection(), PacMan.INSTANCE.isEnergized(), PacMan.INSTANCE.isFakeEnergized());
                critter.setPos(critter.getNextPos(deltaTns, nextDir, this.config));
                critter.setDirection(nextDir);
            }
        }

        // FIXME Pac-Man rules should somehow be in Pacman class
        var pacPos = PacMan.INSTANCE.getPos().round();

        if (!gridState[pacPos.y()][pacPos.x()]) { // Energizer
            if (config.getCell(pacPos).initialItem() instanceof Energizer) { /* score energizer */
                addScore(5);
                config.getCell(pacPos).initialItem().setActive(true);
                gridState[pacPos.y()][pacPos.x()] = true;
            } else if (config.getCell(pacPos).initialItem() instanceof FakeEnergizer) {
                FakeEnergizer.setFakeEnergized(true);
                gridState[pacPos.y()][pacPos.x()] = true;
            } else {
                if (config.getCell(pacPos).initialItem().isCollectable()) {
                    if (!PacMan.INSTANCE.getInventory().isFull()) {
                        PacMan.INSTANCE.getInventory().add(config.getCell(pacPos).initialItem());
                        addScore(10);
                        gridState[pacPos.y()][pacPos.x()] = true;
                    }
                } else {
                    config.getCell(pacPos).initialItem().setActive(true);
                    addScore(1);
                    gridState[pacPos.y()][pacPos.x()] = true;
                }
            /*if(config.getCell(pacPos).initialItem() instanceof Energizer){
                addScore(5); 
                Energizer.setEnergized(true);
                gridState[pacPos.y()][pacPos.x()] = true;
            } else if(config.getCell(pacPos).initialItem() instanceof ItemTest){
                if(!PacMan.INSTANCE.getInventory().isFull()){
                    addScore(10);
                    PacMan.INSTANCE.getInventory().add(config.getCell(pacPos).initialItem());
                    //ItemTest.setActive(true);
                    gridState[pacPos.y()][pacPos.x()] = true;
                    System.out.println(PacMan.INSTANCE.getInventory());
                }
            } else {
>>>>>>> iss33-inventory
                addScore(1);
                gridState[pacPos.y()][pacPos.x()] = true;
            }*/
            }
            } // TODO: Faire une fonction dans item qui fait tout bien (le ramssage) pour chaque item (pour éviter d'écrire 'if ... instanceof ...') et qui ne met pas grid true si l'item n'est pas ramassé...
            for (var critter : critters) { // Collision PacMan Ghosts
                if (critter instanceof Ghost && critter.getPos().round().equals(pacPos) && !PacMan.INSTANCE.isFakeEnergized()) {
                    if (PacMan.INSTANCE.isEnergized()) {
                        addScore(10);
                        animationController.ghostEatenSound();
                        resetCritter(critter);
                    } else {
                        playerLost();
                        return;
                    }
                }
            }
            if (allDotsEaten() && animationController.hasntAlreadyWon()) {
                System.out.println("caca");
                if (level == 2) playerWin();
                animationController.setHasntAlreadyWon(false);
                animationController.win();
            }
        }

        public int getHighScore () {
            try {
                var scanner = new Scanner(new File(System.getProperty("user.dir") + "/src/main/resources/highscore.txt"));
                return scanner.nextInt();
            } catch (FileNotFoundException e) {
                return -1;
            }
        }

        public void setHighScore (int score){
            try {
                var writer = new PrintWriter(new File(System.getProperty("user.dir") + "/src/main/resources/highscore.txt"));
                writer.println(score);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void addScore ( int increment){
            score += increment;
            displayScore();
        }

        private void displayScore() {
            // FIXME: this should be displayed in the JavaFX view, not in the console
        }

        private void playerLost() { //le joueur a perdu au moment où il n'a plus de vie
            // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
            lives--;
            if (lives == 0) {
                if (score > getHighScore()) {
                    setHighScore(score);
                }
                System.exit(0);
                animationController.gameOver();
            }
            //if (!PacMan.INSTANCE.isFakeEnergized()){
            resetCritters();
            //}

        }

        private void playerWin () {
            animationController.win();
        }

        private void resetCritter (Critter critter){
            critter.setDirection(Direction.NONE);
            critter.setPos(initialPos.get(critter));
        }

        private void resetCritters () {
            for (Critter critter : critters) resetCritter(critter);
        }

        public MazeConfig getConfig () {
            return config;
        }

        public boolean getGridState (IntCoordinates pos){
            return gridState[pos.y()][pos.x()];
        }

        public boolean allDotsEaten () {
            for (boolean[] row : gridState) {
                for (boolean cell : row) {
                    if (!cell) {
                        return false;
                    }
                }
            }
            return true;
        }

        public boolean[][] initGridState() {
            boolean[][] gridState = new boolean[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (config.getCell(new IntCoordinates(j, i)).initialItem().getClass().equals(Item.class)) {
                        gridState[i][j] = true;
                    }
                }
            }
            return gridState;
        }

}
