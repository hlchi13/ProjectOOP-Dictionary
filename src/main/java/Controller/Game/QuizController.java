package Controller.Game;

import MultipleChoiceGame.MultipleChoice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import MultipleChoiceGame.Question;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

public class QuizController implements Initializable {
    private MultipleChoice game = new MultipleChoice();
    private List<Question> questionList = new ArrayList<>();
    private int scoreInt = 0;
    private static int counter = 0;
    public static final int NUM_OF_QUESTIONS = 10;
    private static int countCorrect = 0;
    private static int countWrong = 0;
    @FXML
    private Button exitBtn, op1, op2, op3, op4, nextBtn;
    @FXML
    private TextField questionTitle;
    @FXML
    private Label score;
    @FXML
    private Label correctCnt, questionId;
    private File background, file;
    private Media bgSound, sound;
    private MediaPlayer bgMPlayer, mediaPlayer;

    public int getCountCorrect() {
        return countCorrect;
    }

    public int getCountWrong() {
        return countWrong;
    }

    public void setQuiz() {
        game.getFromFile();
        questionList = game.getQuestions();
        Collections.shuffle(questionList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        background = new File("src/main/resources/MultipleChoiceGame/sound/gameBg.mp3");
        bgSound = new Media(background.toURI().toString());
        bgMPlayer = new MediaPlayer(bgSound);
        bgMPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgMPlayer.play();
        setQuiz();
        exitBtn.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        });
        setNewQuestion(counter);
        mouseAction(counter);
        score.setText("Score: " + scoreInt);
        correctCnt.setText("Correct: " + countCorrect + "/" + NUM_OF_QUESTIONS);
        questionId.setText("Question: " + counter + 1);
        counter++;
        if (counter < NUM_OF_QUESTIONS) {
            nextBtn.setOnAction(event -> {
                counter++;
                setNewQuestion(counter);
                mouseAction(counter);
                questionId.setText("Question: " + counter);

            });
        }


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
        questionTitle.setText(questionList.get(idx).getTitle());
        List<String> choices = new ArrayList<>(questionList.get(idx).getChoices());
        Collections.shuffle(choices);
        op1.setText(choices.get(0));
        op2.setText(choices.get(1));
        op3.setText(choices.get(2));
        op4.setText(choices.get(3));
    }

    public void showCorrectAns(int idx, Button choose, Button other1, Button other2, Button other3) {
        bgMPlayer.pause();
        String correctAns = questionList.get(idx).getCorrectAns();
        if (choose.getText().equals(correctAns)) {
            choose.setStyle("-fx-background-color: green; -fx-text-fill: black");
            scoreInt += 10;
            score.setText("Score: " + scoreInt);
            countCorrect++;
            correctCnt.setText("Correct: " + countCorrect + "/" + NUM_OF_QUESTIONS);
            file = new File("src/main/resources/MultipleChoiceGame/sound/correct.mp3");
            sound = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            bgMPlayer.pause();
        } else {
            countWrong++;
            file = new File("src/main/resources/MultipleChoiceGame/sound/wrong.mp3");
            sound = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            choose.setStyle("-fx-background-color: red;" +
                    " -fx-text-fill: #ddd");
        }
        other2.setDisable(true);
        other3.setDisable(true);
        other1.setDisable(true);
        bgMPlayer.play();
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
