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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.MazeState;

import static config.Cell.Content.DOT;
import static config.Cell.Content.ENERGIZER;

import config.Cell;;

public class CellGraphicsFactory {
    private final double scale;

    public ImageView ImageMur(String url, double taille, double translateX, double translateY) {
        ImageView mur = new ImageView(new Image(url, taille, taille, true, false));
        mur.setTranslateX(translateX);
        mur.setTranslateY(translateY);
        return mur;
    }

    public ImageView choixMur(Cell cell, double taille) {
        if (cell.northWall()) {
            return ImageMur("mur-north.png", taille, 0, 0);
        }
        if (cell.eastWall()) {
            return ImageMur("mur-east.png", taille, 9*scale/10, 0);
        }
        if (cell.southWall()) {
            return ImageMur("mur-south.png", taille, 0, 9*scale/10);
        }
        if (cell.westWall()) {
            return ImageMur("mur-west.png", taille, 0, 0);
        }
        return new ImageView();
    }


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
        dot.setRadius(switch (cell.initialContent()) { case DOT -> scale/20; case ENERGIZER -> scale/7; case NOTHING -> 0; });
        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        dot.setFill(Color.WHITE);
        double taille = scale;
        ImageView mur = choixMur(cell, taille);
        group.getChildren().add(mur);


        if(cell.initialContent()==ENERGIZER){
            ScaleTransition blink = new ScaleTransition(Duration.millis(600), dot);
            blink.setFromX(1);
            blink.setFromY(1);
            blink.setToX(0.6);
            blink.setToY(0.6);
            blink.setAutoReverse(true);
            blink.setCycleCount(Timeline.INDEFINITE);
            blink.play();
        }


        return new GraphicsUpdater() {
            long time = System.currentTimeMillis();
            int etatmur = 0;
            @Override
            public void update() {

                /*if(System.currentTimeMillis()-time>500){
                    if(etatmur==0){
                        mur.setImage(new Image("mur-west.png", taille, taille, true, false));
                        etatmur =1;
                    }
                    else{
                        mur.setImage(new Image("mur-south.png", taille, taille, true, false));
                        etatmur = 0;
                    }
                    time = System.currentTimeMillis();
                    }*/
                dot.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
