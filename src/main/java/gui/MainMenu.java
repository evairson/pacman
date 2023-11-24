package gui;


import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.text.Text;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MainMenu implements Menu {

    public AnchorPane imageAnchor;

    public Scene startMenu(Stage primaryStage, Scene gameScene, AnimationController animationController, OptionsMenu optionsMenu) throws IOException {

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/sample.fxml"));
        AnchorPane anchorPane = loader.load();

        ImageView imageView = (ImageView) anchorPane.lookup("#imageMENU");

        Font.loadFont(getClass().getResourceAsStream("/fonts/Crackman.otf"), 12);

        Text playText = (Text) anchorPane.lookup("#play");
        Text optionsText = (Text) anchorPane.lookup("#options");
        Text quitText = (Text) anchorPane.lookup("#quit");
        playText.setStyle("-fx-font-size: 75");
        optionsText.setStyle("-fx-font-size: 45");
        quitText.setStyle("-fx-font-size: 50");

        playText.setFont(Font.font("Crackman", 75));
        optionsText.setFont(Font.font("Crackman", 45));
        quitText.setFont(Font.font("Crackman", 50));

        setHoverEffect(playText, "yellow", "black");
        setHoverEffect(optionsText, "blue", "black");
        setHoverEffect(quitText, "red", "black");
        playText.setOnMouseClicked(event -> {
            primaryStage.setScene(gameScene);
            primaryStage.centerOnScreen();
            animationController.createAnimationTimer().start();

        });
        optionsText.setOnMouseClicked(event -> {
            try {
                primaryStage.setScene(optionsMenu.startMenu(primaryStage,gameScene,animationController,this));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        quitText.setOnMouseClicked(event -> {
            System.exit(0);
        });


        imageView.fitHeightProperty().bind(anchorPane.heightProperty());
        imageView.fitWidthProperty().bind(anchorPane.widthProperty());

        imageView.setPreserveRatio(true);

        Scene scene = new Scene(anchorPane);

        return scene;
    }
    private void setHoverEffect(Text textNode, String color1, String color2) {
        textNode.setOnMouseEntered(event -> {
            textNode.setStyle("-fx-fill: " + color1);
        });

        textNode.setOnMouseExited(event -> {
            textNode.setStyle("-fx-fill: " + color2);
        });
    }

}
