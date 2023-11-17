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
import static config.Cell.Content.ENERGIZER;

import config.Cell;;

public class CellGraphicsFactory {
    private final double scale;

    public void ImageMur(String url, double taille, double translateX, double translateY, Group group) {
        ImageView mur = new ImageView(new Image("mur/"+url, taille, taille, true, false));
        mur.setTranslateX(translateX);
        mur.setTranslateY(translateY);
        group.getChildren().add(mur);
    }

    public String changeImage(int i, int n){
        switch(i){
            case 0 : return "mur-north"+n+".png";
            case 2 : return "mur-south"+n+".png";
            case 1 : return "mur-east"+n+".png";
            case 3 : return "mur-west"+n+".png";
            default : return "";

        }
    }

    public void choixMur(Cell cell, double taille, Group group) {
        if (cell.northWall()) {
            ImageMur("mur-north1.png", taille, 0, 0, group);
        }
        if (cell.eastWall()) {
            ImageMur("mur-east1.png", taille, 9*scale/10, 0, group);
            
        }
        if (cell.southWall()) {
            ImageMur("mur-south1.png", taille, 0, 9*scale/10, group);
        }
        if (cell.westWall()) {
            ImageMur("mur-west1.png", taille, 0, 0, group);
        }
    }

    public boolean[] typeMur(Cell cell){
        boolean[] type = new boolean[4];
        if(cell.northWall()) type[0]=true;
        if(cell.eastWall()) type[1]=true;
        if(cell.southWall()) type[2]=true;
        if(cell.westWall()) type[3]=true;
        return type;
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
        choixMur(cell, taille, group);
        


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
            int etatMur =1;
            boolean[] typeMur = typeMur(cell);

            @Override
            public void update() {

                if(System.currentTimeMillis()-time>400){
                    for(Node m : group.getChildren()){
                        int i = 0;
                        if(m instanceof ImageView){
                            while (i < 4){
                                if(typeMur[i]){
                                    typeMur[i]=false;
                                    ImageView mur = (ImageView)m;
                                    mur.setImage(new Image("mur/"+(changeImage(i,etatMur)),taille,taille,true,false));
                                    i = 4;
                                }
                                else i++;
                            }
                            }
                        }
                    typeMur = typeMur(cell);
                    time = System.currentTimeMillis();
                    if(etatMur==1) etatMur=2; 
                    else etatMur=1;
                }
                dot.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
