package MultipleChoiceGame;

import java.util.List;

public class Question {
    private String title;
    private List<String> choices;
    private String correctAns;

    public Question() {}

    public Question(Question question) {
        this.title = question.title;
        this.choices = question.getChoices();
        this.correctAns = question.correctAns;
    }
    public Question(String title, List<String> choices, String correctAns) {
        this.title = title;
        this.choices = choices;
        this.correctAns = correctAns;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getCorrectAns() {
        return correctAns;
    }
}
