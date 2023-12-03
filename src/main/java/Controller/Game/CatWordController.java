package Controller.Game;


import Game.CatWord.QuestionList;
import Game.CatWord.CatWord;
import Game.Entities.Cat;
import Game.Entities.CorrectMark;
import Game.Entities.Wall;
import Game.GameTime;
import Game.Question.FillWordQuestion;
import Game.Question.MultipleChoiceQuestion;
import Game.Question.Question;

import Game.Sprite.Sprite;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class CatWordController implements Initializable {
    public static CatWord game;
    private QuestionList quiz = new QuestionList();
    private List<Question> questionList = quiz.getQuestionList();
    private int count;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private int cntWrong = 0;
    private Media wrong_sound = new Media(
            new File("./src/main/resources/Game/sound/wrong.mp3").toURI().toString());
    private Media correct_sound = new Media(
            new File("./src/main/resources/Game/sound/correct.mp3").toURI().toString());
    @FXML
    private AnchorPane root, rootGame,
            questionField, lose, win, playAgainField;
    @FXML
    private TextField answerField;
    @FXML
    private Button op1, op2, op3, op4, submit;
    @FXML
    private Label timerLabel, question;
    public static GameTime time;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionField.setVisible(false);
        count = 0;
        game = new CatWord();
        time = new GameTime(timerLabel, 45);
        quiz.init();
        Collections.shuffle(questionList);
        Canvas canvas = new Canvas(320, 320);
        gc = canvas.getGraphicsContext2D();
        rootGame.getChildren().add(canvas);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameUpdate();
                questionUpdate();
                game.render(gc);
            }
        };
        gameLoop.start();
        time.run();

        op1.setOnAction(event -> { answerMultipleChoice(op1);});
        op2.setOnAction(event -> { answerMultipleChoice(op2);});
        op3.setOnAction(event -> { answerMultipleChoice(op3);});
        op4.setOnAction(event -> { answerMultipleChoice(op4);});
    }

    public void gameUpdate() {
        Cat cat = game.getMap().getCat();
        if (!cat.isQuestion()) {
            rootGame.requestFocus();
            rootGame.setOnKeyPressed(keyEvent -> {
                game.update(keyEvent);
            });
        }
        if (cat.getxUnit() == 7 && cat.getyUnit() == 7) {
            gameLoop.stop();
            game.setRunning(false);
            win.setVisible(true);
            playAgainField.setVisible(true);

        }
        if (cntWrong == 2 || time.isEnd()) {
            gameLoop.stop();
            time.setEnd(true);
            game.setRunning(false);
            playAgainField.setVisible(true);
            lose.setVisible(true);
        }
    }

    @FXML
    public void onPlayAgain() {
        refresh();
    }

    @FXML
    public void onExit() {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource("/Game/fxml/HomeGameUI.fxml"));
            root.getChildren().clear();
            root.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void questionUpdate() {
        Cat cat = game.getMap().getCat();
        if (cat.isQuestion()) {
            questionField.setVisible(true);
            questionField.requestFocus();
            Question ques = questionList.get(count);
            if (ques instanceof FillWordQuestion) {
                answerField.setVisible(true);
                submit.setVisible(true);
                answerField.requestFocus();
                question.setText(ques.getTitle());
                op1.setVisible(false);
                op2.setVisible(false);
                op3.setVisible(false);
                op4.setVisible(false);
            } else {
                answerField.setVisible(false);
                submit.setVisible(false);
                op1.setVisible(true);
                op2.setVisible(true);
                op3.setVisible(true);
                op4.setVisible(true);
                question.setText(ques.getTitle());
                setMultipleChoice((MultipleChoiceQuestion) ques);
            }
        }
    }

    private void setMultipleChoice(MultipleChoiceQuestion question) {
        List<String> choices = question.getChoices();
        op1.setText(choices.get(0));
        op2.setText(choices.get(1));
        op3.setText(choices.get(2));
        op4.setText(choices.get(3));
    }

    public void answerMultipleChoice(Button choose) {
        Cat cat = game.getMap().getCat();
        answerField.clear();
        if (cat.isQuestion() && questionList.get(count) instanceof MultipleChoiceQuestion) {
            String answer = choose.getText();
            int x = cat.getxUnit();
            int y = cat.getyUnit();
            System.out.println(questionList.get(count).getCorrectAns());
            if (questionList.get(count).getCorrectAns().equals(answer)) {
                game.getMap().updateMap(x, y, new CorrectMark(x, y, Sprite.correct_mark.getFxImage()));
                MediaPlayer correct = new MediaPlayer(correct_sound);
                correct.play();
            } else {
                game.getMap().updateMap(x, y, new Wall(x, y, Sprite.wall.getFxImage()));
                cat.back();
                cntWrong++;
                MediaPlayer wrong = new MediaPlayer(wrong_sound);
                wrong.play();
            }
        }
        count++;
        answerField.clear();
        cat.setQuestion(false);
        questionField.setVisible(false);
    }

    @FXML
    public void onSubmit() {
        Cat cat = game.getMap().getCat();
        if (cat.isQuestion() && questionList.get(count) instanceof FillWordQuestion) {
            String answer = answerField.getText().trim().toUpperCase();
            int x = cat.getxUnit();
            int y = cat.getyUnit();
            if (questionList.get(count).isTrue(answer)) {
                game.getMap().updateMap(x, y, new CorrectMark(x, y, Sprite.correct_mark.getFxImage()));
                question.setText(questionList.get(count).getTitle().replace('_', answer.charAt(0)));
                MediaPlayer correct = new MediaPlayer(correct_sound);
                correct.play();
            } else {
                game.getMap().updateMap(x, y, new Wall(x, y, Sprite.wall.getFxImage()));
                cat.back();
                String ans = questionList.get(count).getTitle();
                ans = "Correct answer: " + ans.replace('_', questionList.get(count).getCorrectAns().charAt(0));
                question.setText(ans);
                cntWrong++;
                MediaPlayer wrong = new MediaPlayer(wrong_sound);
                wrong.play();
            }
            count++;
            answerField.clear();
            cat.setQuestion(false);
            questionField.setVisible(false);
        }
    }

    @FXML
    private void onEnter() {
        onSubmit();
    }

    public void refresh() {
        playAgainField.setVisible(false);
        lose.setVisible(false);
        win.setVisible(false);
        count = 0;
        cntWrong = 0;
        questionField.setVisible(false);
        answerField.clear();
        game = new CatWord();
        time = new GameTime(timerLabel, 45);
        Collections.shuffle(questionList);
        gameLoop.start();
        time.run();
    }

    public static void setEnd() {
        if (game != null) {
            game.setRunning(false);
        }
        if (time != null) {
            time.setEnd(true);
        }
    }
}

