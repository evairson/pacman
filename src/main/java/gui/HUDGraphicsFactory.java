package gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Critter;
import model.Ghost;
import model.Items.Inventory;
import model.Items.ItemTest;
import model.MazeState;
import model.PacMan;

import java.util.ArrayList;

public class HUDGraphicsFactory {
    private final double scale;
    public HUDGraphicsFactory(double scale){
        this.scale = scale;
    }

    public Text[] initKeybordIndicators(){
        Text[] keyBoardIndicators = new Text[4];
        keyBoardIndicators[0] = new Text("A");
        keyBoardIndicators[1] = new Text("Z");
        keyBoardIndicators[2] = new Text("E");
        keyBoardIndicators[3] = new Text("R");
        return keyBoardIndicators;
    }

    public Circle[] initItemDisplay(){
        Circle[] circles = new Circle[4];
        for(int i = 0; i < 4; i ++){
            circles[i] = new Circle();
            circles[i].setRadius(10);
            circles[i].setFill(Color.WHITE);
            }
        return circles;
    }

    public void updateItems(Inventory inventory, Circle[] circles){
        for(int i = 0; i < 4; i ++){
            if(inventory.getNth(i) instanceof ItemTest){
                circles[i].setFill(Color.RED);
            } else {
                circles[i].setFill(Color.WHITE);
            }
        }
    }

    public VBox[] initInventoryDisplay(Text[] keyBoardIndicators, Circle[] circles){
        VBox[] vBoxes = new VBox[4];

        for(int i = 0; i < 4; i++){
            vBoxes[i] = new VBox();
            vBoxes[i].getChildren().addAll(keyBoardIndicators[i], circles[i]);
            vBoxes[i].setSpacing(20);
            vBoxes[i].setAlignment(Pos.CENTER);
        }
        return vBoxes;
    }

    public GraphicsUpdater makeGraphics(MazeState maze) {

        Group group = new Group();
        Inventory inventory = maze.getInventory();

        Text scoreTxt = new Text("" + maze.getScore());
        Circle[] circles = this.initItemDisplay();
        Text[] keyBoardIndicators = this.initKeybordIndicators();
        VBox[] inventoryDisplay = this.initInventoryDisplay(keyBoardIndicators, circles);

        HBox hBox = new HBox();
        for(VBox vBox : inventoryDisplay){
            hBox.getChildren().add(vBox);
        }
        hBox.setSpacing(20);

        group.getChildren().add(hBox);

        return new GraphicsUpdater() {
            @Override
            public void update() {
                System.out.println(maze.getInventory());
                updateItems(maze.getInventory(), circles);
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
