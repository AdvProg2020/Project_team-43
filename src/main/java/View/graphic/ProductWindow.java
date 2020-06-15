package View.graphic;

import Controller.Graphic.ProductWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;

public class ProductWindow extends Application {
    private static final ProductWindow instance = new ProductWindow();

    public static ProductWindow getInstance() {
        return instance;
    }

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    private Stage stage;

    private ProductWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("productWindow.fxml"));
            root = (Parent) loader.load();
            ProductWindowController controller = (ProductWindowController) loader.getController();
            controller.setProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Product");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
