package Controller;

import Dictionary.DatabaseDictionary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static Dictionary.Speech.speakInEn;

public class BookmarkController implements Initializable {
    private String currentSelectedWord = "";
    @FXML
    private Label target;

    @FXML
    private ListView<String> list;

    @FXML
    private TextArea definitionSite;

    private void prepareList() {
        List<String> bookmarkedWords = getSavedWords();
        list.getItems().setAll(bookmarkedWords);
    }

    private List<String> getSavedWords() {
        List<String> savedWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Bookmark.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                savedWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedWords;
    }

    private void search() {
        String definition = DatabaseDictionary.lookUpWord(currentSelectedWord);
        if (definition.equals("No words were found")) {
            definitionSite.setText("No word can be found");
        } else {
            definitionSite.setText(definition);
        }
    }

    private void delete(String word) {
        List<String> newWords = getSavedWords();
        newWords.remove(word);
        try {
            FileWriter fileWriter = new FileWriter("Bookmark.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String w : newWords) {
                bufferedWriter.write(w);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    @FXML
    void speakEnButton(MouseEvent event) {
        if (!currentSelectedWord.isEmpty()) {
            speakInEn(currentSelectedWord);
        }
    }

    @FXML
    void deleteTheWord(MouseEvent event) {
        String selectedWord = list.getSelectionModel().getSelectedItem();

        if (selectedWord == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete the selected word?");
        Optional<ButtonType> result =alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            list.getItems().remove(selectedWord);
            delete(selectedWord);
        }
    }

    @FXML
    void enterTheWord(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String searchedWord = list.getSelectionModel().getSelectedItem();
            currentSelectedWord = searchedWord;
            search();
            target.setText(searchedWord);
        }
    }

    @FXML
    void clickTheWord(MouseEvent event) {
        String searchedWord = list.getSelectionModel().getSelectedItem();
        currentSelectedWord = searchedWord;
        search();
        target.setText(searchedWord);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareList();
        Platform.runLater(() -> list.requestFocus());
    }
}