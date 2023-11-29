package Game.MultipleChoiceGame;

import Game.Question;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> choices;

    public MultipleChoiceQuestion() {}

    public MultipleChoiceQuestion(MultipleChoiceQuestion question) {
        this.title = question.title;
        this.choices = question.getChoices();
        this.correctAns = question.correctAns;
    }
    public MultipleChoiceQuestion(String title, List<String> choices, String correctAns) {
        this.title = title;
        this.choices = choices;
        this.correctAns = correctAns;
    }

    public List<String> getChoices() {
        return choices;
    }
}
