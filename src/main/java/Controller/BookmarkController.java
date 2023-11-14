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

public class BookmarkController implements Initializable {
    private String currentSelectedWord = "";
    @FXML
    private Label Label1;

    @FXML
    private Label Label2;

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
        try (BufferedReader reader = new BufferedReader(new FileReader("Bookmark.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("Bookmark_temp.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(word)) {
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        File tempFile = new File("Bookmark_temp.txt");
        File originalFile = new File("Bookmark.txt");

        if (tempFile.renameTo(originalFile)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setContentText(word + " has been successfully deleted from the bookmark");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to delete " + word);
            alert.show();
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
        }
    }

    @FXML
    void clickTheWord(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            String searchedWord = list.getSelectionModel().getSelectedItem();
            currentSelectedWord = searchedWord;
            search();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareList();
        Platform.runLater(() -> list.requestFocus());
    }
}