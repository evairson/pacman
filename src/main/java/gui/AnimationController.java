package gui;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.MazeState;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private final StackPane gameComponents;
    private boolean isPaused = false;
    private boolean isFancy = false;

    public AnimationController(List<GraphicsUpdater> graphicsUpdaters, MazeState maze, Stage primaryStage, PacmanController pacmanController, GameView gameView, StackPane root) {
        this.graphicsUpdaters = graphicsUpdaters;
        this.maze = maze;
        this.primaryStage = primaryStage;
        this.pacmanController = pacmanController;
        this.gameView = gameView;
        this.gameComponents = root;
        pauseMenu = new PauseMenu(gameView.getMaze(), root);
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void startPauseMenu(){
        if (isFancy){
            blurGame();
        }
        pauseMenu.startMenu(isFancy);
    }
    public void stopPauseMenu(){
        if (isFancy){
            unBlurGame();
        }
        pauseMenu.stopMenu();
    }

    public boolean isFancy() {
        return isFancy;
    }

    public void setFancy(boolean fancy) {
        isFancy = fancy;
    }

    public void blurGame(){
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(10);
        adj.setInput(blur);
        gameView.getGameRoot().setEffect(adj);
    }
    public void unBlurGame(){
        gameView.getGameRoot().setEffect(null);
    }

    public void gameOver(){
        try {
            //Démarre une pause
            //this.blurGame(); //Ne pas appeler blur car pc pas assez puissant => crash
            this.pauseScheduled = true;
            this.setPaused(true);

            //Affiche le game over
            BorderPane layout = new BorderPane();

            layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

            Text gameOver = new Text("GAME OVER");
            gameOver.setFill(Color.RED);
            gameOver.setStyle("-fx-font-size: 50;-fx-font-family: Serif");

            layout.setCenter(gameOver);
            gameComponents.getChildren().add(layout);

            //Ferme le programme 5s après le game over
            Timer timer = new Timer();
            TimerTask task = new TimerTask() { //Infâme mais fonctionnel (voir comment utiliser Timeline)
                @Override
                public void run() {
                    System.exit(0);
                }
            };

            timer.schedule(task,3000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void win(){
        try {
            //Démarre une pause
            //this.blurGame(); //Ne pas appeler blur car pc pas assez puissant => crash
            this.pauseScheduled = true;
            this.setPaused(true);

            //Affiche le game over
            BorderPane layout = new BorderPane();

            layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

            Text gameOver = new Text("YOU WIN");
            gameOver.setFill(Color.GREEN);
            gameOver.setStyle("-fx-font-size: 50;-fx-font-family: Serif");

            layout.setCenter(gameOver);
            gameComponents.getChildren().add(layout);

            //Ferme le programme 5s après le game over
            Timer timer = new Timer();
            TimerTask task = new TimerTask() { //Infâme mais fonctionnel (voir comment utiliser Timeline)
                @Override
                public void run() {
                    System.exit(0);
                }
            };

            timer.schedule(task,3000);
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
                    var deltaT = now - animationStart;
                    deltaT = now - animationStart;
                    maze.update(deltaT);
                    for (var updater : graphicsUpdaters) {
                        updater.update();
                    }
                    animationStart = now;
                }
            }
        };
    }
}
