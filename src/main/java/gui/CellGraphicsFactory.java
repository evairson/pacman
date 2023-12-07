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
import model.Ghost;
import model.Items.ItemTest;
import model.MazeState;
import model.PacMan;
import model.Items.ItemBouleNeige;
import model.Items.Dot;
import model.Items.Energizer;
import model.Items.FakeEnergizer;
import config.Cell;

public class CellGraphicsFactory {
    private final double scale;
    public static boolean finNiveau;

    public static void setFinNiveau(boolean b) {
        finNiveau = b;
    }

    public void ImageMur(String url, double taille, double translateX, double translateY, Group group) {
        ImageView mur = new ImageView(new Image("mur/" + url, taille, taille, true, false));
        mur.setTranslateX(translateX);
        mur.setTranslateY(translateY);
        group.getChildren().add(mur);
    }

    public String changeImage(int i, int n) {
        switch (i) {
            case 0:
                return "mur-north" + n + ".png";
            case 2:
                return "mur-south" + n + ".png";
            case 1:
                return "mur-east" + n + ".png";
            case 3:
                return "mur-west" + n + ".png";
            default:
                return "";
        }
    }

    public void choixMur(Cell cell, double taille, Group group) {
        if (cell.northWall()) {
            ImageMur("mur-north1.png", taille, 0, 0, group);
        }
        if (cell.eastWall()) {
            ImageMur("mur-east1.png", taille, 9 * scale / 10, 0, group);
        }
        if (cell.southWall()) {
            ImageMur("mur-south1.png", taille, 0, 9 * scale / 10, group);
        }
        if (cell.westWall()) {
            ImageMur("mur-west1.png", taille, 0, 0, group);
        }
    }

    public boolean[] typeMur(Cell cell) {
        boolean[] type = new boolean[4];
        if (cell.northWall()) type[0] = true;
        if (cell.eastWall()) type[1] = true;
        if (cell.southWall()) type[2] = true;
        if (cell.westWall()) type[3] = true;
        return type;
    }


    public CellGraphicsFactory(double scale) {
        this.scale = scale;
        finNiveau = false;
    }

    /**
     * Crée les éléments graphiques pour une cellule donnée.
     * @param state
     * @param pos
     * @return un objet GraphicsUpdater qui permet de mettre à jour les éléments graphiques
     */

    /**
     * Bon maintenant, avec respect : c'est quoi ce code ? KAPPA
     * <p>
     * 1. Utilisation de constante : scale/15, scale/5, scale/10, scale/2 ou 9 * scale / 10
     * c'est quand même déguelasse
     * 2. Méthodes distinctes pour chaque élément graphique
     * 4. COMPLIQUÉ : Gestion des dimensions dynamiques :
     * pour l'instant les dimensions sont fixes, mais si on veut changer la taille de la fenêtre
     * il faut changer les dimensions de chaque élément graphique
     */

    public void setEnergized(Energizer e) {
        if (e.isActive()) {
            e.frameActivity++;
            if (e.frameActivity > 500) {
                e.setActive(false);
                Ghost.energized = Energizer.isOneActive();
                PacMan.INSTANCE.setEnergized(Energizer.isOneActive());
            }
        }
    }

    public void setActiveItemTest(ItemTest t) {
        if (t.isActive()) {
            t.frameActivity++;
            if (t.frameActivity > 500) {
                t.setActive(false);
            }
        }
    }

    public void setActiveFakeEnergizer(FakeEnergizer e) {
        if (e.isActive()) {
            e.frameActivity++;
            if (e.frameActivity > 500) {
                e.setActive(false);
            }
        }
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos, Color color) {
        Group group = new Group(); // permet de mettre dans groupe tous les node à afficher (mur + dot)
        group.setTranslateX(pos.x() * scale);
        group.setTranslateY(pos.y() * scale);
        Cell cell = state.getConfig().getCell(pos);
        double taille = scale;

        // creer les dots
        Circle dot = new Circle();
        group.getChildren().add(dot);

        double radius = 0;
        if (cell.initialItem().getClass() == Dot.class) radius = scale / 20;
        if ((cell.initialItem() instanceof Energizer) || (cell.initialItem() instanceof ItemTest)) radius = scale / 7;
        dot.setRadius(radius);

        dot.setCenterX(scale / 2);
        dot.setCenterY(scale / 2);

        /*if(cell.initialItem() instanceof ItemTest) { dot.setFill(Color.BLUE); }
        else if (cell.initialItem() instanceof FakeEnergizer) { dot.setFill(Color.GREEN); }
        else { dot.setFill(Color.WHITE); }
        double taille = scale;*/

        dot.setFill(Color.WHITE);

        choixMur(cell, taille, group);

        if (cell.initialItem() instanceof FakeEnergizer || (cell.initialItem() instanceof ItemBouleNeige)) {
            cell.initialItem().setImage(new ImageView(new Image(cell.initialItem().getUrl(), taille / 2, taille / 2, true, false)));
            group.getChildren().add(cell.initialItem().getImage());
            cell.initialItem().getImage().setTranslateX(scale / 4);
            cell.initialItem().getImage().setTranslateY(scale / 4);
        }

        if ((cell.initialItem() instanceof Energizer) || (cell.initialItem() instanceof ItemTest)) {

            if ((cell.initialItem() instanceof Energizer) || (cell.initialItem() instanceof ItemTest) || (cell.initialItem() instanceof FakeEnergizer)) {
                ScaleTransition blink = new ScaleTransition(Duration.millis(600), dot);
                blink.setFromX(1);
                blink.setFromY(1);
                blink.setToX(0.6);
                blink.setToY(0.6);
                blink.setAutoReverse(true);
                blink.setCycleCount(Timeline.INDEFINITE);
                blink.play();
            }
        }

        return new GraphicsUpdater() {
            long time = System.currentTimeMillis();
            int etatMur = 1;
            boolean[] typeMur = typeMur(cell);

            @Override
            public void update() {

                if (finNiveau) {
                    if (System.currentTimeMillis() - time > 300) {
                        for (Node m : group.getChildren()) {
                            int i = 0;
                            if (m instanceof ImageView) {
                                while (i < 4) {
                                    if (typeMur[i]) {
                                        typeMur[i] = false;
                                        ImageView mur = (ImageView) m;
                                        mur.setImage(new Image("mur/" + (changeImage(i, etatMur)), taille, taille, true, false));
                                        i = 4;
                                    } else i++;
                                }
                            }
                        }
                        typeMur = typeMur(cell);
                        time = System.currentTimeMillis();
                        if (etatMur == 1) etatMur = 2;
                        else etatMur = 1;
                    }
                }

                if (cell.initialItem() instanceof Energizer) {
                    setEnergized((Energizer) cell.initialItem());
                }
                if (cell.initialItem() instanceof ItemTest) {
                    setActiveItemTest((ItemTest) cell.initialItem());
                }
                if (cell.initialItem() instanceof FakeEnergizer) {
                    setActiveFakeEnergizer((FakeEnergizer) cell.initialItem());
                }
                for (Node n : group.getChildren()) {
                    if (n != cell.initialItem().getImage()) {
                        n.setVisible(!ItemTest.isOneActive());
                    }
                }
                //afficher les points si pacman pas passé dessus
                dot.setVisible(!state.getGridState(pos));
                if ((cell.initialItem() instanceof FakeEnergizer || (cell.initialItem() instanceof ItemBouleNeige)) && state.getGridState(pos)) {
                    group.getChildren().remove(cell.initialItem().getImage());
                }
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}