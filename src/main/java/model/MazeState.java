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

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

import static model.Ghost.*;

public final class MazeState {

    private AnimationController animationController;
    private final MazeConfig config;
    private final int height;
    private final int width;

    private final boolean[][] gridState;

    private final List<Critter> critters;
    private int score;

    private final Map<Critter, RealCoordinates> initialPos;
    private int lives = 3;

    public MazeState(MazeConfig config) {
        this.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, Ghost.CLYDE, BLINKY, INKY, PINKY);
        gridState = new boolean[height][width];
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

    public int getScore() {
        return score;
    }
    public int getLives() {
        return lives;
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

        for (Critter critter: critters){
            critter.tpToCenter();
            if(critter == PacMan.INSTANCE){
                Direction nextDir = ((PacMan) critter).getNextDir();
                if(PacMan.INSTANCE.canSetDirection(nextDir, this.config)){
                    critter.setPos(critter.getNextPos(deltaTns, nextDir, this.config));
                    critter.setDirection(nextDir);
                } else {
                    critter.setPos(critter.getNextPos(deltaTns, critter.getDirection(), this.config));
                }
            } else {
                var nextDir = ((Ghost) critter).getNextDir(this.config, PacMan.INSTANCE.currCellI(), PacMan.INSTANCE.getDirection(), PacMan.INSTANCE.isEnergized(),PacMan.INSTANCE.isFakeEnergized());
                critter.setPos(critter.getNextPos(deltaTns, nextDir, this.config));
                critter.setDirection(nextDir);
            }
        }

        // FIXME Pac-Man rules should somehow be in Pacman class
        var pacPos = PacMan.INSTANCE.getPos().round();
        
        if (!gridState[pacPos.y()][pacPos.x()]) { // Energizer
            if(config.getCell(pacPos).initialItem() instanceof Energizer){ /* score energizer */
                addScore(5); 
                Energizer.setEnergized(true);
                
            }
            else if (config.getCell(pacPos).initialItem() instanceof FakeEnergizer){
                FakeEnergizer.setFakeEnergized(true); 
            }
            else {
                addScore(1);
            }
            gridState[pacPos.y()][pacPos.x()] = true;
        }
        for (var critter : critters) { // Collision PacMan Ghosts
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos) && !PacMan.INSTANCE.isFakeEnergized() ) {
                if (PacMan.INSTANCE.isEnergized() ) {
                    addScore(10);
                    resetCritter(critter);
                } else {
                    playerLost(); 
                    return;
                }
            }
        }
    }

    private void addScore(int increment) {
        score += increment;
        displayScore();
    }

    private void displayScore() {
        // FIXME: this should be displayed in the JavaFX view, not in the console
        System.out.println("Score: " + score);
    }

    private void playerLost() { //le joueur a perdu au moment où il n'a plus de vie
        // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
        lives--;
        if (lives == 0) {
            System.out.println("Game over!");
            System.exit(0);
            //animationController.gameOver();
        }
        System.out.println("Lives: " + lives);
        //if (!PacMan.INSTANCE.isFakeEnergized()){
            resetCritters();
        //}
    
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
    }

    private void resetCritters() {
        for (Critter critter: critters) resetCritter(critter);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }
}
