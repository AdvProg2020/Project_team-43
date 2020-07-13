
import View.GraphicController.Controller;
import View.console.App;
import View.graphic.MainWindow;
import javafx.application.Application;

import javafx.stage.Stage;

import View.Client;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) {
        Client client = new Client();
        client.run();
        Controller.setClient(client);
        MainWindow.getInstance().start(primaryStage);
    }

    public static void main(String[] args) {
        //App.getInstance().open();
        launch(args);
    }

}
