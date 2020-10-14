import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;


public class DetailController {


    @FXML
    Label wordLabel;

    @FXML
    Label explainLabel;

    @FXML
    Label pronounceLabel;

    @FXML
    Label typeLabel;


    public void setWord(Word word) {
        wordLabel.setText(word.getWord_target());
        explainLabel.setText(word.getWord_explain());
        pronounceLabel.setText(word.getWord_pronunciation());
        typeLabel.setText(word.getWord_type());

    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent sampleParent = loader.load();
        Scene scene = new Scene(sampleParent);
        stage.setScene(scene);
    }
}
