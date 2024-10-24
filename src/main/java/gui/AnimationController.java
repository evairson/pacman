package gui;

import config.MazeConfig;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ghost;
import model.MazeState;
import java.io.IOException;

import java.util.*;

/**
 * Cette classe est responsable de la gestion des événements durant l'animation et de l'animation elle-même.
 */
public class AnimationController {
    private List<GraphicsUpdater> graphicsUpdaters;
    private MazeState maze;
    private final Stage primaryStage;

    private final PauseMenu pauseMenu;

    private GameView gameView;
    private HUDView hudView;
    private final StackPane gameComponents;
    private boolean isPaused = false;
    private boolean isInUnstoppableAnimation = false;
    private boolean isFancy = false;
    private final double AppScale;
    private boolean hasntAlreadyWon = true; //Aide à gérer les transitions de niveau
//    AudioClip defaultSiren = new AudioClip(getClass().getResource("/audio/assassindelapolice.mp3").toExternalForm());
    private boolean energizedSirenIsPlaying = false;
//    AudioClip energizedSiren = new AudioClip(getClass().getResource("/audio/assassindelapolice2.mp3").toExternalForm());




    public AnimationController(List<GraphicsUpdater> graphicsUpdaters, MazeState maze, Stage primaryStage, GameView gameView, StackPane root, HUDView hudView, double AppScale) {
        this.graphicsUpdaters = graphicsUpdaters;
        this.maze = maze;
        this.primaryStage = primaryStage;
        this.gameView = gameView;
        this.gameComponents = root;
        this.AppScale = AppScale;
        this.hudView = hudView;
        pauseMenu = new PauseMenu(gameView.getMaze(), root,this);

    }

    public Stage getPrimaryStage() {
        return primaryStage;
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
        this.startPause();
    }
    public void stopPauseMenu(){
        if (isFancy){
            unBlurGame();
        }
        pauseMenu.stopMenu();
        this.stopPause();
    }
    public void startPause(){
        this.pauseScheduled = true;
        this.setPaused(true);
    }
    public void stopPause(){
        this.playScheduled = true;
        this.setPaused(false);
    }

    public boolean isInUnstoppableAnimation() {
        return isInUnstoppableAnimation;
    }

    public void setFancy(boolean fancy) {
        isFancy = fancy;
    }

    public boolean hasntAlreadyWon() {
        return hasntAlreadyWon;
    }

    public void setHasntAlreadyWon(boolean hasntAlreadyWon) {
        this.hasntAlreadyWon = hasntAlreadyWon;

    }

