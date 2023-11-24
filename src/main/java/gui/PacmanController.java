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
                }
                else{
                    animationController.startPauseMenu();
                }
            }
            case U -> { PacMan.INSTANCE.getInventory().getNth(0).setActive(true); PacMan.INSTANCE.getInventory().remove(0);}
            default -> {
                System.out.println(event.getCode());
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
