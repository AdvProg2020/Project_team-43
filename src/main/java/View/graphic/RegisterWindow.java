package View.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterWindow extends Application {
    private Stage stage;
    private static final RegisterWindow instance = new RegisterWindow();

    public static RegisterWindow getInstance() {
        return instance;
    }

    private RegisterWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("RegisterWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Register");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.show();

    }
}
