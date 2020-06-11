package Controller.Graphic;

import View.graphic.MainWindow;
import javafx.event.ActionEvent;

public abstract class Controller {

    public void goBack(ActionEvent actionEvent) {
        MainWindow.getInstance().start(MainWindow.getInstance().getStage());
    }
}
