package View.graphic;

import View.GraphicController.ProductWindowController;
import View.GraphicController.PurchaseWithBankController;
import View.console.Purchase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PurchaseWithBankWindow extends Application {
    private static PurchaseWithBankWindow instance = new PurchaseWithBankWindow();

    public static PurchaseWithBankWindow getInstance() {
        return instance;
    }

    private Stage stage;
    private String address;
    private String phoneNumber;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCodedDiscount(String codedDiscount) {
        this.codedDiscount = codedDiscount;
    }

    private String codedDiscount;

    private PurchaseWithBankWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("purchaseWithBank.fxml"));
            root = (Parent) loader.load();
            PurchaseWithBankController controller = (PurchaseWithBankController) loader.getController();
            controller.setAddress(address);
            controller.setPhoneNumber(phoneNumber);
            controller.setCodedDiscount(codedDiscount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
