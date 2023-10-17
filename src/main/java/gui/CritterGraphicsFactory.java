package gui;


import java.beans.EventHandler;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;

import geometry.RealCoordinates;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;
import model.Direction;

/**
 * Classe qui crée la représentation graphique de Pac-Man et des fantômes.
 *
 * 1. Gestion des erreurs de chargement d'images ? Pour l'instant on suppose que les images sont toujours présentes.
 *    mais si j'essaie d'importer Ama92.png par exemple, ça ne marche pas.
 * 2. Paramétrage de la taille des images ? Pour l'instant c'est fixé à 0.7 mais on peut vouloir changer ça.
 * 3. Gestion des paths des images ? "pacman.jpg" est définit en dur dans le code, mais faut changer ça...
 *    Sinon on peut pas changer les images sans recompiler le code.
 *    ou au pire utiliser des constantes.
 */


public final class CritterGraphicsFactory {
    private final double scale;
    private String imgPacMan;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
        this.imgPacMan = "pacman-droite-ferme.png";
    }



    // Choix de l'image de pacman
    public String setimgPacman(Critter critter){
        imgPacMan = switch(critter.getDirection()){
            case EAST -> "pacman-droite";
            case WEST -> "pacman-gauche";
            case NORTH -> "pacman-haut";
            case SOUTH -> "pacman-bas";
            default -> imgPacMan;
        };
        return imgPacMan;
    }


    // Méthode qui crée la représentation graphique d'une créature.
    public GraphicsUpdater makeGraphics(Critter critter) {
        RealCoordinates pos = critter.getPos();
        String etat = "ferme";

        var size = 0.5; // facteur d'echelle de l'image
        var url = (critter instanceof PacMan) ? setimgPacman(critter) :
                switch ((Ghost) critter) {
                    case BLINKY -> "ghost_blinky.png";
                    case CLYDE -> "ghost_clyde.png";
                    case INKY -> "ghost_inky.png";
                    case PINKY -> "ghost_pinky.png";
                };
        // chargement de l'image à partir du fichier url
        var image = new ImageView(new Image(url, scale * size, scale * size, true, true));
        return new GraphicsUpdater() {
            @Override
            public void update() {
                

                //changer image pacman 

                if(critter instanceof PacMan){
                    if(critter.getPos()!= pos){
                        if(etat == "ferme") etat = "ouvert"; 
                        else etat = "ferme";
                    }
                    image.setImage(new Image(setimgPacman(critter)+"-"+etat+".png", scale * size, scale * size, true, true));
                }
                // mise à jour de la position de l'image
                

                image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                // Debug.out("sprite updated");
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }
}
