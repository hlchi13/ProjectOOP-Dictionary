package Dictionary;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> wordList = new ArrayList<>();

    public Dictionary() {}

    public void addToList(Word w) {
        wordList.add(w);
    }

    public int getLength() {
        return wordList.size();
    }

    public Word getWord(int index) {
        return wordList.get(index);
    }

    public ArrayList<Word> getDict() {
        return wordList;
    }
}
