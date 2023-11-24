package gui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

    public void startMenu(boolean isFancy){
        try{

            Font.loadFont(getClass().getResourceAsStream("/fonts/Crackman.otf"), 12);

            BorderPane layout = new BorderPane();

            double width = this.getWidth();
            double height = this.getHeight();

            layout.setMinWidth(width*0.3);
            layout.setMinHeight(height*0.3);
            layout.setMaxWidth(width*0.7);
            layout.setMaxHeight(height*0.7);
            if(!isFancy){
                layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            Text pauseMenuText = new Text("PAUSE");
            pauseMenuText.setFill(Color.YELLOW);
            pauseMenuText.setFont(Font.font("Crackman", 50));

            Button exitButton = new Button("EXIT");
            exitButton.setWrapText(true);
            exitButton.setFont(Font.font("Crackman", 25));
            exitButton.setOnAction(event -> {
                System.exit(0);
            });


            Text indication = new Text("Press ESC to resume...");
            indication.setFill(Color.YELLOW);
            indication.setFont(Font.font("Crackman", 50));

            Text score = new Text("Score : " + String.valueOf(maze.getScore()));
            score.setFill(Color.YELLOW);
            score.setFont(Font.font("Crackman", 50));

            Text lives = new Text("Vies restantes : " + String.valueOf(maze.getLives()));
            lives.setFill(Color.YELLOW);
            lives.setFont(Font.font("Crackman", 50));


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
