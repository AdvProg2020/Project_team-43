import View.console.App;
import View.graphic.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainWindow.getInstance().start(primaryStage);
    }

    public static void main(String[] args) {
        App.getInstance().open();
        launch(args);

    }
}
