package gui;

import javafx.geometry.Rectangle2D;
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
}
