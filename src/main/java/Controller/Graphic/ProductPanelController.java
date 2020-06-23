package Controller.Graphic;

import Controller.console.BuyerProcessor;
import Controller.console.Processor;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Category;
import model.Product;
import model.Sorting;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.RangeSlider;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;

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
    public ListView<HBox> featuresOfCategoryForFilter;
    public ImageView image1;
    public ImageView image2;
    public ImageView image3;
    public ImageView image4;
    public ImageView image5;
    public ImageView image6;
    public ImageView image7;
    public ImageView image8;
    public ImageView image9;
    public Pane productCartPane1;
    public Pane productCartPane2;
    public Pane productCartPane3;
    public Pane productCartPane4;
    public Pane productCartPane5;
    public Pane productCartPane6;
    public Pane productCartPane7;
    public Pane productCartPane8;
    public Pane productCartPane9;
    public ImageView cancelCategoryButton;
    public ArrayList<Pane> panes;

    public ArrayList<ImageView> images;
    public static int startProductIndex = 0;

    private ArrayList<Product> allProducts = Product.getAllProductsInList();
    public CheckBox availableFilterCheckBox;
    @FXML
    private ListView<String> categoryListView;
    ObservableList<String> categories;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Processor.isLogin) {
            userName.setText(Processor.user.getUsername());
        }
        images = new ArrayList<>();
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);
        images.add(image6);
        images.add(image7);
        images.add(image8);
        images.add(image9);
        panes = new ArrayList<>();
        panes.add(productCartPane1);
        panes.add(productCartPane2);
        panes.add(productCartPane3);
        panes.add(productCartPane4);
        panes.add(productCartPane5);
        panes.add(productCartPane6);
        panes.add(productCartPane7);
        panes.add(productCartPane8);
        panes.add(productCartPane9);
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
        showProducts();
    }

    public void showProducts() {
        for (int i = startProductIndex; i < startProductIndex + 9; i++) {
            if (allProducts.size() > i) {
                if (allProducts.get(i).getImagePath() != null) {
                    images.get(i - startProductIndex).setImage(new Image("file:" + allProducts.get(i).getImagePath()));
                } else {
                    File file = new File("src/main/resources/product.jpg");
                    Image image = new Image(file.toURI().toString());
                    images.get(i - startProductIndex).setImage(image);
                }
                panes.get(i - startProductIndex).setVisible(true);
            } else {
                panes.get(i - startProductIndex).setVisible(false);
            }
        }
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
        startProductIndex = 0;
        showProducts();
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
        allProducts = Product.getAllProductsInList();
        if (!nameFilterText.getText().equals("")) {
            buyerProcessor.filter("by name " + nameFilterText.getText());
        } else {
            buyerProcessor.disableFilter("name");
        }
        buyerProcessor.filter("by price from " + (int) Double.parseDouble(minValue.getText()) + " to " +
                (int) Double.parseDouble(maxValue.getText()));
        if (!categoryName.getText().equalsIgnoreCase("categories")) {
            filterByCategory(categoryName.getText());
            for (HBox item : featuresOfCategoryForFilter.getItems()) {
                String feature = ((Text) item.getChildren().get(0)).getText();
                CheckComboBox<String> checkComboBox = ((CheckComboBox<String>) item.getChildren().get(1));
                for (String checkedItem : checkComboBox.getCheckModel().getCheckedItems()) {
                    buyerProcessor.addFeaturesCategory(feature, checkedItem);
                }
            }
        } else {
            buyerProcessor.disableCategoryFilter();
        }
        allProducts = buyerProcessor.getProductAfterFilter(allProducts);
        getProductsAfterSort();
        startProductIndex = 0;
        showProducts();

    }

    public boolean hasNextPage() {
        if (startProductIndex + 9 >= allProducts.size()) {
            return false;
        }
        return true;
    }

    public void nextPage() {
        if (hasNextPage()) {
            startProductIndex += 9;
            showProducts();
        }
    }

    public boolean hasPreviousPage() {
        if (startProductIndex == 0) {
            return false;
        }
        return true;
    }

    public void previousPage() {
        if (hasPreviousPage()) {
            startProductIndex -= 9;
            showProducts();
        }
    }

    public void showCategories() {
        boolean visible = categoryListView.isVisible();
        categoryListView.setVisible(!visible);
    }

    public void cancelFilterByCategory() {
        categoryName.setText("categories");
        cancelCategoryButton.setVisible(false);
        filter();
    }

    public void selectedCategory() {
        int index = categoryListView.getSelectionModel().getSelectedIndex();
        filterByCategory(categories.get(index));
        categoryListView.setVisible(false);
        showCategoryFeatures();
        cancelCategoryButton.setVisible(true);
        filter();
    }

    public void showCategoryFeatures() {
        featuresOfCategoryForFilter.getItems().clear();
        for (String feature : Category.getCategoryByName(categoryName.getText()).getFeatures()) {
            HBox hBox = new HBox();
            CheckComboBox<String> checkComboBox = new CheckComboBox<String>();
            checkComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> filter());
            hBox.getChildren().add(new Text(feature));
            hBox.getChildren().add(checkComboBox);
            featuresOfCategoryForFilter.getItems().add(hBox);
            TreeSet<String> featuresValue = new TreeSet<>();
            for (Product product : allProducts) {
                if (product.getCategory().getName().equals(categoryName.getText()))
                    featuresValue.add(product.getFeaturesMap().get(feature));
            }
            checkComboBox.getItems().addAll(featuresValue);
        }
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
        filter();
    }

    public void filterOff() {
        if (offFilterCheckBox1.isSelected()) {
            buyerProcessor.filter("off");
        } else {
            buyerProcessor.disableFilter("off");
        }
        filter();
    }
}