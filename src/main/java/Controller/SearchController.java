package Controller;

import Dictionary.DatabaseDictionary;
import Dictionary.Dictionary;
import Dictionary.Trie;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static Dictionary.Speech.speakInEn;


public class SearchController implements Initializable{
    private Dictionary dictionary;
    ObservableList<String> autoCom = FXCollections.observableArrayList();
    private String currentSearchWord = "";
    private String definition;

    @FXML
    private TextArea definitionSite;
    @FXML
    private ListView<String> list;
    @FXML
    private TextField searchBar;
    @FXML
    private Button speakerUSButton, speakerVNButton, editButton, searchBtn, exitBtn;
    @FXML
    private ImageView saveBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseDictionary.initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(() -> searchBar.requestFocus());
        searchBar.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (searchBar.getText().isEmpty()) {
                    autoCom.clear();
                } else {
                    autoCom.clear();
                    String searchWord = searchBar.getText().trim();
                    autoCom = autoComplete(searchWord);
                    if (autoCom.isEmpty()) {
                        list.setDisable(true);
                    } else {
                        list.setDisable(false);
                        list.setItems(autoCom);
                    }
                }
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

        EditController.setEditingWord(currentSearchWord);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Edit.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage newStage = new Stage();

            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.initOwner(stage);
            newStage.setTitle("EditWordDefinition");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exitButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void favouriteButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Bookmark" +
                    ".fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage newStage = new Stage();

            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.initOwner(stage);
            newStage.setResizable(false);
            newStage.setTitle("Add new word");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ggTranslateButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/TranslateUI.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setTitle("Translator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void quizButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/game/fxml/HomeGameUI.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setTitle("Let's play a game!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void searchWord() {
        String searchWord = searchBar.getText();
        currentSearchWord = searchWord;
        definition = DatabaseDictionary.lookUpWord(currentSearchWord);
        if (definition.equals("No words were found")) {
            definitionSite.setText("No word can be found");
            definitionSite.setVisible(true);
        } else {
            definitionSite.setText(definition);
            definitionSite.setVisible(true);
        }
    }

    /**
     * Functions of the search button.
     */
    @FXML
    void searchButton(MouseEvent event) {
        searchWord();
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
        if (event.getClickCount() == 1) {
            String searchedWord = list.getSelectionModel().getSelectedItem();
            searchBar.setText(searchedWord);
        }
        if (event.getClickCount() >= 2) {
            String searchedWord = list.getSelectionModel().getSelectedItem();
            searchBar.setText(searchedWord);
            searchWord();
        }
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
                        Trie.deleteWord(currentSearchWord);
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

    @FXML
    void addWordButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AddWordUI.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage newStage = new Stage();

            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.initOwner(stage);
            newStage.setResizable(false);
            newStage.setTitle("Add new word");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isWordInBookmarkFile(String word) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Bookmark.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(word)) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveButton(MouseEvent event) {
        if (currentSearchWord.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("No word has been selected");
            alert.show();
            return;
        }
        if (!isWordInBookmarkFile(currentSearchWord)) {
            exportToBookmarkFile(currentSearchWord);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("The word has been already saved");
            alert.show();
        }
    }
}

