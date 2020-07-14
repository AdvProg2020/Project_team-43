package View.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankForWithdrawWindow extends Application {
    private static BankForWithdrawWindow instance = new BankForWithdrawWindow();

    public static BankForWithdrawWindow getInstance() {
        return instance;
    }

    private Stage stage;

    private BankForWithdrawWindow() {

    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("bankForWithdraw.fxml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
