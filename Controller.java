import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    private Dictionary dictionary = new Dictionary();

    @FXML
    private TableView<Word> table;

    @FXML
    private TableColumn<Word, String> word_targetColumn;

    private ObservableList<Word> WordList;

    @FXML
    private TextField Search;

    @FXML
    private TextField NewWordTarget;

    @FXML
    private TextField NewWordExplain;

    @FXML
    private ComboBox<String> NewType;

    @FXML
    private TextField NewPronounce;

    @FXML
    private TextField changeWordTarget;

    @FXML
    private TextField changeWordExplain;

    @FXML
    private ComboBox<String> changeType;

    @FXML
    private TextField changePronounce;

    @FXML
    private TextField remove;

    @FXML
    private TextField wordtofind;

    @FXML
    private TextField sentence;

    @FXML
    private TextArea translated_sentence;

    ObservableList<String> list = FXCollections.observableArrayList("danh từ", "động từ", "tính từ", "trạng từ");

    ObservableList<String> changelist = FXCollections.observableArrayList("danh từ", "động từ", "tính từ", "trạng từ");



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.setProperty(
                "freetts.voices",
                "com.sun.speech.freetts.en.us"
                        + ".cmu_us_kal.KevinVoiceDirectory");

        // Register Engine
        try {
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");
        } catch (EngineException e) {
            e.printStackTrace();
        }

        // Create a Synthesizer
        try {
            DictionaryApplication.synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));
        } catch (EngineException e) {
            e.printStackTrace();
        }

        // Allocate synthesizer
        try {
            DictionaryApplication.synthesizer.allocate();
        } catch (EngineException e) {
            e.printStackTrace();
        }

        // Resume Synthesizer
        try {
            DictionaryApplication.synthesizer.resume();
        } catch (AudioException e) {
            e.printStackTrace();
        }

        changeType.setItems(changelist);
        NewType.setItems(list);
        table.setPlaceholder(new Label("Can't find any words."));
        DictionaryManagement.insertFromDatabase(dictionary);
        Search.textProperty().addListener((observableValue, oldValue, newValue) -> {
            WordList.remove(0, WordList.size());
            ArrayList<String> list = dictionary.print_word_again(newValue);
            if(list != null) {
                for (int i = 0; i < list.size(); i++) {
                    WordList.add(dictionary.getLink(list.get(i)));
                }
            }
        });
        WordList = FXCollections.observableArrayList();
        word_targetColumn.setCellValueFactory(new PropertyValueFactory<Word, String>("word_target"));
        table.setItems(WordList);
    }

    public void changeScene(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Detailsample.fxml"));
        Parent wordViewParent = loader.load();
        Scene scene = new Scene(wordViewParent);
        DetailController controller = loader.getController();
        Word selected = table.getSelectionModel().getSelectedItem();
        controller.setWord(selected);
        stage.setScene(scene);
    }

    public void removeWord(ActionEvent actionEvent) throws SQLException {
        DictionaryManagement.removeFromDatabase(remove.getText());
        DictionaryManagement.removeWord(remove.getText(),dictionary);
    }

    public void updateWord(ActionEvent actionEvent) throws SQLException {
        if(!wordtofind.getText().trim().equals("")) {
            if(!changeWordTarget.getText().trim().equals("")) {
                DictionaryManagement.Update_word_FromDatabase(wordtofind.getText(), changeWordTarget.getText());
                DictionaryManagement.change_Word_target(wordtofind.getText(),changeWordTarget.getText(), dictionary);
                if (!changeWordExplain.getText().trim().equals("")) {
                    DictionaryManagement.Update_description_FromDatabase(changeWordTarget.getText(), changeWordExplain.getText());
                    DictionaryManagement.change_Word_explain(changeWordTarget.getText(), changeWordExplain.getText(), dictionary);
                }
                if (!changePronounce.getText().trim().equals("")) {
                    DictionaryManagement.Update_pronounce_FromDatabase(changeWordTarget.getText(), changePronounce.getText());
                    DictionaryManagement.change_Word_pronounce(changeWordTarget.getText(), changePronounce.getText(), dictionary);
                }
                if (!(changeType.getValue() == null)) {
                    DictionaryManagement.Update_type_FromDatabase(changeWordTarget.getText(), changeType.getValue());
                    DictionaryManagement.change_Word_type(changeWordTarget.getText(), changeType.getValue(), dictionary);
                }
            } else {
                if (!changeWordExplain.getText().trim().equals("")) {
                    DictionaryManagement.Update_description_FromDatabase(wordtofind.getText(), changeWordExplain.getText());
                    DictionaryManagement.change_Word_explain(wordtofind.getText(), changeWordExplain.getText(), dictionary);
                }
                if (!changePronounce.getText().trim().equals("")) {
                    DictionaryManagement.Update_pronounce_FromDatabase(wordtofind.getText(), changePronounce.getText());
                    DictionaryManagement.change_Word_pronounce(wordtofind.getText(), changePronounce.getText(), dictionary);
                }
                if (!(changeType.getValue() == null)) {
                    DictionaryManagement.Update_type_FromDatabase(wordtofind.getText(), changeType.getValue());
                    DictionaryManagement.change_Word_type(wordtofind.getText(), changeType.getValue(), dictionary);
                }
            }
        }
    }

    public void addWord(ActionEvent actionEvent) throws SQLException {
        DictionaryManagement.Add_to_Database(NewWordTarget.getText(), NewWordExplain.getText(), NewPronounce.getText(), NewType.getValue());
        DictionaryManagement.addWord(NewWordTarget.getText(), NewWordExplain.getText(), NewType.getValue(), NewPronounce.getText(), dictionary);
    }

    public void Translated_sentence(ActionEvent actionEvent) {
        try {
            APITranslate translateRequest = new APITranslate();
            String response = translateRequest.Post(sentence.getText());
            String result = response.substring(27, response.length() - 15);
            translated_sentence.setText(result);
        } catch (Exception e) {
            translated_sentence.setText(e.toString());
        }
    }
}
