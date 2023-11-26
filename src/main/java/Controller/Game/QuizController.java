package Controller.Game;

import Controller.NavigationController;
import Game.MultipleChoiceGame.GameManagement;
import Game.MultipleChoiceGame.MultipleChoice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import Game.MultipleChoiceGame.Question;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class QuizController implements Initializable {
    private GameManagement game = new GameManagement();
    private List<Question> questionList = new ArrayList<>();
    private static final int NUM_OF_QUESTIONS = 5;
    private static int counter = 0;
    private static int countCorrect = 0;
    private static int countWrong = 0;
    private static int scoreInt = 0;

    public static int getCountCorrect() {
        return countCorrect;
    }

    public static int getCountWrong() {
        return countWrong;
    }

    public static int getScoreInt() {
        return scoreInt;
    }
    @FXML
    private Button exitBtn, op1, op2, op3, op4, nextBtn;
    @FXML
    private Label questionTitle;
    @FXML
    private Label score;
    @FXML
    private Label correctCnt, questionId;
    private File file;
    private Media sound;
    private MediaPlayer mediaPlayer;

    @FXML
    private AnchorPane result;
    @FXML
    private ImageView wrong ,correct;

    private void refresh() {
        counter = 0;
        countCorrect = 0;
        countWrong = 0;
        scoreInt = 0;
        setQuiz();
    }

    public void setQuiz() {
        questionList = game.getQuestionsList();
        Collections.shuffle(questionList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
        setNewQuestion(counter);
        mouseAction(counter);
        score.setText("Score: " + scoreInt);
        correctCnt.setText("Correct: " + countCorrect + "/" + NUM_OF_QUESTIONS);
        questionId.setText("Question: " + counter + 1);
        counter++;
            nextBtn.setOnAction(event -> {
                if (counter < NUM_OF_QUESTIONS) {
                    counter++;
                    setNewQuestion(counter);
                    mouseAction(counter);
                    questionId.setText("Question: " + counter);
                } else {
                        try {
                            AnchorPane component = FXMLLoader.load(Objects.requireNonNull(NavigationController.class.getResource("/MultipleChoiceGame/fxml/Result.fxml")));
                            result.getChildren().clear();
                            result.getChildren().add(component);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            });
    }

    public void setNewQuestion(int idx) {
        wrong.setVisible(false);
        correct.setVisible(false);
        op1.setDisable(false);
        op2.setDisable(false);
        op3.setDisable(false);
        op4.setDisable(false);
        questionTitle.setText(questionList.get(idx).getTitle());
        List<String> choices = new ArrayList<>(questionList.get(idx).getChoices());
        Collections.shuffle(choices);
        op1.setText(choices.get(0));
        op2.setText(choices.get(1));
        op3.setText(choices.get(2));
        op4.setText(choices.get(3));
    }

    public void showCorrectAns(int idx, Button choose, Button other1, Button other2, Button other3) {
        String correctAns = questionList.get(idx).getCorrectAns();
        if (choose.getText().equals(correctAns)) {
            correct.setVisible(true);
            scoreInt += 10;
            score.setText("Score: " + scoreInt);
            countCorrect++;
            correctCnt.setText("Correct: " + countCorrect + "/" + NUM_OF_QUESTIONS);
            file = new File("src/main/resources/MultipleChoiceGame/sound/correct.mp3");
            sound = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } else {
            wrong.setVisible(true);
            countWrong++;
            file = new File("src/main/resources/MultipleChoiceGame/sound/wrong.mp3");
            sound = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
        choose.setDisable(true);
        other2.setDisable(true);
        other3.setDisable(true);
        other1.setDisable(true);
    }

    public void mouseAction(int idx) {
        String correctAns = questionList.get(idx).getCorrectAns();
        System.out.println(correctAns);
        op1.setOnAction(event -> {
            System.out.println(correctAns);
            showCorrectAns(idx, op1, op2, op3, op4);
        });
        op2.setOnAction(event -> {
            showCorrectAns(idx, op2, op1, op3, op4);
        });
        op3.setOnAction(event -> {
            showCorrectAns(idx, op3, op1, op2, op4);
        });
        op4.setOnAction(event -> {
            showCorrectAns(idx, op4, op1, op2, op3);
        });
    }
}
