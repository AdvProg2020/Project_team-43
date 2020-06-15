package Controller.Graphic;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;

public class ProductWindowController {
    public ImageView productImage;
    private Product product;

    public void setProduct(Product product) {
        this.product = product;

    }

    public void setProductImage() {
        productImage.setImage(new Image("file:" + product.getImagePath()));
    }
}
