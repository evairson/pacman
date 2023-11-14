package gui;


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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.text.Text;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainMenu implements Menu {

    public AnchorPane imageAnchor;

    public Scene startMenu(Stage primaryStage, Scene gameScene) throws IOException {


        primaryStage.setFullScreenExitHint("");

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/sample.fxml"));
        AnchorPane anchorPane = loader.load();

        anchorPane.setMaxWidth(1280);
        anchorPane.setMaxHeight(720);
        anchorPane.setMinWidth(655);
        anchorPane.setMinHeight(300);

        ImageView imageView = (ImageView) anchorPane.lookup("#imageMENU");

        Text playText = (Text) anchorPane.lookup("#play");
        Text optionsText = (Text) anchorPane.lookup("#options");
        Text quitText = (Text) anchorPane.lookup("#quit");

        setHoverEffect(playText, "yellow", "black");
        setHoverEffect(optionsText, "blue", "black");
        setHoverEffect(quitText, "red", "black");
        playText.setOnMouseClicked(event -> {
            primaryStage.setScene(gameScene);
        });
        quitText.setOnMouseClicked(event -> {
            System.exit(0);
        });


        imageView.fitHeightProperty().bind(anchorPane.heightProperty());
        imageView.fitWidthProperty().bind(anchorPane.widthProperty());

        imageView.setPreserveRatio(true);

        primaryStage.setFullScreen(true);

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
