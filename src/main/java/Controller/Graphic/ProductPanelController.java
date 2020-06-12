package Controller.Graphic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import model.Category;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductPanelController implements Initializable {

    @FXML
    ImageView image1;
    @FXML
    ImageView image2;
    @FXML
    ImageView image3;
    @FXML
    ImageView image4;
    @FXML
    ImageView image5;
    @FXML
    ImageView image6;
    @FXML
    ImageView image7;
    @FXML
    ImageView image8;
    @FXML
    ImageView image9;
    @FXML
    Label name1;
    @FXML
    Label name2;
    @FXML
    Label name3;
    @FXML
    Label name4;
    @FXML
    Label name5;
    @FXML
    Label name6;
    @FXML
    Label name7;
    @FXML
    Label name8;
    @FXML
    Label name9;
    @FXML
    Label price1;
    @FXML
    Label price2;
    @FXML
    Label price3;
    @FXML
    Label price4;
    @FXML
    Label price5;
    @FXML
    Label price6;
    @FXML
    Label price7;
    @FXML
    Label price8;
    @FXML
    Label price9;


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

    @FXML
    private ListView<String> categoryListView;

    ObservableList<String> categories;

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
}
