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
import config.Cell.Content;
import geometry.IntCoordinates;
import geometry.RealCoordinates;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

import static model.Ghost.*;

public final class MazeState {
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
         * 3. déléguer certaines repsonsabilités à d'autres méthodes ?
         */

        for (var critter: critters){
            critter.tpToCenter();
            var nextDir = critter.getNextDir();
            if(critter == PacMan.INSTANCE){
                if(PacMan.INSTANCE.canSetDirection(nextDir, this.config)){
                    critter.setPos(critter.getNextPos(deltaTns, nextDir, this.config));
                    critter.setDirection(nextDir);
                } else {
                    critter.setPos(critter.getNextPos(deltaTns, critter.getDirection(), this.config));
                }
            } else {
                critter.setPos(critter.getNextPos(deltaTns, nextDir, this.config));
                critter.setDirection(nextDir);
            }

            if ( PacMan.INSTANCE.isEnergized() ){ // Si pacman est en mode energizer (DEBUT)

                
                
                if ( PacMan.INSTANCE.getPos().x()==critter.getPos().x() ){ //Si pacman et un fantome sont sur un meme axe vertical
                    if (PacMan.INSTANCE.getPos().y() > critter.getPos().y() 
                        && PacMan.INSTANCE.getDirection()==Direction.NORTH ){
                            nextDir = PacMan.INSTANCE.getNextDir();
                            critter.setPos(critter.getNextPos(deltaTns, nextDir, config));
                            critter.setDirection(nextDir);

                    }
                    if (PacMan.INSTANCE.getPos().y() < critter.getPos().y() 
                        && PacMan.INSTANCE.getDirection()==Direction.SOUTH ){
                            nextDir = PacMan.INSTANCE.getNextDir();
                            critter.setPos(critter.getNextPos(deltaTns, nextDir, config));
                            critter.setDirection(nextDir);

                    }
                }

                if ( PacMan.INSTANCE.getPos().y()==critter.getPos().y() ){ //Si pacman et un fantome sont sur un meme axe horizental
                    if (PacMan.INSTANCE.getPos().x() > critter.getPos().x() 
                        && PacMan.INSTANCE.getDirection()==Direction.WEST ){
                            nextDir = PacMan.INSTANCE.getNextDir();
                            critter.setPos(critter.getNextPos(deltaTns, nextDir, config));
                            critter.setDirection(nextDir);

                    }
                    if (PacMan.INSTANCE.getPos().x() < critter.getPos().x() 
                        && PacMan.INSTANCE.getDirection()==Direction.EAST ){
                            nextDir = PacMan.INSTANCE.getNextDir();
                            critter.setPos(critter.getNextPos(deltaTns, nextDir, config));
                            critter.setDirection(nextDir);

                    }
                }
                
                if ( PacMan.INSTANCE.getPos().y()!=critter.getPos().y()
                        &&  PacMan.INSTANCE.getPos().x()!=critter.getPos().x() ){ /* si pacman et un fantome
                             ne sont ni sur un meme axe vertical ni sur un meme axe horizental */

                            if ( PacMan.INSTANCE.getPos().y() < critter.getPos().y() 
                                && PacMan.INSTANCE.getPos().x() < critter.getPos().x() ){ // cas de pac man au dessus d'un fantome a gauche
                                
                                if ( PacMan.INSTANCE.getPos().y() - critter.getPos().y() >= -1
                                    && PacMan.INSTANCE.getPos().x() - critter.getPos().x() >= -1
                                    && PacMan.INSTANCE.getDirection()==Direction.SOUTH ) {/*si la différence d'ordonnées 
                                    entre pacman et le ghost est inférieure ou égale à 3 et pacman se dirige vers le sud */
                                        
                                        nextDir = PacMan.INSTANCE.getNextDir();
                                        critter.setPos(critter.getNextPos(deltaTns, nextDir, config));
                                        critter.setDirection(nextDir);// le ghost se dirige vers le sud aussi

                                        if ( config.getCell(critter.currCellI()).southWall() ){ /* Si le ghost se trouve bloqué
                                        contre un mur au sud */
                                            if ( PacMan.INSTANCE.getPos().x() < critter.getPos().x() ){ /*Si pacman 
                                            est toujours à gauche du ghost */
                                                if ( !config.getCell(critter.currCellI()).eastWall() ) { /*si le ghost n'est pas 
                                                        bloque a droite */
                                                    nextDir = Direction.EAST;
                                                    critter.setPos(critter.getNextPos(deltaTns, nextDir, config));
                                                    critter.setDirection(nextDir);
                                                }
                                                else { /*sinon il se dirige vers le nord */
                                                    nextDir = Direction.NORTH;
                                                    critter.setPos(critter.getNextPos(deltaTns, nextDir, config));
                                                    critter.setDirection(nextDir);
                                                }
                                            }
                                        }
                                    }
                            }
                        }


            }
        }

        // FIXME Pac-Man rules should somehow be in Pacman class
        var pacPos = PacMan.INSTANCE.getPos().round();
        if (!gridState[pacPos.y()][pacPos.x()]) { // Energizer
            if(config.getCell(pacPos).initialContent()==Content.ENERGIZER){ /* score energizer */
                addScore(5); 
                PacMan.INSTANCE.setEnergized();
                
            }
            else {
                addScore(1);
            }
            gridState[pacPos.y()][pacPos.x()] = true;
        }
        for (var critter : critters) { // Collision PacMan Ghosts
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
                if (PacMan.INSTANCE.isEnergized()) {
                    addScore(10);
                    resetCritter(critter);
                } else {
                    playerLost(); //FIXME : UNCOMMENT ME !!!
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
        //System.out.println(PacMan.INSTANCE.isEnergized());
    }

    private void playerLost() {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
        lives--;
        if (lives == 0) {
            System.out.println("Game over!");
            System.exit(0);
        }
        System.out.println("Lives: " + lives);
        resetCritters();
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
    }

    private void resetCritters() {
        for (var critter: critters) resetCritter(critter);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }
}
