package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;

public class HUDGraphicsFactory {

    public GraphicsUpdater makeGraphics() {
        return new GraphicsUpdater() {
            @Override
            public void update() {

            }

            @Override
            public Node getNode() {
                return null;
            }
        };
    }
}
