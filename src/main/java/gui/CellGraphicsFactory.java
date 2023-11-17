package gui;

/**
 * Cette classe est responsable de la création des éléments graphiques pour UNE CELLULE du labyrinthe.
 */


import geometry.IntCoordinates;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.MazeState;

import static config.Cell.Content.ENERGIZER;

import config.Cell;;

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
        Group group = new Group();
        group.setTranslateX(pos.x()*scale);
        group.setTranslateY(pos.y()*scale);
        Cell cell = state.getConfig().getCell(pos);
        Circle dot = new Circle();
        group.getChildren().add(dot);
        dot.setRadius(switch (cell.initialContent()) { case DOT -> scale/20; case ENERGIZER -> scale/7; case NOTHING -> 0; });
        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        dot.setFill(Color.WHITE);
        double taille = scale;


        if(cell.initialContent()==ENERGIZER){ //animation de l'energizer
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
            ImageView mur = new ImageView(new Image("mur-north.png", taille, taille, true, false));
            mur.setTranslateX(0);
            mur.setTranslateY(0);
            group.getChildren().add(mur);
        }
        if (cell.eastWall()) {
            ImageView mur = new ImageView(new Image("mur-east.png", taille, taille, true, false));
            mur.setTranslateX(9*scale/10);
            mur.setTranslateY(0);
            group.getChildren().add(mur);
        }
        if (cell.southWall()) {
            ImageView mur = new ImageView(new Image("mur-south.png", taille, taille, true, false));
            mur.setTranslateX(0);
            mur.setTranslateY(9*scale/10);
            group.getChildren().add(mur);
        }
        if (cell.westWall()) {
            ImageView mur = new ImageView(new Image("mur-west.png", taille, taille, true, false));
            mur.setTranslateX(0);
            mur.setTranslateY(0);
            group.getChildren().add(mur);
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
