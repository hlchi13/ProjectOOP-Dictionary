package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class NavigationController implements Initializable {
    @FXML
    private AnchorPane container;
    @FXML
    private Button mainDict, saveList, gameBtn, exitBtn, translateBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainDict.setOnAction(event -> showComponent("/fxml/SearchUI.fxml"));

        saveList.setOnMouseClicked(mouseEvent -> showComponent("/fxml/Bookmark.fxml"));
        gameBtn.setOnMouseClicked(mouseEvent -> showComponent("/MultipleChoiceGame/fxml/HomeGameUI.fxml"));
        translateBtn.setOnAction(e -> showComponent("/fxml/TranslateUI.fxml"));
        try {
            showComponent("/fxml/SearchUI.fxml");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        exitBtn.setOnMouseClicked(e -> System.exit(0));
    }

    @FXML
    void addWordButton(MouseEvent event) {
        showComponent("/fxml/AddWord.fxml");
    }

    public void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    @FXML
    public void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(NavigationController.class.getResource(path)));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
