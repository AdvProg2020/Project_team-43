package View.graphic;

import Controller.Graphic.LoggedOutStatusController;
import Controller.Graphic.ManagerMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;

public class ManagerUserWindow extends Application {
    private static ManagerUserWindow instance = new ManagerUserWindow();

    public static ManagerUserWindow getInstance() {
        return instance;
    }

    private Stage stage;
    private Application parent;
    private Product product;

    public void setParent(Application parent, Product product) {
        this.parent = parent;
        this.product = product;
    }

    private ManagerUserWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("managerUserMenu.fxml"));
            root = (Parent) loader.load();
            ManagerMenuController controller = (ManagerMenuController) loader.getController();
            controller.setParent(parent, product);
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
