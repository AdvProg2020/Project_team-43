package View.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductPanelWindow extends Application {
    private static ProductPanelWindow instance = new ProductPanelWindow();

    public static ProductPanelWindow getInstance() {
        return instance;
    }

    private Stage stage;

    private ProductPanelWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("productsPanel.fxml"));
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
