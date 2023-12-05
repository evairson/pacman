package gui;

import javafx.scene.layout.Pane;
import model.MazeState;

public class HUDView {
    private final MazeState maze;
    private final Pane hudRoot;
    private GraphicsUpdater hudUpdater;

    public HUDView(MazeState maze, Pane hudRoot, double scale){
        this.maze = maze;
        this.hudRoot = hudRoot;

        hudRoot.setMinWidth(scale * maze.getWidth());
        hudRoot.setMinHeight(scale * maze.getHeight());
        hudRoot.setStyle("-fx-background-color: #0F289B");

        HUDGraphicsFactory hudFactory = new HUDGraphicsFactory(scale);

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
