package Controller.Graphic;

import Controller.console.BuyerProcessor;
import View.graphic.BuyerUserWindow;
import View.graphic.MainWindow;
import View.graphic.ProductWindow;
import View.graphic.PurchaseWindow;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import model.*;

import java.io.File;
import java.util.HashMap;

public class BuyerMenuController extends Controller {
    private static boolean isBack = false;
    public ListView<String> products;
    public Text userName;
    public Text totalPrice;
    public ListView<String> discountCodes;
    public ListView<String> orders;
    public ListView<String> order;
    public ImageView closeButton;
    public Tab cartTab;
    public TabPane tabPane;
    public Label balance;
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
        balance.setText(Double.toString(user.getBalance()));
        if (user.getImagePath() != null) {
            profilePhoto.setImage(new Image("file:" + user.getImagePath()));
        }
        setCart();
        setDiscountCodes();
        setOrders();
        if (isBack) {
            isBack = false;
            tabPane.getSelectionModel().select(cartTab);
        }
    }

    public void setCart() {
        HashMap<Pair<Product, Seller>, Integer> cart = user.getNewBuyerCart();
        for (Pair<Product, Seller> productSellerPair : cart.keySet()) {
            setCartCells(productSellerPair);
        }
        products.setCellFactory(param -> new XCell(user));
        setTotalPrice(Double.toString(user.getNewCartPrice()));
    }

    public void setDiscountCodes() {
        for (CodedDiscount discount : user.getDiscounts()) {
            discountCodes.getItems().add(discount.toString() + "remain : " + user.remainRepeats(discount));
        }
    }

    public void setOrders() {
        order.setVisible(false);
        closeButton.setVisible(false);
        for (BuyOrder order : user.getOrders()) {
            orders.getItems().add("id:\t" + order.getOrderId() + "\t" + order.getPayment());
        }
    }

    public void setTotalPrice(String text) {
        this.totalPrice.setText(text);
    }

    public void update() {
        Music.getInstance().confirmation();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        buyerProcessor.editField(userPersonalInfo);
    }

    public void browsePhotoUser() {
        Music.getInstance().open();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            user.setImagePath(file.getAbsolutePath());
            profilePhoto.setImage(new Image(file.toURI().toString()));
        }
    }

    public void setCartCells(Pair<Product, Seller> productSellerPair) {
        products.getItems().add(productSellerPair.getKey().getName() + "\t" +
                productSellerPair.getValue().getUsername() + "\t" + user.getNewBuyerCart().get(productSellerPair) + "\t" + productSellerPair.getKey().getPrice() + "\t" +
                productSellerPair.getKey().getPrice() * user.getNewBuyerCart().get(productSellerPair));

    }

    public void showOrder() {
        order.getItems().clear();
        String orderId = orders.getSelectionModel().getSelectedItem().split("\t")[1];
        BuyOrder buyOrder = (BuyOrder) Order.getOrderById(orderId);
        HashMap<Product, Integer> products = buyOrder.getProducts();
        int i = 0;
        for (Product product : products.keySet()) {
            order.getItems().add(product.getName() + " " + products.get(product) + " " + buyOrder.getSellers().get(i).getUsername());
            i++;
        }
        order.getItems().add(Double.toString(buyOrder.getPayment()));
        order.getItems().add(buyOrder.getDeliveryStatus().toString());
        order.getItems().add(buyOrder.getDate().toString());
        order.getItems().add(buyOrder.getAddress());
        order.setVisible(true);
        closeButton.setVisible(true);

    }

    public void closeOrder() {
        Music.getInstance().close();
        order.setVisible(false);
        closeButton.setVisible(false);

    }

    public void purchase() {
        Music.getInstance().confirmation();
        try {
            isBack = true;
            PurchaseWindow.getInstance().start(this.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProduct(MouseEvent mouseEvent) {
        Music.getInstance().open();
        if (mouseEvent.getClickCount() >= 2) {
            isBack = true;
            String productName = products.getSelectionModel().getSelectedItem().split("\t")[0];
            String sellerName = products.getSelectionModel().getSelectedItem().split("\t")[1];
            for (Pair<Product, Seller> productSellerPair : ((Buyer) buyerProcessor.getUser()).getNewBuyerCart().keySet()) {
                if (productSellerPair.getKey().getName().equals(productName) && productSellerPair.getValue().getUsername().equals(sellerName)) {
                    ProductWindow.getInstance().setProduct(productSellerPair.getKey(), BuyerUserWindow.getInstance());
                    ProductWindow.getInstance().start(MainWindow.getInstance().getStage());
                }
            }
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
            addButton.setOnAction(event -> addRemoveItem(getItem(), true));
            removeButton.setOnAction(event -> addRemoveItem(getItem(), false));
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


        private void addRemoveItem(String item, boolean add) {
            String productName = item.split("\t")[0];
            String sellerName = item.split("\t")[1];
            for (Pair<Product, Seller> productSellerPair : buyer.getNewBuyerCart().keySet()) {
                if (productSellerPair.getKey().getName().equals(productName) &&
                        productSellerPair.getValue().getUsername().equals(sellerName)) {
                    if (add) {
                        BuyerProcessor.getInstance().increaseProduct(productSellerPair.getKey().getProductId(),
                                productSellerPair.getValue().getUsername());
                    } else {
                        BuyerProcessor.getInstance().decreaseProduct(productSellerPair.getKey().getProductId(),
                                productSellerPair.getValue().getUsername());
                    }
                    setLabel(productSellerPair);
                }
                setTotalPrice(Double.toString(user.getNewCartPrice()));

            }
        }

        private void setLabel(Pair<Product, Seller> productSellerPair) {
            if (buyer.getNewBuyerCart().containsKey(productSellerPair))
                label.setText(productSellerPair.getKey().getName() + "\t" + productSellerPair.getValue().getUsername() + "\t" +
                        buyer.getNewBuyerCart().get(productSellerPair) + "\t" + productSellerPair.getKey().getPrice() + "\t" +
                        productSellerPair.getKey().getPrice() * buyer.getNewBuyerCart().get(productSellerPair));
            else
                getListView().getItems().remove(getItem());
        }
    }
}
