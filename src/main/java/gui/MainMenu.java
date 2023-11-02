package gui;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class MainMenu {

    public Scene startMenu(Stage primaryStage, double width, double height, Scene gameScene) throws FileNotFoundException {

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");

        BorderPane layout = new BorderPane();

        layout.setMinWidth(width);
        layout.setMinHeight(height);
        layout.setMaxWidth(width);
        layout.setMaxHeight(height);
        layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Button startButton = new Button("Commencer une nouvelle partie");
        startButton.setWrapText(true);
        startButton.setStyle("-fx-font-size: 2em;-fx-border-color: black");
        startButton.setOnAction(event -> {
            primaryStage.setScene(gameScene);

            //Permet de centrer la fenêtre de jeu dynamiquement par rapport à la taille de l'écran et la taille de la fenêtre de jeu
            primaryStage.setX((width - primaryStage.getWidth()) / 2);
            primaryStage.setY((height - primaryStage.getHeight()) / 2);

            primaryStage.show();
        });

        Button stopButton = new Button("Quitter");
        stopButton.setWrapText(true);
        stopButton.setStyle("-fx-font-size: 2em;-fx-border-color: black");
        stopButton.setOnAction(event -> System.exit(0));


        Image logoIMG = new Image(new FileInputStream("src/main/resources/logo.png"),width/4,height/4,true,true);
        ImageView logo = new ImageView(logoIMG);
        logo.setPreserveRatio(true);


        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.getChildren().addAll(startButton,stopButton);
        vbox.setAlignment(Pos.TOP_CENTER);

        VBox vbox2 = new VBox();
        vbox2.getChildren().add(logo);
        vbox2.getChildren().add(vbox);
        vbox2.setPadding(new Insets(250));
        vbox2.setAlignment(Pos.TOP_CENTER);

        layout.setCenter(vbox2);


        return new Scene(layout);
    }
}
