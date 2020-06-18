package Controller.Graphic;

import Controller.console.BuyerProcessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Category;
import model.Product;
import org.controlsfx.control.RangeSlider;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductPanelController extends Controller implements Initializable {
    private final BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    public TextField nameFilterText;
    public RangeSlider priceFilterSlider;
    public Label minValue;
    public Label maxValue;
    private ArrayList<Product> allProducts = Product.getAllProductsInList();
    public CheckBox availableFilterCheckBox;
    @FXML
    private ListView<String> categoryListView;

    ObservableList<String> categories;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMaxAndMinPrice();
        priceFilterSlider.highValueProperty().addListener((observable) -> {
            maxValue.setText(Double.toString((int) priceFilterSlider.getHighValue()));

        });
        priceFilterSlider.lowValueProperty().addListener((observable -> {
            minValue.setText(Double.toString((int) priceFilterSlider.getLowValue()));
        }));
        BuyerProcessor.getInstance().newProductFilter();
        categoryListView.setVisible(false);
        for (Category category : Category.getAllCategories()) {
            categories.add(category.getName());
        }
        categoryListView.setItems(categories);
    }

    public void setMaxAndMinPrice() {
        double maxPrice = 0;
        for (Product product : allProducts) {
            if (product.getPrice() > maxPrice)
                maxPrice = product.getPrice();
        }
        priceFilterSlider.setMax(maxPrice);
        priceFilterSlider.setHighValue(maxPrice);
        minValue.setText("0");
        maxValue.setText(Double.toString(maxPrice));

    }

    public void sortByView() {

    }

    public void sortByDate() {

    }

    public void sortByScore() {

    }

    public void sortByPrice() {

    }

    public void filter() {
        if (!nameFilterText.getText().equals("")) {
            buyerProcessor.filter("by name " + nameFilterText.getText());

        } else {
            buyerProcessor.disableFilter("name");
        }
        buyerProcessor.filter("by price from " + minValue.getText() + " to " + maxValue.getText());

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


    public void hideCategories() {
        categoryListView.setVisible(false);
    }

    public void filterAvailable() {
        if (availableFilterCheckBox.isSelected()) {
            buyerProcessor.filter("by availability");
        } else {
            buyerProcessor.disableFilter("availability");
        }
    }
}
