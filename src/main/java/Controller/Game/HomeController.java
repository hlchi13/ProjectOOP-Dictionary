package Controller.Game;

import Controller.NavigationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class HomeController implements Initializable {
    @FXML
    private AnchorPane container;
    @FXML
    private Button playBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBtn.setOnAction(event -> {
            container.setVisible(true);
            showComponent("/Game/fxml/CatWord.fxml");
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
