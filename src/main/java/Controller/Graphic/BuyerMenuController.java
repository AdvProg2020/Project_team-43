package Controller.Graphic;

import Controller.console.BuyerProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.Buyer;
import model.Product;
import model.Seller;
import model.UserPersonalInfo;

import java.io.File;
import java.util.HashMap;

public class BuyerMenuController extends Controller {
    public ListView<String> products;
    public Text userName;
    public Text totalPrice;
    BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;
    public ImageView profilePhoto;
    private Buyer user;

    @FXML
    public void initialize() {
        user = (Buyer) buyerProcessor.getUser();
        userName.setText(user.getUsername());
        UserPersonalInfo userPersonalInfo = user.getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        if (user.getImagePath() != null) {
            profilePhoto.setImage(new Image("file:" + user.getImagePath()));
        }
        setCart();
    }

    public void setCart() {
        buyerProcessor.addToBuyerCart(new Pair<Product, Seller>(Product.getProductById("1"),
                Product.getProductById("1").getSellers().get(0)));
        HashMap<Pair<Product, Seller>, Integer> cart = user.getNewBuyerCart();
        for (Pair<Product, Seller> productSellerPair : cart.keySet()) {
            products.getItems().add(productSellerPair.getKey().getName() + " " +
                    productSellerPair.getValue().getUsername() + " " + cart.get(productSellerPair));
        }
        products.setCellFactory(param -> new XCell(user));
        setTotalPrice(Double.toString(user.getNewCartPrice()));
    }

    public void setTotalPrice(String text) {
        this.totalPrice.setText(text);
    }

    public void update() {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        buyerProcessor.editField(userPersonalInfo);
    }

    public void browsePhotoUser() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            user.setImagePath(file.getAbsolutePath());
            profilePhoto.setImage(new Image(file.toURI().toString()));
        }
    }

    private class XCell extends ListCell<String> {
        Buyer buyer;
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button addButton = new Button("+");
        Button removeButton = new Button("-");

        public XCell(Buyer buyer) {
            super();
            this.buyer = buyer;
            hbox.getChildren().addAll(label, pane, addButton, removeButton);
            HBox.setHgrow(pane, Priority.ALWAYS);
            addButton.setOnAction(event -> addItem(getItem()));
            removeButton.setOnAction(event -> removeItem(getItem()));
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);
            if (item != null && !empty) {
                label.setText(item);
                setGraphic(hbox);
            }
        }

        private void addItem(String item) {
            String productName = item.split(" ")[0];
            String sellerName = item.split(" ")[1];
            for (Pair<Product, Seller> productSellerPair : buyer.getNewBuyerCart().keySet()) {
                if (productSellerPair.getKey().getName().equals(productName) &&
                        productSellerPair.getValue().getUsername().equals(sellerName)) {
                    BuyerProcessor.getInstance().increaseProduct(productSellerPair.getKey().getProductId(),
                            productSellerPair.getValue().getUsername());
                    label.setText(productSellerPair.getKey().getName() + " " + productSellerPair.getValue().getUsername() + " " +
                            buyer.getNewBuyerCart().get(productSellerPair));
                }
            }
            setTotalPrice(Double.toString(user.getNewCartPrice()));


        }

        private void removeItem(String item) {
            String productName = item.split(" ")[0];
            String sellerName = item.split(" ")[1];
            for (Pair<Product, Seller> productSellerPair : buyer.getNewBuyerCart().keySet()) {
                if (productSellerPair.getKey().getName().equals(productName) &&
                        productSellerPair.getValue().getUsername().equals(sellerName)) {
                    BuyerProcessor.getInstance().decreaseProduct(productSellerPair.getKey().getProductId(),
                            productSellerPair.getValue().getUsername());
                    if (buyer.getNewBuyerCart().containsKey(productSellerPair)) {
                        label.setText(productSellerPair.getKey().getName() + " " + productSellerPair.getValue().getUsername() + " " +
                                buyer.getNewBuyerCart().get(productSellerPair));
                    } else {
                        getListView().getItems().remove(getItem());
                    }
                }
            }
            setTotalPrice(Double.toString(user.getNewCartPrice()));
        }
    }
}
