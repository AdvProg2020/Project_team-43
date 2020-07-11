package View.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.database.Database;

import java.io.*;

public class MainWindow extends Application {
    private static MainWindow instance = new MainWindow();

    public static MainWindow getInstance() {
        return instance;
    }

    private Stage stage;

    private MainWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        stage.getIcons().add(new Image("file:src/main/resources/fuck.jpeg"));

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bamazon");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public Stage getStage() {
        return stage;
    }
}
