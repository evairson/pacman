package gui;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MazeState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseMenu implements Menu{

    private final StackPane root;
    private final MazeState maze;
    private final AnimationController animationController;
    private Pane pauseLayout;
    private VBox vBox;

    public PauseMenu(MazeState maze, StackPane root, AnimationController animationController) {
        this.maze = maze;
        this.root  = root;
        this.animationController = animationController;
    }

    public void startMenu(boolean isFancy){
        try{

            Font.loadFont(getClass().getResourceAsStream("/fonts/Crackman.otf"), 12);

            BorderPane layout = new BorderPane();

            double width = root.getWidth();
            double height = root.getHeight();

            double fontScale = (50*root.getHeight())/1020;

            layout.setMinWidth(width*0.3);
            layout.setMinHeight(height*0.3);
            layout.setMaxWidth(width*0.7);
            layout.setMaxHeight(height*0.7);
            if(!isFancy){
                layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            Text pauseMenuText = new Text("PAUSE");
            pauseMenuText.setFill(Color.YELLOW);
            pauseMenuText.setFont(Font.font("Crackman", fontScale));


            Text exitText = new Text("EXIT");
            exitText.setStyle("-fx-font-family: Crackman; -fx-font-size: " + fontScale);
            exitText.setFill(Color.WHITE);
            setHoverEffect(exitText,Color.RED,Color.WHITE);
            exitText.setOnMouseClicked(event -> {
                Platform.runLater(() -> App.restartApplication(animationController.getPrimaryStage()));
            });

            Text indication = new Text("Press ESC to resume...");
            indication.setFill(Color.YELLOW);
            indication.setFont(Font.font("Crackman", fontScale));
            setHoverEffect(indication,Color.BLUE,Color.YELLOW);
            indication.setOnMouseClicked(event -> {
                animationController.stopPauseMenu();
                    }
            );

            Text score = new Text("Score : " + String.valueOf(maze.getScore()));
            score.setFill(Color.WHITE);
            score.setFont(Font.font("Crackman", fontScale));

            Text lives = new Text("Vies restantes : " + String.valueOf(maze.getLives()));
            lives.setFill(Color.RED);
            lives.setFont(Font.font("Crackman", fontScale));


            Text highScore = new Text("High Score : " + String.valueOf(maze.getHighScore()));
            highScore.setFill(Color.GREEN);
            highScore.setFont(Font.font("Crackman", fontScale));


            VBox components = new VBox();
            components.getChildren().addAll(score,lives, highScore);
            components.setSpacing((30*root.getHeight())/1020);
            components.setAlignment(Pos.CENTER);

            VBox vBox = new VBox();
            vBox.maxHeightProperty().bind(root.maxHeightProperty());//Pour qu'elle fit bien dans la pane
            vBox.setSpacing((90*root.getHeight())/1020);
            vBox.getChildren().addAll(pauseMenuText,exitText,indication);
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.getChildren().add(components);


            layout.setCenter(vBox);


            pauseLayout = layout;
            this.vBox = vBox;

            root.getChildren().add(layout);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void stopMenu(){
        root.getChildren().remove(pauseLayout);
    }

}
