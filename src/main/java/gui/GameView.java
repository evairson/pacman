package gui;

/**
 * La classe GameView est responsable de l'affichage et l'animation du jeu.
 * Elle est composée de GraphicsUpdater qui sont chargés de mettre à jour
 * l'affichage d'un élément du jeu.
 *
 * J'ai pas grand chose à ajouter dans cette classe, mis à part que si l'on veut ajouter
 * du son, il faudra le faire ici.
 * Par exemple avec import javafx.scene.media.AudioClip; et un attribut privé AudioClip
 * qui sera initialisé dans le constructeur.
 *
 */

import geometry.IntCoordinates;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.MazeState;

import java.util.ArrayList;
import java.util.List;

public class GameView {
    // class parameters
    private final MazeState maze;
    private final Pane gameRoot; // main node of the game

    private final List<GraphicsUpdater> graphicsUpdaters;

    public Pane getGameRoot() {
        return gameRoot;
    }

    private void addGraphics(GraphicsUpdater updater) {
        gameRoot.getChildren().add(updater.getNode());
        graphicsUpdaters.add(updater);
    }



    /**
     * @param maze  le "modèle" de cette vue (le labyrinthe et tout ce qui s'y trouve)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le nombre de pixels représentant une unité du labyrinthe
     */
    public GameView(MazeState maze, Pane root, double scale) {
        this.maze = maze;
        this.gameRoot = root;
        // pixels per cell
        root.setMinWidth(maze.getWidth() * scale);
        root.setMinHeight(maze.getHeight() * scale);
        // Définir la couleur de fond du nœud racine
        root.setStyle("-fx-background-color: #000000");
        var critterFactory = new CritterGraphicsFactory(scale);
        var cellFactory = new CellGraphicsFactory(scale);
        graphicsUpdaters = new ArrayList<>();

        // Ajouter les cellules du labyrinthe à la vue en utilisant CellGraphicsFactory
        for (int x = 0; x < maze.getWidth(); x++)
            for (int y = 0; y < maze.getHeight(); y++)
                addGraphics(cellFactory.makeGraphics(maze, new IntCoordinates(x, y)));

        // Ajouter les créatures à la vue en utilisant CritterGraphicsFactory
        for (var critter : maze.getCritters()) addGraphics(critterFactory.makeGraphics(critter));

    }

    public List<GraphicsUpdater> getGraphicsUpdaters() {
        return graphicsUpdaters;
    }

    public MazeState getMaze() {
        return maze;
    }
}
