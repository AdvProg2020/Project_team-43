package Controller.Graphic;

import Controller.console.BuyerProcessor;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Category;
import model.Product;
import model.Sorting;
import org.controlsfx.control.RangeSlider;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class ProductPanelController extends Controller implements Initializable {
    private final BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    public TextField nameFilterText;
    public RangeSlider priceFilterSlider;
    public Label minValue;
    public Label maxValue;
    public ToggleGroup toggleGroup1;
    public JFXRadioButton viewRadioButton;
    public JFXRadioButton mostExpensiveRadioButton;
    public JFXRadioButton leastExpensiveRadioButton;
    public JFXRadioButton dateAddedRadioButton;
    public JFXRadioButton scoreRadioButton;
    public CheckBox offFilterCheckBox1;
    public TextArea categoryName;
    public Text userName;


    private ArrayList<Product> allProducts = Product.getAllProductsInList();
    public CheckBox availableFilterCheckBox;
    @FXML
    private ListView<String> categoryListView;

    ObservableList<String> categories;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (buyerProcessor.isUserLoggedIn())
            userName.setText(buyerProcessor.getUser().getUsername());
        viewRadioButton.setSelected(true);
        buyerProcessor.sort("by view");
        toggleGroup1.selectedToggleProperty().addListener((observable -> {
            sort(toggleGroup1.getSelectedToggle());
        }));
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

    public void sort(Toggle selectedToggle) {
        String sort = ((RadioButton) selectedToggle).getText();
        if (sort.equals("most expensive")) {
            buyerProcessor.sort("by price");
        } else if (sort.equals("least expensive")) {
            buyerProcessor.sort("by price -d");
        } else if (sort.equals("date added")) {
            buyerProcessor.sort("by date");
        } else if (sort.equals("view")) {
            buyerProcessor.sort("by view");
        } else if (sort.equals("score")) {
            buyerProcessor.sort("by score");
        }
        getProductsAfterSort();
    }

    public void getProductsAfterSort() {
        allProducts.sort(Sorting.getComparator());
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


    public void filter() {
        if (!nameFilterText.getText().equals("")) {
            buyerProcessor.filter("by name " + nameFilterText.getText());

        } else {
            buyerProcessor.disableFilter("name");
        }
        buyerProcessor.filter("by price from " + minValue.getText() + " to " + maxValue.getText());
        allProducts = buyerProcessor.getProductAfterFilter(allProducts);

    }

    public void nextPage() {

    }

    public void previousPage() {

    }

    public void showCategories() {
        boolean visible = categoryListView.isVisible();
        categoryListView.setVisible(!visible);
    }

    public void selectedCategory() {
        int index = categoryListView.getSelectionModel().getSelectedIndex();
        filterByCategory(categories.get(index));
        categoryListView.setVisible(false);
    }

    public void filterByCategory(String categoryName) {
        buyerProcessor.filteringProcess("select category " + categoryName);
        this.categoryName.setText(categoryName);
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

    public void filterOff() {
        if (offFilterCheckBox1.isSelected()) {
            buyerProcessor.filter("off");
        } else {
            buyerProcessor.disableFilter("off");
        }
    }
}
