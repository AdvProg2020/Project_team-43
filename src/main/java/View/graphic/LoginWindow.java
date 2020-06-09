package View.graphic;

import Controller.Graphic.Controller;
import Controller.Graphic.MainMenuController;
import Controller.console.BuyerProcessor;
import View.console.MainMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindow extends Application {
    private static LoginWindow instance = new LoginWindow();
    private Stage stage;

    public static LoginWindow getInstance() {
        return instance;
    }

    private LoginWindow() {
    }

    @Override
    public void start(Stage primaryStage) {
        stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("loginWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.show();
    }

}
