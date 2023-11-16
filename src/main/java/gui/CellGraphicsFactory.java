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
import model.Ghost;
import model.MazeState;
import model.PacMan;
import model.Items.Dot;
import model.Items.Energizer;
import model.Items.FakeEnergizer;

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

    public void setEnergized(Energizer e){
        

        if(Energizer.isEnergized()){
            Energizer.frameEnergizer ++;
        }

        if(Energizer.frameEnergizer>2000){
            Energizer.setEnergized(false);
            Ghost.energized = false;
            PacMan.INSTANCE.setEnergized(false);
        }
    }

    public void setFakeEnergized(FakeEnergizer e){
        

        if(FakeEnergizer.isFakeEnergized()){
            FakeEnergizer.frameEnergizer ++;
        }

        if(FakeEnergizer.frameEnergizer>750){
            FakeEnergizer.setFakeEnergized(false);
            //Ghost.energized = false;
            PacMan.INSTANCE.setFakeEnergized(false);
        }
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        var group = new Group();
        group.setTranslateX(pos.x()*scale);
        group.setTranslateY(pos.y()*scale);
        var cell = state.getConfig().getCell(pos);
        var dot = new Circle();
        group.getChildren().add(dot);

        double radius =0;
        if(cell.initialItem().getClass() == Dot.class)  radius = scale/20;
        if(cell.initialItem() instanceof Energizer || cell.initialItem() instanceof FakeEnergizer )  radius = scale/7;

        dot.setRadius(radius);
        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        dot.setFill(Color.WHITE);


        if(cell.initialItem() instanceof Energizer  ){
            
            ScaleTransition blink = new ScaleTransition(Duration.millis(600), dot);
            blink.setFromX(1);
            blink.setFromY(1);
            blink.setToX(0.6);
            blink.setToY(0.6);
            blink.setAutoReverse(true);
            blink.setCycleCount(Timeline.INDEFINITE);
            blink.play();
        }
        else if (cell.initialItem() instanceof FakeEnergizer){
            var dot1=dot;
            dot.setFill(Color.RED);
            ScaleTransition blink = new ScaleTransition(Duration.millis(600), dot1);
            blink.setFromX(1);
            blink.setFromY(1);
            blink.setToX(0.6);
            blink.setToY(0.6);
            blink.setAutoReverse(true);
            blink.setCycleCount(Timeline.INDEFINITE);
            blink.play();
        }

        if (cell.northWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.eastWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setY(0);
            nWall.setX(9*scale/10);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.southWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(9*scale/10);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }
        if (cell.westWall()) {
            var nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUE);
            group.getChildren().add(nWall);
        }



        return new GraphicsUpdater() {
            @Override
            public void update() {
                dot.setVisible(!state.getGridState(pos));


                if (cell.initialItem() instanceof Energizer){
                    setEnergized((Energizer)cell.initialItem()); 
                }
                else if (cell.initialItem() instanceof FakeEnergizer){
                    setFakeEnergized((FakeEnergizer)cell.initialItem());
                }

            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
