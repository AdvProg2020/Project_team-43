package View.GraphicController;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.FileProduct;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.ArrayList;

public class FilesPanelController extends Controller {
    public ArrayList<Pane> panes;
    public CustomTextField pageNumber;
    public ArrayList<FileProduct> allFiles;
    private int startProductIndex = 0;

    public void initialize() {
        //TODO get files from server
    }

    public boolean hasNextPage() {
        return startProductIndex + 9 < allFiles.size();
    }

    public void nextPage() {
        Music.getInstance().open();
        if (hasNextPage()) {
            pageNumber.setText((Integer.parseInt(pageNumber.getText()) + 1) + "");
            startProductIndex += 9;
            showFiles();
        }
    }

    public boolean hasPreviousPage() {
        return startProductIndex != 0;
    }

    public void previousPage() {
        Music.getInstance().open();
        if (hasPreviousPage()) {
            pageNumber.setText((Integer.parseInt(pageNumber.getText()) - 1) + "");
            startProductIndex -= 9;
            showFiles();
        }
    }

    public void openFilePanel() {
    }

    public void showFiles() {
        for (int i = startProductIndex; i < startProductIndex + 9; i++) {
            Pane pane = panes.get(i - startProductIndex);
            if (allFiles.size() > i) {
                panes.get(i - startProductIndex).setVisible(true);
                ((Label) pane.getChildren().get(1)).setText(allFiles.get(i).getFileName());
            } else {
                pane.setVisible(false);
            }
        }

    }
}
