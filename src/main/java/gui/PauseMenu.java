package gui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.MazeState;

public class PauseMenu implements Menu{

    private final StackPane root;
    private final MazeState maze;
    private Pane pauseLayout;
    private VBox vBox;

    public PauseMenu(MazeState maze, StackPane root) {
        this.maze = maze;
        this.root  = root;
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

            Text highScore = new Text("High Score : " + String.valueOf(maze.getHighScore()));
            highScore.setFill(Color.WHITE);
            highScore.setStyle("-fx-font-size: 50;-fx-font-family: Serif");


            HBox hBox = new HBox();
            hBox.getChildren().addAll(score,lives, highScore);
            hBox.setSpacing(50);
            hBox.setAlignment(Pos.CENTER);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(pauseMenuText,exitButton,indication);
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.setSpacing(100);
            vBox.getChildren().add(hBox);

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
