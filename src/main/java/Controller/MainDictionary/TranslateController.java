package Controller.MainDictionary;

import Dictionary.TranslatorAPI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TranslateController implements Initializable {
    @FXML
    private Button transBtn, changeBtn;
    @FXML
    private Button speakTarget, speakToTrans;
    @FXML
    private Label labelFrom, labelTo;
    @FXML
    private TextArea sourceArea, translateArea;

    private String lFrom, lTo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelFrom.setText("English");
        labelTo.setText("Vietnamese");
        lFrom = "en";
        lTo = "vi";
        transBtn.setOnAction(event -> {
            try{
                String translation = TranslatorAPI.translate(lFrom, lTo, sourceArea.getText());
                translateArea.setText(translation);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information");
                alert.setHeaderText("Translator API:");
                alert.setContentText("Exceeding the number of characters can be translated");
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
            String tmp = translateArea.getText();
            translateArea.setText(sourceArea.getText());
            sourceArea.setText(tmp);
        });

        sourceArea.textProperty().addListener((observable, oldValue, newValue)-> {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(()->{
                try {
                    String translation = TranslatorAPI.translate(lFrom, lTo, newValue);
                    translateArea.setText(translation);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information");
                    alert.setHeaderText("Translator API:");
                    alert.setContentText("Exceeding the number of characters can be translated");
                    alert.showAndWait();
                    e.printStackTrace();
                }
            });
            executorService.shutdown();
        });
    }

    @FXML
    public void clear() {
        if (sourceArea.getText().isEmpty() && translateArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty");
            alert.showAndWait();
        } else {
            sourceArea.clear();
            translateArea.clear();
        }
    }


    @FXML
    public void speakingSource() {
        speaking(sourceArea, lFrom);
    }

    @FXML
    public void speakingTranslate() {
        speaking(translateArea, lTo);
    }

    private void speaking(TextArea area, String language) {
        if (!area.getText().isEmpty()) {
            TranslatorAPI.playGoogleSound(area.getText(), language);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Empty");
            alert.showAndWait();
        }
    }
}
