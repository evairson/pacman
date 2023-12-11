package gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Screen;


public interface Menu {
    default double getWidth(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return screenBounds.getWidth();
    }
    default double getHeight(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return screenBounds.getHeight();
    }
    default void setHoverEffect(Text textNode, Paint whileHovered, Paint afterHovered) {
        textNode.setOnMouseEntered(event -> {
            textNode.setFill(whileHovered);
        });

        textNode.setOnMouseExited(event -> {
            textNode.setFill(afterHovered);
        });
    }
    default void setHoverEffect(Text textNode, Paint whileHovered) {
        textNode.setOnMouseEntered(event -> {
            textNode.setFill(whileHovered);
        });

        textNode.setOnMouseExited(event -> {
            textNode.setFill(Color.BLACK);
        });
    }
}
