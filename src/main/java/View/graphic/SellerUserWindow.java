package View.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerUserWindow extends Application {
    private static SellerUserWindow instance = new SellerUserWindow();

    public static SellerUserWindow getInstance() {
        return instance;
    }

    private Stage stage;

    private SellerUserWindow() {

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
        primaryStage.setTitle("Seller Menu");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
