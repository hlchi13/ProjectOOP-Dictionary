package Game.MultipleChoiceGame;

import java.util.ArrayList;
import java.util.List;

public class GameManagement {
    private MultipleChoice game = new MultipleChoice();
    private List<Question> questionList = new ArrayList<>();


    public GameManagement() {}

    public List<Question> getQuestionsList() {
        questionList = game.getFromFile();
        return questionList;
    }


}
