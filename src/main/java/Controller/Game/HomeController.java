package Controller.Game;

import Controller.NavigationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class HomeController implements Initializable {
    @FXML
    private AnchorPane container;
    @FXML
    private Button playBtn, exitBtn, backBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBtn.setOnAction(event -> {
            container.setVisible(true);
            showComponent("/MultipleChoiceGame/fxml/QuizUI.fxml");
        });
    }

    @FXML
    public void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(NavigationController.class.getResource(path)));
            container.getChildren().clear();
            container.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
