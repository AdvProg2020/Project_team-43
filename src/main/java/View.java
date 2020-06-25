import View.console.App;
import View.graphic.MainWindow;
import View.graphic.RegisterWindow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Manager;
import model.UserPersonalInfo;

public class View extends Application {
    @Override
    public void start(Stage primaryStage) {
        if (!Manager.hasManager()) {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("first manager");
            Label label1 = new Label("username: ");
            Label label2 = new Label("password: ");
            TextField text1 = new TextField();
            TextField text2 = new TextField();
            GridPane grid = new GridPane();
            grid.add(label1, 1, 1);
            grid.add(text1, 2, 1);
            grid.add(label2, 1, 2);
            grid.add(text2, 2, 2);
            textInputDialog.getDialogPane().setContent(grid);
            textInputDialog.setTitle("register");
            textInputDialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
            final Button btOk = (Button) textInputDialog.getDialogPane().lookupButton(ButtonType.OK);
            btOk.addEventFilter(
                    ActionEvent.ACTION,
                    event -> {
                        if (text1.getText().equals("") || text2.getText().equals("")) {
                            event.consume();
                        } else {
                            new Manager(text1.getText(), new UserPersonalInfo("", "", "", "", text2.getText()));
                        }
                    }
            );
            textInputDialog.showAndWait();
        }
        if (Manager.hasManager())
            MainWindow.getInstance().start(primaryStage);
        else
            System.exit(0);
    }

    public static void main(String[] args) {
        App.getInstance().open();
        launch(args);

    }
}
