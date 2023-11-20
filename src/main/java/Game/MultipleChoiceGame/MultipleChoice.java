package MultipleChoiceGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoice {
    private List<Question> questions;
    private String title;
    private List<String> choices;
    private String correct;

    public MultipleChoice() {}
    public MultipleChoice(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void getFromFile() {
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
                Question question = new Question(title, choices, correct);
                questions.add(question);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MultipleChoice game = new MultipleChoice();
        game.getFromFile();
        List<Question> questionList = game.questions;

        for (Question s: questionList) {
//            System.out.println(s.getTitle());
            System.out.println(s.getChoices());
        }
    }
}
