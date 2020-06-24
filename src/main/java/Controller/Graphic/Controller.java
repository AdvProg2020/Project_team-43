package Controller.Graphic;

import View.graphic.MainWindow;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public abstract class Controller {
    protected Stage stage = MainWindow.getInstance().getStage();
    public void goBack() {
        Music.getInstance().backPage();
        MainWindow.getInstance().start(MainWindow.getInstance().getStage());
    }
}
