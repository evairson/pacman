package gui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MazeState;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.SimpleTimeZone;

public class PauseMenu implements Menu{

    private Stage stage;
    private PacmanController pacmanController;
    private final MazeState maze;

    public PauseMenu(PacmanController pacmanController, MazeState maze) {
        this.pacmanController = pacmanController;
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        this.maze = maze;
    }

    public void startMenu(){
        try{


            BorderPane layout = new BorderPane();

            double width = this.getWidth();
            double height = this.getHeight();

            layout.setMinWidth(width*0.3);
            layout.setMinHeight(height*0.3);
            layout.setMaxWidth(width*0.7);
            layout.setMaxHeight(height*0.7);
            layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

            Text pauseMenuText = new Text("PAUSE");
            pauseMenuText.setFill(Color.WHITE);
            pauseMenuText.setStyle("-fx-font-size: 50;-fx-font-family: Serif");

            Button exitButton = new Button("EXIT");
            exitButton.setWrapText(true);
            exitButton.setStyle("-fx-font-size: 2em;-fx-border-color: black;-fx-font-family: Serif");
            exitButton.setOnAction(event -> {
                System.exit(0);
            });


            Text indication = new Text("Press ESC to resume...");
            indication.setFill(Color.WHITE);
            indication.setStyle("-fx-font-size: 50;-fx-font-family: Serif");

            Text score = new Text("Score : " + String.valueOf(maze.getScore()));
            score.setFill(Color.WHITE);
            score.setStyle("-fx-font-size: 50;-fx-font-family: Serif");

            Text lives = new Text("Vies restantes : " + String.valueOf(maze.getLives()));
            lives.setFill(Color.WHITE);
            lives.setStyle("-fx-font-size: 50;-fx-font-family: Serif");


            HBox hBox = new HBox();
            hBox.getChildren().addAll(score,lives);
            hBox.setSpacing(50);
            hBox.setAlignment(Pos.CENTER);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(pauseMenuText,exitButton,indication);
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.setSpacing(100);
            vBox.getChildren().add(hBox);



            layout.setCenter(vBox);


            Scene pause = new Scene(layout);
            pause.setFill(Color.TRANSPARENT);


            pause.setOnKeyPressed(pacmanController::keyPressedHandler);
            pause.setOnKeyReleased(pacmanController::keyReleasedHandler);

            stage.setScene(pause);
            stage.centerOnScreen();
            stage.setAlwaysOnTop(true);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }




    }
    public void stopMenu(){
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }
}
