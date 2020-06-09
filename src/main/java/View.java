import View.console.App;
import View.graphic.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        MainWindow.getInstance().start(primaryStage);
    }

    public static void main(String[] args) {
        App.getInstance().open();
        launch(args);

    }
}
