package View.GraphicController;

import controller.client.BuyerProcessor;
import controller.client.Processor;
import View.graphic.*;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Category;
import model.Off;
import model.Product;
import model.Sorting;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.RangeSlider;

import java.util.ArrayList;
import java.util.TreeSet;


public class ProductPanelController extends Controller {
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
    public ImageView cancelCategoryButton;
    public ArrayList<Pane> panes;
    public TextField pageNumber;
    private int startProductIndex = 0;

    private ArrayList<Category> allCategoriesFromServer;
    private ArrayList<Product> allProductsFromServer;
    private ArrayList<Product> allProducts;
    public CheckBox availableFilterCheckBox;
    @FXML
    private ListView<String> categoryListView;
    ObservableList<String> categories;

    public void initialize() {
        setOffs();
        initProducts();
        initCategories();
        this.parent = ProductPanelWindow.getInstance();
        pageNumber.setText("1");
        if (Processor.isLogin) {
            userName.setText(Processor.user.getUsername());
        }
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
        for (Category category : allCategoriesFromServer) {
            categories.add(category.getName());
        }
        categoryListView.setItems(categories);
        getProductsAfterSort();
        showProducts();
    }

    private void initCategories() {
        allCategoriesFromServer = client.getAllCategories();
        Category.setAllCategories(allCategoriesFromServer);
    }

    private void initProducts() {
        allProductsFromServer = client.getAllProducts();
        allProducts = allProductsFromServer;
        Product.setAllProductsInList(allProductsFromServer);
    }

    public void showProducts() {
        for (int i = startProductIndex; i < startProductIndex + 9; i++) {
            Pane pane = panes.get(i - startProductIndex);
            if (allProducts.size() > i) {
                setProductImage(allProducts.get(i), (ImageView) pane.getChildren().get(0), 140, 220);
                panes.get(i - startProductIndex).setVisible(true);

                ((Label) pane.getChildren().get(1)).setText(allProducts.get(i).getName());
                if (allProducts.get(i).getAvailableCount() > 0) {
                    ((Label) pane.getChildren().get(2)).setTextFill(Color.BLACK);
                    ((Label) pane.getChildren().get(2)).setText("price: " + allProducts.get(i).getPrice());
                } else {
                    ((Label) pane.getChildren().get(2)).setTextFill(Color.RED);
                    ((Label) pane.getChildren().get(2)).setText("Not Available");
                }
                ((Label) pane.getChildren().get(3)).setText("id: " + allProducts.get(i).getProductId());
                ((Label) pane.getChildren().get(4)).setText(allProducts.get(i).getScore().getAvgScore() + "/5");
            } else {
                pane.setVisible(false);
            }
        }

    }

    public void sort(Toggle selectedToggle) {
        Music.getInstance().confirmation();
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
        Music.getInstance().confirmation();
        allProducts = allProductsFromServer;
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
        return startProductIndex + 9 < allProducts.size();
    }

    public void nextPage() {
        Music.getInstance().open();
        if (hasNextPage()) {
            pageNumber.setText((Integer.parseInt(pageNumber.getText()) + 1) + "");
            startProductIndex += 9;
            showProducts();
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
            showProducts();
        }
    }

    public void showCategories() {
        Music.getInstance().open();
        boolean visible = categoryListView.isVisible();
        categoryListView.setVisible(!visible);
    }

    public void cancelFilterByCategory() {
        Music.getInstance().close();
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

    private Category getCategoryByName(String name) {
        for (Category category : allCategoriesFromServer) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public void showCategoryFeatures() {
        featuresOfCategoryForFilter.getItems().clear();
        for (String feature : getCategoryByName(categoryName.getText()).getFeatures()) {
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

    private void setOffs() {
        Off.setAcceptedOffs(client.getOffs());
    }

    public void openProductPanel(MouseEvent mouseEvent) {
        Music.getInstance().open();
        Product product = getProductById((((Label) ((Pane) mouseEvent.getSource()).getChildren().get(3)).getText().split(" ")[1]));
        ProductWindow.getInstance().setProduct(product, ProductPanelWindow.getInstance());
        ProductWindow.getInstance().start(MainWindow.getInstance().getStage());

    }

    private Product getProductById(String id) {
        for (Product product1 : allProductsFromServer) {
            if (product1.getProductId().equals(id)) {
                return product1;
            }
        }
        return null;
    }

}
