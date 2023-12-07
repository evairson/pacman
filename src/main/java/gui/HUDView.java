package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.MazeState;

public class HUDView {
    private final MazeState maze;
    private final Pane hudRoot;
    private GraphicsUpdater hudUpdater;

    public HUDView(MazeState maze, Pane hudRoot, double width, double height, double scale){
        this.maze = maze;
        this.hudRoot = hudRoot;

        hudRoot.setPrefWidth(width);
        hudRoot.setPrefHeight(height);
        hudRoot.setStyle("-fx-background-color: #000000");
        //hudRoot.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        HUDGraphicsFactory hudFactory = new HUDGraphicsFactory(width, height, scale);

        this.setGraphics(hudFactory.makeGraphics(maze));
    }

    public void setGraphics(GraphicsUpdater updater){
        this.hudRoot.getChildren().add(updater.getNode());
        this.hudUpdater = updater;
    }

    public GraphicsUpdater getHudUpdater(){
        return this.hudUpdater;
    }
}
