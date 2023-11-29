package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class NavigationController implements Initializable {
    @FXML
    private AnchorPane container;
    @FXML
    private Button mainDict, bookmarkList, gameBtn, exitBtn, translateBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainDict.setOnAction(event -> showComponent("/Dictionary/fxml/SearchUI.fxml"));
        bookmarkList.setOnMouseClicked(mouseEvent -> showComponent("/Dictionary/fxml/Bookmark.fxml"));
        gameBtn.setOnMouseClicked(mouseEvent -> showComponent("/MultipleChoiceGame/fxml/HomeGameUI.fxml"));
        translateBtn.setOnAction(e -> showComponent("/Dictionary/fxml/TranslateUI.fxml"));
        try {
            showComponent("/Dictionary/fxml/SearchUI.fxml");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        exitBtn.setOnMouseClicked(e -> System.exit(0));
    }

    @FXML
    void addWordButton(MouseEvent event) {
        showComponent("/Dictionary/fxml/AddWord.fxml");
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
