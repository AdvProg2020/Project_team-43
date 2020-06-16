package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.graphic.MainWindow;
import View.graphic.ProductWindow;
import javafx.application.Application;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import model.Product;
import model.Seller;

public class ProductWindowController {
    public ImageView productImage;
    public Label productName;
    public Label productPrice;
    public Label productScore;
    public ChoiceBox sellers;
    private Product product;
    Application parent;

    public void setProductAndParent(Product product, Application parent) {
        this.product = product;
        this.parent = parent;
        //setProductImage();
        setProductProperties();
        setSellers();

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
        productImage.setImage(new Image("file:" + product.getImagePath()));
    }

    public void goBack() {
        try {
            parent.start(ProductWindow.getInstance().getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart(MouseEvent mouseEvent) {
        Seller seller = (Seller) Seller.getUserByUserName((String) sellers.getValue());
        BuyerProcessor.getInstance().addToBuyerCart(new Pair<>(product, seller));
    }
}
