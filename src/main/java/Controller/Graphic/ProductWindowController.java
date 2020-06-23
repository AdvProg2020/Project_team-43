package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.graphic.AddCommentWindow;
import View.graphic.MainWindow;
import View.graphic.ProductWindow;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import javafx.util.Pair;
import model.Buyer;
import model.Comment;
import model.Product;
import model.Seller;
import org.controlsfx.control.Rating;

import java.util.Map;

public class ProductWindowController {
    public ImageView productImage;
    public Label productName;
    public Label productPrice;
    public Label productScore;
    public ChoiceBox<String> sellers;
    public Label error;
    public Rating rating;
    public TableView<Map.Entry<String, String>> features;
    public TableColumn<Map.Entry<String, String>, String> feature;
    public TableColumn<Map.Entry<String, String>, String> value;
    public ListView<String> comments;
    public TextArea comment;
    private static boolean isBackToComment = false;
    public TabPane tabPane;
    public Tab tab;
    public ImageView ivTarget;


    private Product product;
    Application parent;

    public void setProductAndParent(Product product, Application parent) {
        this.product = product;
        this.parent = parent;
        setProductImage();
        ivTarget.setVisible(false);
        setProductProperties();
        setSellers();
        setFeatures();
        setComments();
        comment.setEditable(false);
        error.setVisible(false);
        if (isBackToComment) {
            isBackToComment = false;
            tabPane.getSelectionModel().select(tab);
        }

    }

    public void setComments() {
        product.getComments().forEach((comment -> comments.getItems().add(comment.getBuyerUserName() + "\t" + comment.isBuy())));
    }

    public void setFeatures() {
        feature.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        value.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(product.getFeaturesMap().entrySet());
        features.getColumns().setAll(feature, value);
        features.setItems(items);

    }

    public void setSellers() {
        for (Seller seller : product.getSellers()) {
            sellers.getItems().add(seller.getUsername());
        }
        sellers.setValue(product.getSellers().get(0).getUsername());
    }

    public void setProductProperties() {
        productName.setText(product.getName());
        productPrice.setText(Double.toString(product.getPrice()));
        productScore.setText(Double.toString(product.getScore().getAvgScore()));
    }

    public void setProductImage() {
        if (product.getImagePath() != null) {
            productImage.setImage(new Image("file:" + product.getImagePath()));
        }
    }

    public void goBack() {
        try {
            parent.start(ProductWindow.getInstance().getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart() {
        Seller seller = (Seller) Seller.getUserByUserName(sellers.getValue());
        new Alert(Alert.AlertType.INFORMATION, BuyerProcessor.getInstance().addToBuyerCart(new Pair<>(product, seller))).showAndWait();
    }

    public void rate() {
        error.setVisible(true);
        if (!(BuyerProcessor.getInstance().getUser() instanceof Buyer)) {
            error.setText("you are not buyer");
        }
        if (!BuyerProcessor.getInstance().isUserLoggedIn()) {
            error.setText("first log in please");
        } else if (!((Buyer) BuyerProcessor.getInstance().getUser()).hasBuyProduct(product)) {
            error.setText("you didn't buy this product");
        } else if (product.getScore().isUserRatedBefore(BuyerProcessor.getInstance().getUser())) {
            error.setText("you rated before");
        } else {
            product.rateProduct((int) rating.getRating(), BuyerProcessor.getInstance().getUser());
            error.setText("done");
        }
    }

    public void showComment() {
        if (comments.getItems().size() != 0) {
            int index = comments.getSelectionModel().getSelectedIndex();
            comment.setText(product.getComments().get(index).getCommentText());
        }
    }

    public void addComment() {
        if (!BuyerProcessor.getInstance().isUserLoggedIn()) {
            new Alert(Alert.AlertType.ERROR, "first log in").showAndWait();
        } else {
            AddCommentWindow.getInstance().setProduct(product);
            AddCommentWindow.getInstance().start(MainWindow.getInstance().getStage());
            isBackToComment = true;
        }
    }


    public void zoom(MouseEvent event) {
        System.out.println("hello");
        int x = (int) event.getX();
        int y = (int) event.getY();
        ivTarget.setImage(productImage.getImage());
        Rectangle2D viewPortRect=new Rectangle2D(x,y,500,500);
        ivTarget.setViewport(viewPortRect);
        ivTarget.setVisible(true);
    }
}
