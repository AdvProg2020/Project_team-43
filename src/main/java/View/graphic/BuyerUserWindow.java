package View.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BuyerUserWindow extends Application {
    private static BuyerUserWindow instance = new BuyerUserWindow();

    public static BuyerUserWindow getInstance() {
        return instance;
    }

    private Stage stage;

    private BuyerUserWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("buyerUserMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Buyer Menu");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}
