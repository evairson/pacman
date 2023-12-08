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


public class OptionsMenu implements Menu {

    private boolean isFancy = false;
    public Scene startMenu(Stage primaryStage, Scene gameScene, AnimationController animationController, MainMenu mainMenu) throws IOException {

        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/optionsMenu.fxml"));
        AnchorPane anchorPane = loader.load();

        ImageView imageView = (ImageView) anchorPane.lookup("#imageOptionsMenu");

        Font.loadFont(getClass().getResourceAsStream("/gui/Crackman.otf"), 12);

        Text fancyText = (Text) anchorPane.lookup("#fancyTest");
        Text returnText = (Text) anchorPane.lookup("#return");

        fancyText.setFont(Font.font("Crackman", 50));
        returnText.setFont(Font.font("Crackman", 56));

        setHoverEffect(fancyText, Color.GREEN);
        setHoverEffect(returnText, Color.RED);
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

}
