package gui;

import javafx.scene.layout.*;
import model.MazeState;

public class HUDView {
    private MazeState maze;
    private final Pane hudRoot;
    private GraphicsUpdater hudUpdater;
    private final HUDGraphicsFactory hudGraphicsFactory;

    public HUDView(MazeState maze, Pane hudRoot, double width, double height, double scale){
        this.maze = maze;
        this.hudRoot = hudRoot;

        hudRoot.setPrefWidth(width);
        hudRoot.setPrefHeight(height);
        hudRoot.setStyle("-fx-background-color: #000000");
        //hudRoot.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        this.hudGraphicsFactory = new HUDGraphicsFactory(width, height, scale);

        this.setGraphics(this.hudGraphicsFactory.makeGraphics(maze));
    }

    public void setGraphics(GraphicsUpdater updater){
        this.hudRoot.getChildren().add(updater.getNode());
        this.hudUpdater = updater;
    }

    public GraphicsUpdater getHudUpdater(){
        return this.hudUpdater;
    }

    public void changeMaze(MazeState maze){
        this.hudRoot.getChildren().remove(this.hudUpdater.getNode());
        this.maze = maze;
        this.setGraphics(this.hudGraphicsFactory.makeGraphics(maze));
    }
}
