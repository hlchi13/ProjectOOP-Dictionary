package Controller;

import Dictionary.TranslatorAPI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

public class TranslateController implements Initializable {
    @FXML
    private Button transBtn, changeBtn, exitBtn;
    @FXML
    private Button speakTarget, speakToTrans;
    @FXML
    private Label labelFrom, labelTo;
    @FXML
    private TextArea textFrom, textTo;

    private String lFrom, lTo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelFrom.setText("English");
        labelTo.setText("Vietnamese");
        lFrom = "en";
        lTo = "vi";
        transBtn.setOnAction(event -> {
            try{
                textTo.setText(TranslatorAPI.translate(lFrom, lTo, textFrom.getText()));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Translate");
                alert.setHeaderText("Translator API:");
                alert.setContentText("Vượt quá số kí tự có thể dịch được");

                alert.showAndWait();
                e.printStackTrace();
            }
        });
        changeBtn.setOnAction(event -> {
            if (labelFrom.getText().equals("English")) {
                labelFrom.setText("Vietnamese");
                labelTo.setText("English");
                lFrom = "vi";
                lTo = "en";
            } else {
                labelFrom.setText("English");
                labelTo.setText("Vietnamese");
                lFrom = "en";
                lTo = "vi";
            }
        });

        exitBtn.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });
        speakTarget.setOnAction(event -> {
            if (!textFrom.getText().isEmpty()) {
                TranslatorAPI.playGoogleSound(textFrom.getText(), lFrom);

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Translate Play Sound:");
                alert.setContentText("Không có từ nào");

                alert.showAndWait();
            }
        });
        speakToTrans.setOnAction(event -> {
            TranslatorAPI.playGoogleSound(textTo.getText(), lTo);
        });
    }

    @FXML
    public void clear() {
        textFrom.clear();
        textTo.clear();
    }
    @FXML
    public void translateWord() {

    }
}
