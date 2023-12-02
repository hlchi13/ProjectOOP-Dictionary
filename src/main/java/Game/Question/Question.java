package Game.Question;

public abstract class Question {
    protected String title;
    protected String correctAns;

    public Question() {
        
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

    public boolean isTrue(String answer) {
        if (answer.equals(correctAns)) {
            return true;
        }
        return false;
    }

}
