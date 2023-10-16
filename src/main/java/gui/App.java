package gui;

/**
 * Cette classe est le point d'entrée de l'application.
 * Elle crée une fenêtre avec un labyrinthe et un Pacman.
 * Elle crée aussi un listener d'événements clavier pour le Pacman.
 * Elle évite la méthode start() qui est appelée automatiquement par JavaFX.
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import config.MazeConfig;
import model.MazeState;

import java.io.IOException;

public class App extends Application {
    @Override
    /**
     * Crée une fenêtre avec un labyrinthe et un Pacman.
     * @param primaryStage la fenêtre principale
     *                     (voir https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html)
     *                     pour la documentation de la classe Stage)
     *                     (voir https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html)
     *                     pour la documentation de la classe Scene)
     *                     (voir https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/Pane.html)
     *                     pour la documentation de la classe Pane)
     */
    public void start(Stage primaryStage) throws IOException {
        // Pane est un conteneur qui peut contenir des éléments graphiques
        var root = new Pane();
        // Scene est un objet qui contient tous les éléments graphiques (ça correspond à la fenêtre qui sera affichée)
        var gameScene = new Scene(root);
        // PacmanController est un listener d'événements clavier (ça récupère les touches promptées par l'user)
        var pacmanController = new PacmanController();

        /** gameScene est un objet de type Scene
         *
         *  setOnKeyPressed() et setOnKeyReleased() sont des méthodes de Scene.
         *  Elles permettent de définir des actions à effectuer quand une touche est pressée ou relâchée.
         *
         *
         *  Elles prennent en paramètre un objet de type EventHandler<KeyEvent>.
         *  PacmanController implémente EventHandler<KeyEvent>, donc on peut lui passer en paramètre.
         *  C'est ce qu'on fait ici.
         *  C'est un peu comme si on faisait :
         *
         *  gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
         *    @Override
         *    public void handle(KeyEvent event) {
         *    pacmanController.keyPressedHandler(event);
         *    }
         *    });
         *    gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
         *    @Override
         *    public void handle(KeyEvent event) {
         *    pacmanController.keyReleasedHandler(event);
         *    }
         *    });
         *    C'est juste plus court.
         *
         * */

        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.makeExampleTxt());

        //Récupère la taille de l'écran
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();


        //Adapte la taille de l'écran en fonction du nombre de lignes et de colonnes, ainsi que de la taille de l'écran
        double widthScale = Math.floor(screenBounds.getWidth() / maze.getWidth())/10.0;
        double heightScale = Math.floor(screenBounds.getHeight() / maze.getHeight())/10.0;
        double scale = Math.min((int)widthScale,(int)heightScale) * 10.0 - 3;


        var gameView = new GameView(maze, root, scale);

        /**
         * Ces 3 dernières lignes permette
         * 1. la configuration de la fenêtre avec gameScene comme contenu.
         * 2. l'affichage de la fenêtre.
         * 3. le lancement de l'animation du jeu.
         * c bo
         * pour l'instant c'est surtout moche en fait xd
         */
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
    }
}
