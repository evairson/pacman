import gui.AnimationController;

public class DummyAnimationController extends AnimationController {
    private boolean gameOverCalled = false;

    public DummyAnimationController() {
        super(null, null, null, null, null, 0);
    }

    @Override
    public void gameOver() {
        gameOverCalled = true;
    }

    public boolean isGameOverCalled() {
        return gameOverCalled;
    }
}
