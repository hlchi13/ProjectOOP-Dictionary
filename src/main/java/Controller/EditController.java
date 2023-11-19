package Controller;

import Dictionary.DatabaseDictionary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class EditController implements Initializable {
    private static String editingWord;
    @FXML
    private Button exitButton;
    @FXML
    private Button saveButton;
    @FXML
    private HTMLEditor htmlEditor;

    public static void setEditingWord(String editingWord) {
        EditController.editingWord = editingWord;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        htmlEditor.setHtmlText(DatabaseDictionary.lookUpWordEditWindow(editingWord));
    }
    @FXML
    void saveTheDefinition(MouseEvent event) {;
        String newDefinition = htmlEditor.getHtmlText();
        newDefinition = newDefinition.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
        newDefinition = newDefinition.replace("</body></html>", "");
        newDefinition = newDefinition.replace("\"", "'");
        if (DatabaseDictionary.updateWordDefinition(editingWord, newDefinition)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setContentText(editingWord + " is successfully updated");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setContentText(editingWord + " is failed to update");
            alert.show();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

