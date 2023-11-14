package Dictionary;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Trie {
    /**
     * Trie node constructor.
     */
    public static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();
        boolean endOfWord;
        public TrieNode() {
            endOfWord = false;
        }
    }

    private static final TrieNode root = new TrieNode();
    private static final ArrayList<String> searchedWords = new ArrayList<>();

    public static ArrayList<String> getSearchedWords() {
        return searchedWords;
    }

    /**
     * Insert single word into Trie.
     */
    public static void insertWord(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (current.children.get(ch) == null) {
                current.children.put(ch, new TrieNode());
            }
            current = current.children.get(ch);
        }
        current.endOfWord = true;
    }

    private static void searchWord(TrieNode current, String word) {
        if (current.endOfWord) {
            searchedWords.add(word);
        }
        for (Character c : current.children.keySet()) {
            if (current.children.get(c) != null) {
                searchWord(current.children.get(c), word + c);
            }
        }
    }

    /**
     * delete the trie node after the deletion update.
     */
    public static void deleteWord(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (current.children.get(ch) == null) {
                System.out.println("There is no such word in dtb");
                return;
            }
            current = current.children.get(ch);
        }
        if (!current.endOfWord) {
            System.out.println("There is no such word in dtb");
            return;
        }
        root.endOfWord = false;
    }

    public static ArrayList<String> advancedSearch(String prefix) {
        if (prefix.isEmpty()) {
            return new ArrayList<>();
        }
        searchedWords.clear();
        TrieNode current = root;

        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (current.children.get(ch) == null) {
                return getSearchedWords();
            }
            current = current.children.get(ch);
        }
        searchWord(current, prefix);
        return getSearchedWords();
    }
}