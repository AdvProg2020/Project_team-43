package View.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerUserWindow extends Application {
    private static ManagerUserWindow instance = new ManagerUserWindow();

    public static ManagerUserWindow getInstance() {
        return instance;
    }

    private Stage stage;

    private ManagerUserWindow() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manager Menu");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
