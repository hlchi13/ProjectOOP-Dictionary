package Controller.Game;

import Game.GameTime;
import Game.Memory.Memory;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import javafx.scene.media.Media;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MemoryController implements Initializable {

    ArrayList<Button> buttons = new ArrayList<>();

    Memory memoryGame;

    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML private Button button7;
    @FXML private Button button8;
    @FXML private Button button9;
    @FXML private Button button10;
    @FXML private Button button11;
    @FXML
    private Label timerLabel, turnLabel, loseReason;
    @FXML private AnchorPane container, lose, win, playAgainField;
    GameTime time;
    private AnimationTimer gameLoop;
    private static int cnt = 0;
    private static int turn = 25;
    private boolean firstButtonClicked = false;

    private int firstButtonIndex;
    private int secondButtonIndex;
    private boolean match;

    private final Media gameBg = new Media(new File("./src/main/resources/Game/sound/gameBg2.mp3").toURI().toString());
    private final Media correct = new Media(new File("./src/main/resources/Game/sound/correct2.mp3").toURI().toString());
    private final Media wrong = new Media(new File("./src/main/resources/Game/sound/wrong2.mp3").toURI().toString());
    private final Media click = new Media(new File("./src/main/resources/Game/sound/pick.mp3").toURI().toString());
    private MediaPlayer player;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = new MediaPlayer(gameBg);
        player.play();
        time = new GameTime(timerLabel, 30);
        memoryGame = new Memory();
        memoryGame.setupGame();
        buttons.addAll(Arrays.asList(
                button0, button1, button2, button3,
                button4, button5, button6, button7,
                button8, button9, button10, button11
        ));
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                turnLabel.setText("Turn left: " + turn);
                gameState();
            }
        };
        gameLoop.start();
        time.run();
    }

    @FXML
    void buttonClicked(ActionEvent event) {
        turn--;
        MediaPlayer playCorrect = new MediaPlayer(correct);
        MediaPlayer playWrong = new MediaPlayer(wrong);
        MediaPlayer playerClick = new MediaPlayer(click);
        playerClick.play();
        if(!firstButtonClicked){
            //If next turn is started before old buttons are hidden
            if(!match){
                hideButtons();
            }
            match = false;
            firstButtonClicked = true;
            //Get clicked button memory letter
            String buttonId = ((Control)event.getSource()).getId();
            firstButtonIndex = Integer.parseInt(buttonId.substring(6));  // Extract the entire index substring
            //Change clicked button text
            buttons.get(firstButtonIndex).setText(memoryGame.getOptionAtIndex(firstButtonIndex));

            return;
        }
        //Get clicked button memory letter
        String buttonId = ((Control)event.getSource()).getId();
        secondButtonIndex = Integer.parseInt(buttonId.substring(6));  // Extract the entire index substring
        //Change clicked button text
        buttons.get(secondButtonIndex).setText(memoryGame.getOptionAtIndex(secondButtonIndex));
        firstButtonClicked = false;
        //Check if the two clicked button match
        if(memoryGame.checkTwoPositions(firstButtonIndex,secondButtonIndex)){
            cnt++;
            System.out.println("Match");
            match = true;
            playerClick.stop();
            playCorrect.play();
        } else {
            playerClick.stop();
            playWrong.play();
        }
    }

    private void gameState() {
        if (cnt == 6) {
            gameLoop.stop();
            player.stop();
            win.setVisible(true);
            playAgainField.setVisible(true);
        }
        if (time.isEnd()) {
            gameLoop.stop();
            lose.setVisible(true);
            player.stop();
            time.setEnd(true);
            loseReason.setText("Time's up!");
            playAgainField.setVisible(true);
        }
        if (turn == 0) {
            gameLoop.stop();
            lose.setVisible(true);
            player.stop();
            time.setEnd(true);
            loseReason.setText("Out of turn!");
            playAgainField.setVisible(true);
        }
    }

    private void hideButtons(){
        buttons.get(firstButtonIndex).setText("");
        buttons.get(secondButtonIndex).setText("");
    }

    public void exitButtonOnAction (ActionEvent event) {
        player.stop();
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource("/Game/fxml/HomeGameUI.fxml"));
            container.getChildren().clear();
            container.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onPlayAgain() {
        time.setEnd(true);
        player.play();
        firstButtonClicked = false;
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText("");
        }
        turn = 25;
        win.setVisible(false);
        lose.setVisible(false);
        playAgainField.setVisible(false);
        time = new GameTime(timerLabel, 30);
        cnt = 0;
        memoryGame = new Memory();
        memoryGame.setupGame();
        gameLoop.start();
        time.run();
    }

    @FXML
    void onExitMemoryGame() {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource("/Game/fxml/HomeGameUI.fxml"));
            container.getChildren().clear();
            container.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}