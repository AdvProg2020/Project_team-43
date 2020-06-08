package View.graphic;

import View.console.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {
    private static MainWindow instance = new MainWindow();

    public static MainWindow getInstance() {
        return instance;
    }

    private MainWindow() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bamazon");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
