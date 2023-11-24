package gui;


import com.sun.tools.javac.Main;
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


public class OptionsMenu implements Menu {

    private boolean isFancy = false;
    public Scene startMenu(Stage primaryStage, Scene gameScene, AnimationController animationController, MainMenu mainMenu) throws IOException {

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/optionsMenu.fxml"));
        AnchorPane anchorPane = loader.load();

        ImageView imageView = (ImageView) anchorPane.lookup("#imageOptionsMenu");

        Font.loadFont(getClass().getResourceAsStream("/fonts/Crackman.otf"), 12);

        Text fancyText = (Text) anchorPane.lookup("#fancyTest");
        Text returnText = (Text) anchorPane.lookup("#return");

        fancyText.setFont(Font.font("Crackman", 50));
        returnText.setFont(Font.font("Crackman", 56));

        setHoverEffect(fancyText, "green", "black");
        setHoverEffect(returnText, "red", "black");
        fancyText.setOnMouseClicked(event -> {
            if(!isFancy){
                fancyText.setText("Fancy");
                isFancy = true;
                animationController.setFancy(true);
            }
            else{
                fancyText.setText("Fast");
                isFancy = false;
                animationController.setFancy(false);
            }
        });
        returnText.setOnMouseClicked(event -> {
            try {
                primaryStage.setScene(mainMenu.startMenu(primaryStage,gameScene,animationController,this));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
