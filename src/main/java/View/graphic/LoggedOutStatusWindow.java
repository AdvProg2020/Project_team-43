package View.graphic;

import View.GraphicController.LoggedOutStatusController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;

public class LoggedOutStatusWindow extends Application {
    private static LoggedOutStatusWindow instance = new LoggedOutStatusWindow();

    public static LoggedOutStatusWindow getInstance() {
        return instance;
    }

    private Application parent;
    private Product product;

    private LoggedOutStatusWindow() {

    }

    public void setParent(Application parent, Product product) {
        this.parent = parent;
        this.product = product;
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loggedOutStatus.fxml"));
            root = (Parent) loader.load();
            LoggedOutStatusController controller = (LoggedOutStatusController) loader.getController();
            controller.setParent(parent, product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
