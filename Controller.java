import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        // load studentDetail //
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


}
