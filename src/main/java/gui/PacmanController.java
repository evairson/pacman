package gui;
/**
 * Classe pour le controleur du Pacman
 * keyPressedHandler : récupère la touche appuyée et change la direction du Pacman
 * avec event.getCode() qui renvoie la touche appuyée
 * keyReleasedHandler : rien à faire 
 */

import model.Direction;
import model.PacMan;

import javafx.scene.input.KeyEvent;

public class PacmanController {

    private AnimationController animationController;

    public void keyPressedHandler(KeyEvent event) {

        switch (event.getCode()){
            case ESCAPE -> {
                if(animationController.isPaused()) {
                    animationController.stopPauseMenu();
                    animationController.unBlurGame();
                    animationController.playScheduled = true;
                    animationController.setPaused(false);
                }
                else{
                    animationController.startPauseMenu();
                    animationController.blurGame();
                    animationController.pauseScheduled = true;
                    animationController.setPaused(true);
                }
            }
            default -> {
                PacMan.INSTANCE.setNextDir(
                        switch (event.getCode()) {
                            case LEFT -> Direction.WEST;
                            case RIGHT -> Direction.EAST;
                            case UP -> Direction.NORTH;
                            case DOWN -> Direction.SOUTH;
                            default -> PacMan.INSTANCE.getDirection();
                        }
                );
            }
        }
    }
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing for now
    }

    public AnimationController getAnimationController() {
        return animationController;
    }

    public void setAnimationController(AnimationController animationController) {
        this.animationController = animationController;
    }
}
