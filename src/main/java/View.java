import View.App;
import View.MainMenu;
import View.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        Menu.setStage(primaryStage);
        MainMenu mainMenu = new MainMenu("main menu");
        mainMenu.show();
    }

    public static void main(String[] args) {
        App.getInstance().open();
        launch(args);

    }
}
