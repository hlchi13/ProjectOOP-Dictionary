package Game.Question;

import Game.Question.Question;

import java.util.Collections;
import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> choices;

    public MultipleChoiceQuestion() {}

    public MultipleChoiceQuestion(String title, String correctAns, List<String> choices) {
        super(title, correctAns);
        this.choices = choices;
    }

    public List<String> getChoices() {
        return choices;
    }

    @Override
    public boolean isTrue(String answer) {
        return super.isTrue(answer);
    }
}
