package Game.MultipleChoiceGame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoice {
    private List<MultipleChoiceQuestion> questions;
    private String title;
    private List<String> choices;
    private String correct;
    private boolean isRunning;

    public MediaPlayer playerBg = new MediaPlayer(new Media(new File("src/main/resources/MultipleChoiceGame/sound/gameBg.mp3").toURI().toString()));
    public MultipleChoice() {
        this.isRunning = true;
        playerBg.play();
    }

    public MultipleChoice(List<MultipleChoiceQuestion> questions) {
        this.questions = questions;
    }

    public void stopMusic() {
        playerBg.stop();
    }
    public List<MultipleChoiceQuestion> getQuestions() {
        return questions;
    }

    public List<MultipleChoiceQuestion> getFromFile() {
        questions = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("src/main/resources/MultipleChoiceGame/questions.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] quiz = line.split("#");
                choices = new ArrayList<>();
                title = quiz[0];
                choices.add(quiz[1]);
                choices.add(quiz[2]);
                choices.add(quiz[3]);
                choices.add(quiz[4]);
                correct = quiz[5];
                MultipleChoiceQuestion question = new MultipleChoiceQuestion(title, choices, correct);
                questions.add(question);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
