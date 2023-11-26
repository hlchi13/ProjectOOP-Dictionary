package Controller;

import Dictionary.DatabaseDictionary;
import Dictionary.Trie;
import Game.Entities.Cat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static Dictionary.Speech.speakInEn;


public class SearchController implements Initializable{
    ObservableList<String> autoCom = FXCollections.observableArrayList();
    private String currentSearchWord = "";
    private String definition;

    @FXML
    private ScrollPane definitionSite;
    @FXML
    private TextArea editSite;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField searchBar;
    @FXML
    private Button clearBtn, saveEditBtn, cancelEditBtn, toMarkBtn, isMarkedBtn;
    @FXML
    private Label selectedWord, notFound;

    public void refreshSearcher() {
        selectedWord.setText("Target");
        searchBar.setText("Nhập từ");
        notFound.setVisible(false);
        clearBtn.setVisible(false);
        toMarkBtn.setVisible(true);
        isMarkedBtn.setVisible(false);
        definitionSite.setContent(null);
        list.setDisable(true);
        currentSearchWord = "";
    }
    @FXML
    public void clear(MouseEvent event) {
        refreshSearcher();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseDictionary.initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (isWordInBookmarkFile(currentSearchWord)) {
            isMarkedBtn.setVisible(true);
            toMarkBtn.setVisible(false);
        } else {
            isMarkedBtn.setVisible(false);
            toMarkBtn.setVisible(true);
        }

        searchBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (searchBar.getText().equals("Nhập từ"))
                    searchBar.setText("");
            }
        });

        searchBar.textProperty().addListener((observable, oldValue, newValue)-> {
            clearBtn.setVisible(true);
            autoCom.clear();
            String searchWord = newValue;
            autoCom = autoComplete(searchWord);
            if (autoCom.isEmpty()) {
                list.setDisable(true);
            } else {
                list.setDisable(false);
                list.setItems(autoCom);
            }
        });
    }

    public ObservableList<String> autoComplete (String word) {
        ObservableList<String> obL = FXCollections.observableArrayList();
        try {
            ArrayList<String> results = Trie.advancedSearch(word);
            if (results != null) {
                int length = Math.min((results.size()), 30);
                for (int i = 0; i < length; i++) {
                    obL.add(results.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("AutoCom gone wrong: " + e);
        }
        return obL;
    }

    @FXML
    void editButton(MouseEvent event) {
        // case1.
        if (currentSearchWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText("No word has been selected to edit");
            alert.show();
            return;
        }

        // case2.
        if (DatabaseDictionary.lookUpWord(currentSearchWord).equals("No words were found")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setContentText(currentSearchWord + " doesn't exist in the dictionary");
            alert.show();
            return;
        }
        definitionSite.setVisible(false);
        editSite.setText(definition);
        editSite.setVisible(true);
        saveEditBtn.setVisible(true);
        cancelEditBtn.setVisible(true);
    }

    @FXML
    void saveTheDefinition(MouseEvent event) {
        String newDefinition = editSite.getText();
        if (DatabaseDictionary.updateWordDefinition(currentSearchWord, newDefinition)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setContentText(currentSearchWord + " is successfully updated");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setContentText(currentSearchWord + " is failed to update");
            alert.show();
        }
        show(editSite.getText());
        definitionSite.setVisible(true);
        editSite.setVisible(false);
        saveEditBtn.setVisible(false);
        cancelEditBtn.setVisible(false);
    }

    @FXML
    void cancelTheDefinition() {

    }

    public void searchWord() {
        String searchWord = searchBar.getText();
        currentSearchWord = searchWord;
        definition = DatabaseDictionary.lookUpWord(currentSearchWord);
        if (isWordInBookmarkFile(currentSearchWord)) {
            isMarkedBtn.setVisible(true);
            toMarkBtn.setVisible(false);
        } else {
            isMarkedBtn.setVisible(false);
            toMarkBtn.setVisible(true);
        }
        if (definition.equals("No words were found")) {
            definitionSite.setContent(new Label("No word can be found"));
            definitionSite.setVisible(true);
            notFound.setVisible(true);
        } else {
            show(definition);
            definitionSite.setVisible(true);
            notFound.setVisible(false);
        }
    }


    @FXML
    void searchBarKey(KeyEvent event) {
        // press Enter to search the interested word.
        if (event.getCode() == KeyCode.ENTER) {
            searchWord();
        }
        // change to the suggestion list.
        if (event.getCode() == KeyCode.DOWN) {
            list.requestFocus();
            if (!list.getItems().isEmpty()) {
                list.getSelectionModel().select(0);
            }
        }
    }

    /**
     * Functions of the list.
     */
    @FXML
    void listMouseClicked(MouseEvent event) {
        String searchedWord = list.getSelectionModel().getSelectedItem();
        searchBar.setText(searchedWord);
        searchWord();
        selectedWord.setText(searchedWord);
    }

    @FXML
    void listKeyPressed(KeyEvent event) {
        if (list.getSelectionModel().getSelectedIndices().isEmpty()) {
            return;
        }
        if (event.getCode() == KeyCode.ENTER) {
            String searchedWord = list.getSelectionModel().getSelectedItem();
            searchBar.setText(searchedWord);
            searchWord();
            selectedWord.setText(searchedWord);
        } else if (event.getCode() == KeyCode.UP && list.getSelectionModel().getSelectedIndex() == 0) {
            searchBar.requestFocus();
        }
    }
    @FXML
    void settingButton(MouseEvent event) {

    }

    @FXML
    void speakENButton(MouseEvent event) {
        if (!currentSearchWord.isEmpty()) {
            speakInEn(currentSearchWord);
        }
    }

    @FXML
    void deleteButton(MouseEvent event) {
        if (currentSearchWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No word has been selected");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure to delete "
            + currentSearchWord
            + " from the dtb ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent()) {
                if (option.get() == ButtonType.OK) {
                    if (DatabaseDictionary.deleteWord(currentSearchWord)) {
                        refreshSearcher();
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Info");
                        alert1.setContentText(currentSearchWord + "deleted successfully");
                        alert1.show();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setContentText(currentSearchWord + "doesnt exist in the dtb");
                        alert2.show();
                    }
                }
            }
            currentSearchWord = "";
        }
    }

    private boolean isWordInBookmarkFile(String word) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Bookmark.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void exportToBookmarkFile(String word) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Bookmark.txt", true))) {
            writer.write(word);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void markAWord(MouseEvent event) {
        if (currentSearchWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No word has been selected");
            alert.show();
            return;
        }
        if (!isWordInBookmarkFile(currentSearchWord)) {
            toMarkBtn.setVisible(false);
            isMarkedBtn.setVisible(true);
            exportToBookmarkFile(currentSearchWord);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("The word has been already saved");
            alert.show();
        }
    }

    @FXML
    void unmarkAWord(MouseEvent event) {

    }

    public void show(String wordExplain) {
        VBox vbox = new VBox(5);
        String[] s = wordExplain.split("\n", -1);
        definition = "";
        Font font1 = Font.font("Arial", FontWeight.MEDIUM, 16);
        Font font2 = Font.font("Arial", FontWeight.BOLD,16);
        Font font3 = Font.font("Arial", 14);
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].trim();
            if (s[i].startsWith("@")) {
                definition = definition + s[i] + "\n";
                String tmp = s[i];
                Text text = new Text(tmp);
                text.setFont(font1);
                vbox.getChildren().add(text);
            } else if (s[i].startsWith("*")) {
                definition = definition + "  " + s[i] + "\n";
                String tmp = "  " + s[i] ;
                Text text = new Text(tmp);
                text.setFont(font2);
                vbox.getChildren().add(text);
            } else if (s[i].startsWith("-")) {
                definition = definition + "     " + s[i] + "\n";
                String tmp = "     " + s[i];
                Text text = new Text(tmp);
                text.setFont(font3);
                vbox.getChildren().add(text);
            } else {
                definition = definition + " " + s[i] + "\n";
                String tmp = " " + s[i];
                Text text = new Text(tmp);
                text.setFont(font3);
                vbox.getChildren().add(text);
            }
        }
        definitionSite.setContent(vbox);
        notFound.setVisible(false);
    }
}

