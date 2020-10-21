import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;

public class DictionaryApplication extends Application {
    public static Synthesizer synthesizer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(windowEvent -> {
                try {
                    synthesizer.deallocate();
                } catch (EngineException e) {
                    e.printStackTrace();
                }
            });
            primaryStage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
