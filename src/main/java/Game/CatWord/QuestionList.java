package Game.CatWord;


import Dictionary.DatabaseDictionary;
import Game.Question.FillWordQuestion;
import Game.Question.MultipleChoiceQuestion;
import Game.Question.Question;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionList {
    private List<Question> questionList = new ArrayList<>();

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void initFillWordQuestion() {
        List<String> allWord = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("src/main/resources/Game/Words.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                String[] quiz = line.split("\t");
                allWord.add(quiz[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(allWord);
        List<String> wordList = allWord.subList(0, 20);
        for (String x : wordList) {
            if (!(x.trim().equals(""))) {
                int random = (int) (Math.random() * x.length());
                String answer = String.valueOf(x.charAt(random)).toUpperCase();
                String ques = x.replace(x.charAt(random), '_');
                Question question = new FillWordQuestion(ques.toUpperCase(), answer);
                questionList.add(question);
            }
        }
    }

    public void initMultipleChoiceQuestion() {
        String title;
        String correct;
        List<String> choices;
        try {
            FileReader fileReader = new FileReader("src/main/resources/Game/questions.txt");
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
                Collections.shuffle(choices);
                MultipleChoiceQuestion question = new MultipleChoiceQuestion(title, correct, choices);
                questionList.add(question);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        initFillWordQuestion();
        initMultipleChoiceQuestion();
    }
}
