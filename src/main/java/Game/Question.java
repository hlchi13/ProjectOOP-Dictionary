package Game;

public class Question {
    protected String title;
    protected String correctAns;

    public Question() {
        this.title = "";
        this.correctAns = "";
    }

    public Question(String title, String answer) {
        this.title = title;
        this.correctAns = answer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getTitle() {
        return title;
    }

    public String getCorrectAns() {
        return correctAns;
    }
}
