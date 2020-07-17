package View.graphic;

import View.GraphicController.SellerMenuController;
import View.GraphicController.SupporterMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;

public class SupporterUserWindow extends Application{
    private static SupporterUserWindow instance = new SupporterUserWindow();

    public static SupporterUserWindow getInstance() {
        return instance;
    }

    private Stage stage;
    private Application parent;
    private Product product;

    public void setParent(Application parent, Product product) {
        this.parent = parent;
        this.product = product;
    }
    private SupporterUserWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("SupporterUserMenu.fxml"));
            root = (Parent) loader.load();
            SupporterMenuController controller = (SupporterMenuController) loader.getController();
            controller.setParent(parent, product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Supporter Menu");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
