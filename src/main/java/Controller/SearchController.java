package Controller;

import Dictionary.DatabaseDictionary;
import Dictionary.Dictionary;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private Dictionary dictionary;
    private DatabaseDictionary dtb;
    private String target;
    @FXML
    private ListView<String> listview;
    @FXML
    private TextField searchBar;
    @FXML
    private Button speakerUSButton, speakerVNButton, editButton, searchBtn, exitBtn;
    @FXML
    private ImageView saveBtn;
    @FXML
    void editButton(MouseEvent event) {

    }

    @FXML
    void exitButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void favouriteButton(MouseEvent event) {

    }

    @FXML
    void ggTranslateButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/TranslateUI.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setTitle("Translator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void infoButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/game/fxml/HomeGameUI.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setTitle("Let's play a game!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void saveButton(MouseEvent event) {

    }

    @FXML
    void searchButton(MouseEvent event) {

    }

    @FXML
    void settingButton(MouseEvent event) {

    }

    @FXML
    void speakEnButton(MouseEvent event) {

    }

    @FXML
    void speakVnButton(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //todo
        searchBtn.setOnAction(evt->{
            dtb = new DatabaseDictionary();
            String target = searchBar.getText();
            System.out.println(dtb.showExplain(target));
        });
    }
}

