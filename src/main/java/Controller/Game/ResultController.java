package Controller.Game;

import Controller.NavigationController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    @FXML
    private Label text, score, correctCnt;
    @FXML
    private AnchorPane result;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int correct = QuizController.getCountCorrect();
        int wrong = QuizController.getCountWrong();

        correctCnt.setText("Correct Answer: " + correct);
        score.setText("Correct Answer: " + QuizController.getScoreInt());

    }

    @FXML
    private void playAgain() {
        try {
            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(NavigationController.class.getResource("/MultipleChoiceGame/fxml/QuizUI.fxml")));
            result.getChildren().clear();
            result.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
