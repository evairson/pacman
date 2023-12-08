package gui;


import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;

import javafx.stage.Stage;
import javafx.scene.text.Text;


import java.io.IOException;


public class MainMenu implements Menu {

    public AnchorPane imageAnchor;

    public Scene startMenu(Stage primaryStage, Scene gameScene, AnimationController animationController, OptionsMenu optionsMenu) throws IOException {

        FXMLLoader loader =  new FXMLLoader(MainMenu.class.getResource("/fxml/sample.fxml"));
        AnchorPane anchorPane = loader.load();

        ImageView imageView = (ImageView) anchorPane.lookup("#imageMENU");

        Font.loadFont(getClass().getResourceAsStream("Crackman.otf"), 12);

        Text playText = (Text) anchorPane.lookup("#play");
        Text optionsText = (Text) anchorPane.lookup("#options");
        Text quitText = (Text) anchorPane.lookup("#quit");
        playText.setStyle("-fx-font-size: 75");
        optionsText.setStyle("-fx-font-size: 45");
        quitText.setStyle("-fx-font-size: 50");

        playText.setFont(Font.font("Crackman", 75));
        optionsText.setFont(Font.font("Crackman", 45));
        quitText.setFont(Font.font("Crackman", 50));

        setHoverEffect(playText, Color.YELLOW);
        setHoverEffect(optionsText, Color.BLUE);
        setHoverEffect(quitText, Color.RED);
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

}
