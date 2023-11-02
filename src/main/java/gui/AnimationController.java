package gui;

import javafx.animation.AnimationTimer;

public class AnimationController {
    private final AnimationTimer animationTimer;
    private boolean isPaused = false;

    public AnimationController(AnimationTimer animationTimer) {
        this.animationTimer = animationTimer;
    }

    public AnimationTimer getAnimationTimer() {
        return animationTimer;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
