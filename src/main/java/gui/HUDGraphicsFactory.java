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
import model.Critter;
import model.Ghost;
import model.Items.FakeEnergizer;
import model.Items.Inventory;
import model.Items.ItemTest;
import model.MazeState;
import model.PacMan;

import java.util.ArrayList;

public class HUDGraphicsFactory {
    private final double width;
    private final double height;
    public HUDGraphicsFactory(double width, double height){
        this.height = height;
        this.width = width;
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

    public Circle[] initItemDisplay(){
        Circle[] circles = new Circle[4];
        for(int i = 0; i < 4; i ++){
            circles[i] = new Circle();
            circles[i].setRadius(15);
            circles[i].setStroke(Color.WHITE);
            circles[i].setStrokeWidth(3);
            circles[i].setFill(Color.BLACK);
            }
        return circles;
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

    public void updateItems(Inventory inventory, Circle[] circles){
        for(int i = 0; i < 4; i ++){
            if(inventory.getNth(i) instanceof ItemTest){
                circles[i].setFill(Color.BLUE);
            } else if(inventory.getNth(i) instanceof FakeEnergizer){
                circles[i].setFill(Color.GREEN);
            } else {
                circles[i].setFill(Color.BLACK);
            }
        }
    }

    public void updateHealth(int nbLives, Circle[] circles){
        for(int i = nbLives; i < 3; i++){
            circles[i].setFill(Color.BLACK);
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

        Font.loadFont(getClass().getResourceAsStream("/fonts/Crackman.otf"), 20);

        Text scoreTxt = new Text();
        scoreTxt.setFill(Color.WHITE);
        scoreTxt.setFont(Font.font("Crackman", 35));

        Circle[] circlesItems = this.initItemDisplay(); // Ce sera plutôt des sprites pour représenter les items
        Circle[] circlesHealth = this.initHealthDisplay(); // Ce sera plutôt des sprites pour représenter la vie
        Text[] keyBoardIndicators = this.initKeybordIndicators();
        VBox[] inventoryDisplay = this.initInventoryDisplay(keyBoardIndicators, circlesItems);


        HBox hBox = new HBox();

        hBox.getChildren().addAll(circlesHealth); // 180px + 20px margin
        hBox.getChildren().addAll(inventoryDisplay); // 240px + ?? margin
        hBox.getChildren().add(scoreTxt); // 125px

        hBox.setSpacing(30);
        HBox.setMargin(circlesHealth[0], new Insets(0, 0, 0, 20));
        System.out.println((this.width / 2) - 200 - 120);
        HBox.setMargin(inventoryDisplay[0], new Insets(0, 0, 0, (this.width / 2) - 200 - 120));
        HBox.setMargin(scoreTxt, new Insets(0, 0, 0, (this.width / 2) - 350));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefHeight(this.height);

        group.getChildren().add(hBox);

        return new GraphicsUpdater() {
            @Override
            public void update() {
                updateItems(maze.getInventory(), circlesItems);
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
