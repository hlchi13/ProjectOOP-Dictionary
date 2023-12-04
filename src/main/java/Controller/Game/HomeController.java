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
    private Button catWordBtn, memoryBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        catWordBtn.setOnAction(event -> {
            container.setVisible(true);
            showComponent("/Game/fxml/CatWord.fxml");
        });

        memoryBtn.setOnAction(event -> {
            container.setVisible(true);
            showComponent("/Game/fxml/Memory.fxml");
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
