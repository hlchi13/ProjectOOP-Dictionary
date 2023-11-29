package Controller.MainDictionary;

import Dictionary.DatabaseDictionary;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    @FXML
    private TextArea newWordTarget, newWordMeaning;
    @FXML
    private Button saveWord, cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void saveNewWord(MouseEvent e) {
        String newWord = newWordTarget.getText();
        String newMeaning = newWordMeaning.getText();
        if (DatabaseDictionary.lookUpWord(newWord).equals("No words were found")) {
            if (DatabaseDictionary.insertWord(newWord, newMeaning)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setContentText(newWord + " has been successfully added to the dtb");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setContentText(newWord + " cant be added to the dictionary");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setContentText(newWord + " had already been in the dictionary");
            alert.show();
        }
    }

    @FXML
    void cancelAddWord() {
        if (newWordTarget.getText().isEmpty() && newWordMeaning.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty");
            alert.showAndWait();
        } else {
            newWordTarget.clear();
            newWordMeaning.clear();
        }
    }
}
