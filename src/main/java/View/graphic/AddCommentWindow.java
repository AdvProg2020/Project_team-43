package View.graphic;

import Controller.Graphic.AddCommentController;
import Controller.Graphic.ProductWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;

public class AddCommentWindow extends Application {
    private static AddCommentWindow instance = new AddCommentWindow();
    private Stage stage;
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public static AddCommentWindow getInstance() {
        return instance;
    }

    private AddCommentWindow() {
    }

    @Override
    public void start(Stage primaryStage) {
        stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addComment.fxml"));
            root = (Parent) loader.load();
            AddCommentController controller = (AddCommentController) loader.getController();
            controller.setProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Comment");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.show();
    }
}
