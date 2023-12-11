import gui.AnimationController;
import gui.GameView;
import javafx.scene.layout.Pane;
import model.MazeState;

public class DummyAnimationController extends AnimationController {
    private boolean gameOverCalled = false;

    public DummyAnimationController(MazeState maze, Pane gamePane) {

        super(null, null, null, new GameView(maze, gamePane, 1), null, 0);
    }

    @Override
    public void gameOver() {
        gameOverCalled = true;
    }

    public boolean isGameOverCalled() {
        return gameOverCalled;
    }
}
