package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.graphic.MainWindow;
import View.graphic.ProductWindow;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Buyer;
import model.Comment;
import model.Product;

public class AddCommentController {

    public TextArea commentText;
    public Button submitCommentButton;
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        BooleanBinding commentValid = Bindings.createBooleanBinding(() -> commentText.getText().length() > 0, commentText.textProperty());
        submitCommentButton.disableProperty().bind(commentValid.not());
    }

    public void submitComment() {
        Music.getInstance().confirmation();
        boolean isBuy = ((Buyer) BuyerProcessor.getInstance().getUser()).hasBuyProduct(product);
        Comment commentOfBuyer = new Comment(product, commentText.getText(), isBuy, (Buyer) BuyerProcessor.getInstance().getUser());
        product.addComment(commentOfBuyer);
        ((Stage) submitCommentButton.getScene().getWindow()).close();
        ProductWindow.getInstance().setProduct(product, MainWindow.getInstance());
        ProductWindow.getInstance().start(MainWindow.getInstance().getStage());
    }
}
