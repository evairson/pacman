import gui.AnimationController;
import gui.GameView;
import gui.HUDView;
import javafx.scene.layout.Pane;
import model.MazeState;

public class DummyAnimationController extends AnimationController {
    private boolean gameOverCalled = false;

    public DummyAnimationController(MazeState maze, Pane gamePane) {
        super(null, null, null, new GameView(maze, gamePane, 1), null, new HUDView(maze, new Pane(), maze.getWidth() * 0.8, maze.getHeight() * 0.8 * 0.25,1),0);
    }

    @Override
    public void gameOver() {
        gameOverCalled = true;
    }

    public boolean isGameOverCalled() {
        return gameOverCalled;
    }
}
