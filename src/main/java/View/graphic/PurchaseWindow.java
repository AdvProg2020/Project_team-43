package View.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PurchaseWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("purchase.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Products");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
