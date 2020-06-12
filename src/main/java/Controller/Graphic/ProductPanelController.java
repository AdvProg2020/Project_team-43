package Controller.Graphic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Category;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductPanelController implements Initializable {


    public void sortByView(){

    }

    public void sortByDate(){

    }

    public void sortByScore(){

    }

    public void sortByPrice(){

    }

    public void filter(){

    }

    public void showCategories(){
        categoryListView.setVisible(true);
    }

    public void selectedCategory(){
        int index = categoryListView.getSelectionModel().getSelectedIndex();
        filterByCategory(categories.get(index));
        categoryListView.setVisible(false);
    }

    public void filterByCategory(String categoryName){

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
