package Dictionary;

import org.jsoup.Jsoup;

import java.sql.*;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;

    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    public void insertFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of words: ");
        int numberOfWords = sc.nextInt();
        sc.nextLine();
            for (int i = 0; i < numberOfWords; i++) {
                System.out.println("Enter the word target: ");
                String word_target = sc.nextLine();
                System.out.println("Enter the word explain: ");
                String word_explain = sc.nextLine();
                Word newWord = new Word();
//                newWord.setWord_explain(word_explain);
//                newWord.setWord_target(word_target);
                dictionary.addToList(newWord);
            }
    }
}
