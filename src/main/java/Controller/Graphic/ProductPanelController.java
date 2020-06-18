package Controller.Graphic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Category;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductPanelController extends Controller implements Initializable {

    @FXML
    private ListView<String> categoryListView;

    ObservableList<String> categories;

    public void sortByView() {

    }

    public void sortByDate() {

    }

    public void sortByScore() {

    }

    public void sortByPrice() {

    }

    public void filter() {

    }

    public void nextPage() {

    }

    public void previousPage() {

    }

    public void showCategories() {
        categoryListView.setVisible(true);
    }

    public void selectedCategory() {
        int index = categoryListView.getSelectionModel().getSelectedIndex();
        filterByCategory(categories.get(index));
        categoryListView.setVisible(false);
    }

    public void filterByCategory(String categoryName) {

    }


    public ProductPanelController() {
        categories = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryListView.setVisible(false);
        for (Category category : Category.getAllCategories()) {
            categories.add(category.getName());
        }
        categoryListView.setItems(categories);
    }


    public void hideCategories() {
        categoryListView.setVisible(false);
    }
}
