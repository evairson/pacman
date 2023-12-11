package gui;
import geometry.RealCoordinates;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;
import model.Items.BouleNeige;
import model.Direction;
import java.lang.Math;
import java.util.Objects;


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

    private String etatPacman; //permet de changer l'image de pacman
    private static int etatghost; //permet de changer l'image des fantômes
    private RealCoordinates pos; 
    private static long time;
    private final double offsetX = 0.01; 
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
    public String setImgPacman(Critter critter){
        String url;
        //primaryStage.addKeyListener(new EspaceClickListener());
        if(critter.isFakeEnergized() /*&& clickListener.isEspaceClique()*/ ){
            url = Objects.requireNonNull(CritterGraphicsFactory.class.getResource("FakeGhost.jpg")).toString();
            return url;
        }
        else {
            if(critter.getDirection()==Direction.NONE){
                url = Objects.requireNonNull(CritterGraphicsFactory.class.getResource("pacman-rond.png")).toString();
            }
            else{
                url = Objects.requireNonNull(CritterGraphicsFactory.class.getResource("pacman-"+getDirectionString(critter)+"-"+etatPacman+".png")).toString();
            }
            return url;
        }
            
    }

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
    public String setImgGhostNE(Critter critter){
        String ghost = switch ((Ghost) critter) {
            case BLINKY -> "ghost-blinky-";
            case CLYDE -> "ghost-clyde-";
            case INKY -> "ghost-inky-";
            case PINKY -> "ghost-pinky-";
        };
        return ghost;

    }

    public String setImgGhost(Ghost critter, int numghost, String setimgghostNE){
        if(!critter.isEnergized()) 
        return Objects.requireNonNull(CritterGraphicsFactory.class.getResource(setimgghostNE+getDirectionString(critter)+etatghost+".png")).toString();
        else {
            return Objects.requireNonNull(CritterGraphicsFactory.class.getResource("ghost-blue"+etatghost+".png")).toString();

        }  
    }

    // Choix du numéro des fantômes
    public int getNumGhost(Critter critter){
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

        int numGhost;
        String setImgGhostNE;

        if(critter instanceof Ghost) {
            numGhost = getNumGhost(critter);
            setImgGhostNE = setImgGhostNE(critter);}
        else {
            numGhost = 0;
            setImgGhostNE = "";
        }
        
        double size = 0.65; // facteur d'echelle de l'image
        double taille = scale * size;

        String url = (critter instanceof PacMan) ? setImgPacman(critter) :
                     (critter instanceof BouleNeige) ? Objects.requireNonNull(CritterGraphicsFactory.class.getResource("bouleNeige.png")).toString() :
                setImgGhost((Ghost)critter, numGhost, setImgGhostNE);

        // chargement de l'image à partir du fichier url
        ImageView image = (critter instanceof PacMan || critter instanceof Ghost) ? new ImageView(new Image(url, taille, taille, true, false)): new ImageView();

        return new GraphicsUpdater() {
            @Override
            public void update() {

                if(critter instanceof BouleNeige){
                    if(BouleNeige.INSTANCE.isActive() ){
                        if(image.getImage()==null){
                            image.setImage(new Image(url, 3*taille/4, 3*taille/4, true, false));
                        }
                    }
                    else {
                        image.setImage(null);
                    }
                }


                // mise à jour de la position de l'image
                if(critter instanceof PacMan || critter instanceof Ghost || BouleNeige.INSTANCE.isActive()){
                    image.setTranslateX((critter.getPos().x() + offsetX + (1 - size)/2) * scale);
                    image.setTranslateY((critter.getPos().y() + offsetY + (1 - size)/2) * scale);
                }


                //changer image pacman 
                if(critter instanceof PacMan){
                    if(Math.abs(critter.getPos().x() - pos.x()) >= 0.2 || Math.abs(critter.getPos().y() - pos.y()) >= 0.2 ){
                        etatPacman = switch(etatPacman){
                            case "ferme" -> "rond";
                            case "rond" -> "ouvert";
                            case "ouvert" -> "ferme";
                            default -> "ferme";
                        };
                        pos = critter.getPos();
                    }
                    image.setImage(new Image(setImgPacman(critter), taille, taille, true, false));
                }

                 //changer image fantôme
                if((critter instanceof Ghost)){
                    if(critter==Ghost.BLINKY && System.currentTimeMillis()-time>500){
                        time = System.currentTimeMillis(); 
                        if(etatghost == 1) {etatghost = 2; }
                        else { etatghost = 1; }
                    }
                    image.setImage(new Image(setImgGhost((Ghost)critter,numGhost,setImgGhostNE), taille, taille, true, false));
                }
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }
}
