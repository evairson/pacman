package gui;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MazeState;

import java.util.List;

/**
 * Cette classe est responsable de la gestion des événements durant l'animation et de l'animation elle-même.
 */
public class AnimationController {
    private final List<GraphicsUpdater> graphicsUpdaters;
    private final MazeState maze;
    private final Stage primaryStage;

    private final PacmanController pacmanController;

    private PauseMenu pauseMenu;

    private GameView gameView;
    private boolean isPaused = false;



    public AnimationController(List<GraphicsUpdater> graphicsUpdaters, MazeState maze, Stage primaryStage, PacmanController pacmanController, GameView gameView) {
        this.graphicsUpdaters = graphicsUpdaters;
        this.maze = maze;
        this.primaryStage = primaryStage;
        this.pacmanController = pacmanController;
        this.gameView = gameView;
        pauseMenu = new PauseMenu(pacmanController,gameView.getMaze());
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void startPauseMenu(){
        pauseMenu.startMenu();
    }
    public void stopPauseMenu(){
        pauseMenu.stopMenu();
    }


    public void blurGame(){
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        gameView.getGameRoot().setEffect(adj);
    }
    public void unBlurGame(){
        gameView.getGameRoot().setEffect(null);
    }

    public void gameOver(){
        try {
            //Démarre une pause
            this.startPauseMenu();
            this.blurGame();
            this.pauseScheduled = true;
            this.setPaused(true);
            pauseMenu.getStage().hide();

            //Affiche le game over
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            BorderPane layout = new BorderPane();
            layout.setMinWidth(pauseMenu.getWidth());
            layout.setMinHeight(pauseMenu.getHeight());
            layout.setMaxWidth(pauseMenu.getWidth());
            layout.setMaxHeight(pauseMenu.getHeight());

            layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

            Text gameOver = new Text("GAME OVER");
            gameOver.setFill(Color.RED);
            gameOver.setStyle("-fx-font-size: 50;-fx-font-family: Serif");

            layout.setCenter(gameOver);

            Scene gameOverScene = new Scene(layout);
            gameOverScene.setFill(Color.TRANSPARENT);

            stage.setScene(gameOverScene);
            stage.centerOnScreen();
            stage.setAlwaysOnTop(true);
            stage.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    //Ces 4 attributs permettent de gérer la pause
    private long pauseStart; //Quand est-ce que la pause a commencé
    public boolean pauseScheduled = false; // Y-a-t'il une pause de prévue ?
    public boolean playScheduled = false; // Y a-t'il un unpause de prévu ?

    // Méthode pour démarrer l'animation
    public AnimationTimer createAnimationTimer() {
        return new AnimationTimer() {

            long animationStart = 0;


            @Override
            public void handle(long now) { //La fonction handle est celle appelée à chaque frame du jeu
                if(pauseScheduled){
                    pauseStart = now;
                    pauseScheduled = false;
                }
                if(playScheduled){
                    animationStart += (now - pauseStart);
                    playScheduled = false;
                }

                if(!isPaused){
                    if (animationStart == 0) { // ignore the first tick, just compute the first deltaT
                        animationStart = now;
                        return;
                    }
                    long deltaT = now - animationStart;
                    deltaT = now - animationStart;
                    maze.update(deltaT);
                    for (GraphicsUpdater updater : graphicsUpdaters) {
                        updater.update();
                    }
                    animationStart = now;
                }
            }
        };

    }
}
