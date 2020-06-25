package View.graphic;

import Controller.Graphic.BuyerMenuController;
import Controller.Graphic.ManagerMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;

public class BuyerUserWindow extends Application {
    private static BuyerUserWindow instance = new BuyerUserWindow();

    public static BuyerUserWindow getInstance() {
        return instance;
    }

    private Stage stage;
    private Application parent;
    private Product product;

    public void setParent(Application parent, Product product) {
        this.parent = parent;
        this.product = product;
    }

    private BuyerUserWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("buyerUserMenu.fxml"));
            root = (Parent) loader.load();
            BuyerMenuController controller = (BuyerMenuController) loader.getController();
            controller.setParent(parent, product);
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
