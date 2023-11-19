package Controller;

import Dictionary.DatabaseDictionary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddWordController implements Initializable {
    @FXML
    private Label Label1;
    @FXML
    private Label Label2;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private TextField inputBar;

    @FXML
    void saveButton(MouseEvent event) {
        String newWord = inputBar.getText();
        String newDefinition = htmlEditor.getHtmlText();
        newDefinition = newDefinition.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
        newDefinition = newDefinition.replace("</body></html>", "");
        newDefinition = newDefinition.replace("\"", "'");
        if (DatabaseDictionary.insertWord(newWord, newDefinition)) {
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> inputBar.requestFocus());
    }
}