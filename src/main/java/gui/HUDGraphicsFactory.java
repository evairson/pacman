package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Items.FakeEnergizer;
import model.Items.Inventory;
import model.Items.ItemBouleNeige;
import model.MazeState;

public class HUDGraphicsFactory {
    private final double width;
    private final double height;
    private final double scale;

    //--Sprites--
    private final Image fakeEnergizerSprite;
    private final Image bouleNeigeSprite;
    private final Image noneImage = null;

    public HUDGraphicsFactory(double width, double height, double scale){
        this.height = height;
        this.width = width;
        this.scale = scale;

        this.fakeEnergizerSprite = new Image("model/Items/FakeGhost.jpg", 0.35 * this.scale, 0.35 * this.scale, true, false);
        this.bouleNeigeSprite = new Image("model/Items/bouleNeige.png", 0.35 * this.scale, 0.35 * this.scale, true, false);
    }

    public Text[] initKeybordIndicators(){
        Text[] keyBoardIndicators = new Text[4];
        keyBoardIndicators[0] = new Text("A");
        keyBoardIndicators[1] = new Text("Z");
        keyBoardIndicators[2] = new Text("E");
        keyBoardIndicators[3] = new Text("R");

        for(int i = 0; i < 4; i++){
            keyBoardIndicators[i].setFill(Color.WHITE);
            keyBoardIndicators[i].setFont(Font.font("Crackman", 25));
        }
        return keyBoardIndicators;
    }

    public ImageView[] initItemDisplay(){
        ImageView[] imageTab = new ImageView[4];
        for(int i = 0; i < 4; i ++){
            imageTab[i] = new ImageView();
            imageTab[i].setFitHeight(0.35 * this.scale);
            imageTab[i].setFitWidth(0.35 * this.scale);
        }
        return imageTab;
    }

    public Circle[] initHealthDisplay(){
        Circle[] circles = new Circle[3];
        for(int i = 0; i < 3; i ++){
            circles[i] = new Circle();
            circles[i].setRadius(15);
            circles[i].setStroke(Color.WHITE);
            circles[i].setStrokeWidth(3);
            circles[i].setFill(Color.RED);
        }
        return circles;
    }

    public void updateItems(Inventory inventory, ImageView[] imageTab){
        for(int i = 0; i < 4; i ++){
            if(inventory.getNth(i) instanceof FakeEnergizer){
                imageTab[i].setImage(this.fakeEnergizerSprite);
            } else if(inventory.getNth(i) instanceof ItemBouleNeige){
                imageTab[i].setImage(this.bouleNeigeSprite);
            } else {
                imageTab[i].setImage(noneImage);
            }
        }
    }

    public void updateHealth(int nbLives, Circle[] circles){
        for(int i = nbLives; i < 3; i++){
            circles[i].setFill(Color.BLACK);
        }
    }

    public VBox[] initInventoryDisplay(Text[] keyBoardIndicators, ImageView[] imageTab){
        VBox[] vBoxes = new VBox[4];

        for(int i = 0; i < 4; i++){
            vBoxes[i] = new VBox();
            vBoxes[i].getChildren().addAll(keyBoardIndicators[i], imageTab[i]);
            vBoxes[i].setSpacing(20);
            vBoxes[i].setAlignment(Pos.CENTER);
        }
        return vBoxes;
    }

    public GraphicsUpdater makeGraphics(MazeState maze) {

        Group group = new Group();

        Font.loadFont(getClass().getResourceAsStream("/gui/Crackman.otf"), 20);

        Text scoreTxt = new Text();
        scoreTxt.setFill(Color.WHITE);
        scoreTxt.setFont(Font.font("Crackman", 35));

        ImageView[] spriteItems = this.initItemDisplay();
        Circle[] circlesHealth = this.initHealthDisplay(); // Ce sera plutôt des sprites pour représenter la vie
        Text[] keyBoardIndicators = this.initKeybordIndicators();
        VBox[] inventoryDisplay = this.initInventoryDisplay(keyBoardIndicators, spriteItems);


        HBox hBox = new HBox();

        hBox.getChildren().addAll(circlesHealth); // 180px + 20px margin
        hBox.getChildren().addAll(inventoryDisplay); // 240px + ?? margin
        hBox.getChildren().add(scoreTxt); // 125px

        hBox.setSpacing(30);
        HBox.setMargin(circlesHealth[0], new Insets(0, 0, 0, 20));
        HBox.setMargin(inventoryDisplay[0], new Insets(0, 0, 0, (this.width / 2) - 200 - 120));
        HBox.setMargin(scoreTxt, new Insets(0, 0, 0, (this.width / 2) - 350));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefHeight(this.height);

        group.getChildren().add(hBox);

        return new GraphicsUpdater() {
            @Override
            public void update() {
                updateItems(maze.getInventory(), spriteItems);
                updateHealth(maze.getLives(), circlesHealth);
                scoreTxt.setText("Score : " + maze.getScore());
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}