    public void setEnergizedSirenIsPlaying(boolean energizedSirenIsPlaying) {
        this.energizedSirenIsPlaying = energizedSirenIsPlaying;
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
            if (isFancy){ //Ne pas appeler blur car pc pas assez puissant => crash
                this.blurGame();
            }

            this.startPause();
            this.isInUnstoppableAnimation = true;

            Font.loadFont(AnimationController.class.getResourceAsStream("Crackman.otf"), 12);

            //Affiche le game over
            BorderPane layout = new BorderPane();

            layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

            Text gameOver = new Text("GAME OVER");
            gameOver.setFill(Color.RED);
            gameOver.setFont(Font.font("Crackman", 50));

            layout.setCenter(gameOver);
            gameComponents.getChildren().add(layout);

            final Stage stage = this.primaryStage;

            //Ferme le programme 3s après le game over
            Timer timer = new Timer();
            TimerTask task = new TimerTask() { //Infâme mais fonctionnel (voir comment utiliser Timeline)
                @Override
                public void run() {
                    Platform.runLater(() -> App.restartApplication(stage));
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
            /*if (isFancy){
                this.blurGame();
            }*/ //TODO : revoir ?
            this.startPause();
            this.isInUnstoppableAnimation = true;
            //Affiche le winscreen
            BorderPane winScreen = new BorderPane();

            winScreen.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

            Text gameOver = new Text("YOU WIN");
            gameOver.setFill(Color.GREEN);
            gameOver.setFont(Font.font("Crackman", 50));

            winScreen.setCenter(gameOver);
            gameComponents.getChildren().add(winScreen);

            CellGraphicsFactory.setFinNiveau(true);

            //Ferme le programme 3s après la win
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {

                gameComponents.getChildren().remove(gameView.getGameRoot());
                this.stopPause();
                this.isInUnstoppableAnimation = false;
                gameComponents.getChildren().remove(winScreen);
                try {
                    transitionLvl(getNextLevel(this.maze.getLevel()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
            timeline.setCycleCount(1);
            timeline.play();
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
                    maze.update(deltaT);
                    animationStart = now;
                }
                //Ce morceau de boucle permet de tout mettre à jour
                //La non mise à jour de la variable animationStart permet au jeu de ne pas se dérouler (seuls les murs s'animeront)
                for(GraphicsUpdater updater : graphicsUpdaters){
                    updater.update();
                }
            }
        };
    }

    public void transitionLvl(int nextLevel) throws IOException {
        MazeState maze = new MazeState(Objects.requireNonNull(MazeConfig.makeGenericExample(nextLevel))); //Crée une nouvelle mazestate qui correspond à la nouvelle map
        maze.setScore(this.maze.getScore());
        maze.setAnimationController(this);
        maze.setLevel(nextLevel);
        maze.setScore(this.maze.getScore());
        maze.setLives(this.maze.getLives());
        this.maze = maze;
        this.graphicsUpdaters.remove(this.hudView.getHudUpdater());
        this.hudView.changeMaze(this.maze);
        this.graphicsUpdaters.add(this.hudView.getHudUpdater());


        this.gameView.getGameRoot().getChildren().clear(); //Clear l'ancien panneau de jeu
        GameView gameView1 = new GameView(maze, gameView.getGameRoot(), AppScale); //Crée une nouvelle vue de jeu
        this.gameView = gameView1;
        gameView1.getGraphicsUpdaters().add(this.graphicsUpdaters.get(this.graphicsUpdaters.size() - 1)); // Ajout du hud updater
        this.graphicsUpdaters = gameView1.getGraphicsUpdaters();
        gameComponents.getChildren().add(gameView.getGameRoot()); //Ajoute la nouvelle map à l'affichage
        Ghost.setAllEnergizedValue(false);
        // ajout vies

        this.hasntAlreadyWon = true; //Remet le paramètre pour la transition de level
        setPaused(false);
        this.unBlurGame();
    }

    // Sound controlling methods

    public void ghostEatenSound() {
        AudioClip eaten = new AudioClip(AnimationController.class.getResource("pacManGhostEaten.mp3").toExternalForm());
        eaten.setVolume(0.1);
        eaten.play();
    }

    public void mainTheme() {
        AudioClip main = new AudioClip(AnimationController.class.getResource("pacmanThemeOriginal.mp3").toExternalForm());
        main.setVolume(0.1);
        main.play();
    }

    public static int getNextLevel(int x){
        if(x == 3) return 1;
        else return ++x;
    }

    /*public static AnimationController mockAnimationController() {
        List<GraphicsUpdater> mockGraphicsUpdaters = Collections.emptyList(); // Liste vide pour simplifier
        MazeState mockMazeState = new MazeState(MazeConfig.mockExample()); // Utiliser MazeConfig.mockExample pour créer un état de labyrinthe
        Stage mockPrimaryStage = new Stage(); // Stage vide
        StackPane mockRoot = new StackPane();
        Pane mockPane = new StackPane(); // Pane vide pour la structure de l'interface utilisateur
        double appScale = 1.0; // Valeur par défaut pour l'échelle de l'application
        GameView mockGameView = new GameView(mockMazeState, mockPane, appScale); // Instance de GameView avec dépendances mockées

        return new AnimationController(mockGraphicsUpdaters, mockMazeState, mockPrimaryStage, mockGameView, mockRoot, appScale);
    }*/

    /* C'est à chier pour l'instant
    public void siren() {
        if (energizedSirenIsPlaying) {
            defaultSiren.stop();
        } else {
            defaultSiren.play();
        }
    } */
}
