package gui;

/**
 * Cette classe est responsable de la création des éléments graphiques pour UNE CELLULE du labyrinthe.
 */


import geometry.IntCoordinates;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.MazeState;

import static config.Cell.Content.DOT;
import static config.Cell.Content.ENERGIZER;;

public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }

    /**
     * Crée les éléments graphiques pour une cellule donnée.
     * @param state
     * @param pos
     * @return un objet GraphicsUpdater qui permet de mettre à jour les éléments graphiques
     */

    /**
     * Bon maintenant, avec respect : c'est quoi ce code ? KAPPA
     *
     * 1. Utilisation de constante : scale/15, scale/5, scale/10, scale/2 ou 9 * scale / 10
     *  c'est quand même déguelasse
     * 2. Méthodes distinctes pour chaque élément graphique
     * 4. COMPLIQUÉ : Gestion des dimensions dynamiques :
     * pour l'instant les dimensions sont fixes, mais si on veut changer la taille de la fenêtre
     * il faut changer les dimensions de chaque élément graphique
     */

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos, Color wallColor) {
        var group = new Group();
        group.setTranslateX(pos.x() * scale);
        group.setTranslateY(pos.y() * scale);
        var cell = state.getConfig().getCell(pos);
        var dot = new Circle();
        group.getChildren().add(dot);
        dot.setRadius(switch (cell.initialContent()) {
            case DOT -> scale / 20;
            case ENERGIZER -> scale / 7;
            case NOTHING -> 0;
        });
        dot.setCenterX(scale / 2);
        dot.setCenterY(scale / 2);
        dot.setFill(Color.WHITE);


        if (cell.initialContent() == ENERGIZER) {
            ScaleTransition blink = new ScaleTransition(Duration.millis(600), dot);
            blink.setFromX(1);
            blink.setFromY(1);
            blink.setToX(0.6);
            blink.setToY(0.6);
            blink.setAutoReverse(true);
            blink.setCycleCount(Timeline.INDEFINITE);
            blink.play();
        }

        if (cell.northWall()) {
            var nWall = createWall(scale, scale / 10, wallColor, 0, 0);
            group.getChildren().add(nWall);
        }
        if (cell.eastWall()) {
            var eWall = createWall(scale / 10, scale, wallColor, 9 * scale / 10, 0);
            group.getChildren().add(eWall);
        }
        if (cell.southWall()) {
            var sWall = createWall(scale, scale / 10, wallColor, 0, 9 * scale / 10);
            group.getChildren().add(sWall);
        }
        if (cell.westWall()) {
            var wWall = createWall(scale / 10, scale, wallColor, 0, 0);
            group.getChildren().add(wWall);
        }
        return new GraphicsUpdater() {
            @Override
            public void update() {
                dot.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }

    private Rectangle createWall(double width, double height, Color color, double x, double y) {
            var wall = new Rectangle();
            wall.setWidth(width);
            wall.setHeight(height);
            wall.setX(x);
            wall.setY(y);
            wall.setFill(color);
            return wall;
        }

}

