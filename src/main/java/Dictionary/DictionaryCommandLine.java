package Dictionary;

import java.util.ArrayList;

public class DictionaryCommandLine {
    private Dictionary dictionary;
    private DictionaryManagement dict_manage;
    private ArrayList<Word> listWordToPrint;

    public DictionaryCommandLine(Dictionary dictionary) {
        this.dictionary = dictionary;
        dict_manage = new DictionaryManagement(dictionary);
    }

    public DictionaryCommandLine(Dictionary dictionary, DictionaryManagement dict_manager)
    {
        this.dictionary = dictionary;
        this.dict_manage = dict_manager;
    }

    public void showAllWords()
    {
        if (listWordToPrint == null)
            listWordToPrint = new ArrayList<Word>(dictionary.getDict());
        boolean isEmpty = true;
        int N = 0;
        for (int i = 0; i < listWordToPrint.size(); i++) {
            if (listWordToPrint.get(i) == null) continue;
            if (isEmpty)
            {
                isEmpty = false;
                System.out.printf("%-6s|%-35s|%s%n%n", "No", "English", "Vietnamese");
            }
            System.out.printf("%-6d|%-35s|%s%n", ++N, listWordToPrint.get(i).getWordTarget(),
                    listWordToPrint.get(i).getWordExplain());
        }
        if (isEmpty)
        {
            System.out.println("The are's any word in this Dictionary!!!");
        }
        listWordToPrint = null;
    }
}
