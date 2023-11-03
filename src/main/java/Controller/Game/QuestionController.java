package Controller.Game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Game.MultipleChoice.Questions;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;
import java.util.List;

public class QuestionController implements Initializable {
    private Questions game = new Questions();
    private List<String> ques;
    private List<List<String>> allAns;
    private List<String> correctAns;
    private int scoreInt = 0;
    private static int counter = 0;
    public static final int NUM_OF_QUESTIONS = 10;
    private static int countCorrect = 0;
    @FXML
    private Button exitBtn, op1, op2, op3, op4, nextBtn;
    @FXML
    private TextField question;
    @FXML
    private TextField score;

    public static int getCountCorrect() {
        return countCorrect;
    }

    public void setQuiz() {
        game.getFormFile();
        ques = game.getQuestions();
        allAns = game.getChoices();
        correctAns = game.getCorrectAns();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQuiz();
        exitBtn.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });
        int id;
        id = new Random().nextInt(ques.size());
        setNewQuestion(id);
        mouseAction(id);
        counter = 1;
        score.setText(String.valueOf(scoreInt));
        nextBtn.setOnAction(event -> {
            int newId = new Random().nextInt(ques.size());
            if (newId == id) {
                newId = new Random().nextInt(ques.size());
            }
            if (counter < NUM_OF_QUESTIONS) {
                setNewQuestion(newId);
                mouseAction(newId);
                counter++;
            } else {

            }
        });
    }

    public void mouseAction(int idx) {
        System.out.println(correctAns.get(idx));
        op1.setOnAction(event -> {
            System.out.println(correctAns.get(idx));
            if (op1.getText().equals(correctAns.get(idx))) {
                op1.setStyle("-fx-background-color: green");
                op2.setDisable(true);
                op3.setDisable(true);
                op4.setDisable(true);
                scoreInt += 10;
                score.setText(String.valueOf(scoreInt));
                countCorrect++;
            } else {
                op1.setStyle("-fx-background-color: red;" +
                        " -fx-text-fill: #fff");
                if (correctAns.get(idx).equals(op2.getText())) {
                    op2.setStyle("-fx-background-color: green");
                    op3.setDisable(true);
                    op4.setDisable(true);
                } else if (correctAns.get(idx).equals(op3.getText())) {
                    op3.setStyle("-fx-background-color: green");
                    op2.setDisable(true);
                    op4.setDisable(true);
                } else {
                    op4.setStyle("-fx-background-color: green");
                    op3.setDisable(true);
                    op2.setDisable(true);
                }
            }
//            removeQuestion(idx);
        });
        op2.setOnAction(event -> {
            if (correctAns.get(idx).equals(op2.getText())) {
                op2.setStyle("-fx-background-color: green");
                op1.setDisable(true);
                op3.setDisable(true);
                op4.setDisable(true);
                scoreInt += 10;
                score.setText(String.valueOf(scoreInt));
                countCorrect++;
            } else {
                op2.setStyle("-fx-background-color: red; " +
                        "-fx-text-fill: #fff");
                if (correctAns.get(idx).equals(op1.getText())) {
                    op1.setStyle("-fx-background-color: green");
                    op3.setDisable(true);
                    op4.setDisable(true);
                } else if (correctAns.get(idx).equals(op3.getText())) {
                    op3.setStyle("-fx-background-color: green");
                    op1.setDisable(true);
                    op4.setDisable(true);
                } else {
                    op4.setStyle("-fx-background-color: green");
                    op3.setDisable(true);
                    op1.setDisable(true);
                }
            }
//            removeQuestion(idx);
        });
        op3.setOnAction(event -> {
            if (op3.getText().compareTo(correctAns.get(idx)) == 0) {
                op3.setStyle("-fx-background-color: green");
                op1.setDisable(true);
                op2.setDisable(true);
                op4.setDisable(true);
                scoreInt += 10;
                score.setText(String.valueOf(scoreInt));
                countCorrect++;
            } else {
                op3.setStyle("-fx-background-color: red; -fx-text-fill: #fff");
                if (correctAns.get(idx).equals(op1.getText())) {
                    op1.setStyle("-fx-background-color: green");
                    op2.setDisable(true);
                    op4.setDisable(true);
                } else if (correctAns.get(idx).equals(op2.getText())) {
                    op2.setStyle("-fx-background-color: green");
                    op1.setDisable(true);
                    op4.setDisable(true);
                } else {
                    op4.setStyle("-fx-background-color: green");
                    op2.setDisable(true);
                    op1.setDisable(true);
                }
            }
//            removeQuestion(idx);
        });
        op4.setOnAction(event -> {
            if (op4.getText().compareTo(correctAns.get(idx)) == 0) {
                op4.setStyle("-fx-background-color: green");
                op1.setDisable(true);
                op3.setDisable(true);
                op2.setDisable(true);
                scoreInt += 10;
                score.setText(String.valueOf(scoreInt));
                countCorrect++;
            } else {
                op4.setStyle("-fx-background-color: red; -fx-text-fill: #fff");
                if (correctAns.get(idx).equals(op1.getText())) {
                    op1.setStyle("-fx-background-color: green");
                    op3.setDisable(true);
                    op2.setDisable(true);
                } else if (correctAns.get(idx).equals(op2.getText())) {
                    op2.setStyle("-fx-background-color: green");
                    op1.setDisable(true);
                    op3.setDisable(true);
                } else {
                    op3.setStyle("-fx-background-color: green");
                    op1.setDisable(true);
                    op2.setDisable(true);
                }
            }
//            removeQuestion(idx);
        });
    }

    public void setNewQuestion(int idx) {
        op1.setDisable(false);
        op2.setDisable(false);
        op3.setDisable(false);
        op4.setDisable(false);
        op1.setStyle("-fx-background-color: #fbeaeb");
        op2.setStyle("-fx-background-color: #fbeaeb");
        op3.setStyle("-fx-background-color: #fbeaeb");
        op4.setStyle("-fx-background-color: #fbeaeb");
        ArrayList<String> ans = new ArrayList<>(allAns.get(idx));
        Collections.shuffle(ans);
        op1.setText(ans.get(0));
        op2.setText(ans.get(1));
        op3.setText(ans.get(2));
        op4.setText(ans.get(3));
        question.setText(ques.get(idx));
    }

    public void removeQuestion(int idx) {
        ques.remove(idx);
        allAns.remove(idx);
        correctAns.remove(idx);
    }

}
