package gui;
import geometry.RealCoordinates;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;
import model.Direction;
import java.lang.Math;
import gui.App;



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

    private String etatPacman;
    private static int etatghost;
    private RealCoordinates pos;
    private static long time;
    private final double offsetX = 0.01; //FIXME : très moche lol
    private final double offsetY = 0.05;
        

    public void setEtatPacman(String e){ //permet de changer l'etat pour les tests
        etatPacman = e;
    }

    public void setEtatghost(int i, int n){
        etatghost = i;
    }


    public CritterGraphicsFactory(double scale) { // constructeur
        this.scale = scale;
        etatPacman = "rond";
        etatghost = 1;
        pos = new RealCoordinates(0, 0);
        time = System.currentTimeMillis();
    }


    // Choix de l'image de pacman
    public String setimgPacman(Critter critter){
        String url;
        
            //primaryStage.addKeyListener(new EspaceClickListener());
        
            if ( critter.isFakeEnergized() /*&& clickListener.isEspaceClique()*/ ){
                url = "FakeGhost.jpg";
                return url;
            }
            else {
                if(critter.getDirection()==Direction.NONE || etatPacman=="rond"){
                    url = "pacman/pacman-rond.png";
                    return url;
                }
                else{
                    url = "pacman/pacman-"+getDirectionString(critter)+"-"+etatPacman+".png";
                    return url;
                } 
            }
            
    }

        
    

    //choix de l'image de pacman lorsqu'il est fakely energizé A REVOIR !!!!!!!!!!!
    /*public String setimgPacmanF(Critter critter){
        
        String url;
        
        url = "ghost-blue1.png";
        
        
        return url;

    }*/

    // Trouve la direction des critters
    private String getDirectionString(Critter critter){
        String direction = switch(critter.getDirection()){
            case EAST -> "droite";
            case WEST -> "gauche";
            case NORTH -> "haut";
            case SOUTH -> "bas";
            default -> "droite";
        };
        return direction;
    }

    // Choix de l'image en fonction du fantôme (sans la direction)
    public String setimgghostNEb(Critter critter){
        String ghost = switch ((Ghost) critter) {
            case BLINKY -> "ghost-blinky/ghost-blinky-";
            case CLYDE -> "ghost-clyde/ghost-clyde-";
            case INKY -> "ghost-inky/ghost-inky-";
            case PINKY -> "ghost-pinky/ghost-pinky-";
        };
        return ghost;

    }

    public String setimgghostNE(Critter critter){ //avec direction
        return setimgghostNEb(critter)+getDirectionString(critter);
    }

    public String setimgghost(Ghost critter, int numghost, String setimgghostNE){
        if(!critter.isEnergized()) 
        return setimgghostNE+etatghost+".png";
        else {
            return "ghost-blue"+etatghost+".png";

        }  
    }

    // Choix du numéro des fantômes
    public int getnumghost(Critter critter){
        int numghost = switch ((Ghost) critter) {
            case BLINKY -> 0;
            case CLYDE -> 1;
            case INKY -> 2;
            case PINKY -> 3;
        };
        return numghost;
    }


    // Méthode qui crée la représentation graphique d'une créature.
    public GraphicsUpdater makeGraphics(Critter critter) {

        int numghost;
        String setimgghostNE;

        if(critter instanceof Ghost) {
            numghost = getnumghost(critter);
            setimgghostNE = setimgghostNE(critter);}
        else {
            numghost = 0; 
            setimgghostNE = "";
        }
        
        var size = 0.5; // facteur d'echelle de l'image
        double taille = scale * size;
        
        var url = (critter instanceof PacMan) ? setimgPacman(critter) :
                setimgghost((Ghost)critter,numghost,setimgghostNE);
        
        // chargement de l'image à partir du fichier url
        var image = new ImageView(new Image(url, taille, taille, false, false));
        return new GraphicsUpdater() {
            @Override
            public void update() {

                // mise à jour de la position de l'image

                image.setTranslateX((critter.getPos().x() + offsetX + (1 - size)/2) * scale);
                image.setTranslateY((critter.getPos().y() + offsetY + (1 - size)/2) * scale);

                // Debug.out("sprite updated");

                //changer image pacman 
                if(critter instanceof PacMan){
                    if(Math.abs(critter.getPos().x() - pos.x()) >= 0.2 || Math.abs(critter.getPos().y() - pos.y()) >= 0.2 ){
                        etatPacman = switch(etatPacman){
                            case "ferme" ->"rond"; 
                            case "rond" -> "ouvert";
                            case "ouvert" -> "ferme";
                            default -> "ferme";
                        };
                        pos = critter.getPos();
                    }
                    image.setImage(new Image(setimgPacman(critter), taille, taille, false, false));
                }

                 //changer image fantôme

                if((critter instanceof Ghost)){
                    if(critter==Ghost.BLINKY && System.currentTimeMillis()-time>500){
                        time = System.currentTimeMillis(); 
                        if(etatghost == 1) {etatghost = 2; }
                        else { etatghost = 1; }
                    }
                    image.setImage(new Image(setimgghost((Ghost)critter,numghost,setimgghostNE), taille, taille, false, false));
                        
                }
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }
}
