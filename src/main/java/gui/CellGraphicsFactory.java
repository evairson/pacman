package gui;

/**
 * Cette classe est responsable de la création des éléments graphiques pour UNE CELLULE du labyrinthe.
 */


import geometry.IntCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.MazeState;

import static config.Cell.Content.DOT;

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
     * 3. Couleurs en paramètre ? comme ça le client choisi la couleur qu'il veut
     * 4. COMPLIQUÉ : Gestion des dimensions dynamiques :
     * pour l'instant les dimensions sont fixes, mais si on veut changer la taille de la fenêtre
     * il faut changer les dimensions de chaque élément graphique
     */

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        var group = new Group();
        group.setTranslateX(pos.x()*scale);
        group.setTranslateY(pos.y()*scale);
        var cell = state.getConfig().getCell(pos);
        var dot = new Circle();
        group.getChildren().add(dot);
        dot.setRadius(switch (cell.initialContent()) { case DOT -> scale/20; case ENERGIZER -> scale/5; case NOTHING -> 0; });
        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        dot.setFill(Color.WHITE);
        if (cell.northWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.eastWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setY(0);
            nWall.setX(9*scale/10);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.southWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(9*scale/10);
            nWall.setX(0);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.westWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
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
}
