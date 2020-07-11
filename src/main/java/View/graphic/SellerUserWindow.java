package View.graphic;

import View.GraphicController.SellerMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;

public class SellerUserWindow extends Application {
    private static SellerUserWindow instance = new SellerUserWindow();

    public static SellerUserWindow getInstance() {
        return instance;
    }

    private Stage stage;
    private Application parent;
    private Product product;

    public void setParent(Application parent, Product product) {
        this.parent = parent;
        this.product = product;
    }

    private SellerUserWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sellerUserMenu.fxml"));
            root = (Parent) loader.load();
            SellerMenuController controller = (SellerMenuController) loader.getController();
            controller.setParent(parent, product);
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
