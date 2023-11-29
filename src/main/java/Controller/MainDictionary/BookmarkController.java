package Controller.MainDictionary;

import Dictionary.DatabaseDictionary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
    private ScrollPane definitionSite;

    private void prepareList() {
        List<String> bookmarkedWords = getMarkedWords();
        list.getItems().setAll(bookmarkedWords);
    }

    private List<String> getMarkedWords() {
        List<String> markedWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Bookmark.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                markedWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return markedWords;
    }

    private void search() {
        String definition = DatabaseDictionary.lookUpWord(currentSelectedWord);
        if (definition.equals("No words were found")) {
            definitionSite.setContent(null);
        } else {
            show(definition);
        }
    }

    private void delete(String word) {
        List<String> newWords = getMarkedWords();
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
        alert.setTitle("Confirm");
        alert.setHeaderText("Are you sure you want to delete the selected bookmark?");
        Optional<ButtonType> result =alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            list.getItems().remove(selectedWord);
            delete(selectedWord);
            target.setText("Target");
            definitionSite.setContent(null);
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
        if (searchedWord != null) {
            currentSelectedWord = searchedWord;
            search();
            target.setText(searchedWord);
        }
    }

    @FXML
    void deleteAllMarkedWords() {
        if(!list.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to delete all bookmarks?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    FileWriter fileWriter = new FileWriter("Bookmark.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.close();
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e);
                }
                list.getItems().clear();
                target.setText("Target");
                definitionSite.setContent(null);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Empty list");
            alert.showAndWait();
        }
    }

    public void show(String wordExplain) {
        VBox vbox = new VBox(5);
        String[] s = wordExplain.split("\n", -1);
        Font font1 = Font.font("Arial", FontWeight.BOLD, 18);
        Font font2 = Font.font("Arial", FontWeight.SEMI_BOLD,16);
        Font font3 = Font.font("Arial", 14);
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].trim();
            if (s[i].startsWith("@")) {
                String tmp = s[i];
                Text text = new Text(tmp);
                text.setFont(font1);
                vbox.getChildren().add(text);
            } else if (s[i].startsWith("*")) {
                String tmp = "  " + s[i] ;
                Text text = new Text(tmp);
                text.setFont(font2);
                vbox.getChildren().add(text);
            } else {
                String tmp = "\t" + s[i];
                Text text = new Text(tmp);
                text.setFont(font3);
                vbox.getChildren().add(text);
            }
        }
        definitionSite.setContent(vbox);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareList();
    }
}