package Controller.Graphic;

import Controller.console.BuyerProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.Buyer;
import model.Product;
import model.Seller;
import model.UserPersonalInfo;

import java.util.HashMap;

public class BuyerMenuController extends Controller {
    public ListView<String> products;
    public Text userName;
    BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField phoneNumber;

    @FXML
    public void initialize() {
        userName.setText(buyerProcessor.getUser().getUsername());
        UserPersonalInfo userPersonalInfo = buyerProcessor.getUser().getUserPersonalInfo();
        firstName.setText(userPersonalInfo.getFirstName());
        lastName.setText(userPersonalInfo.getLastName());
        email.setText(userPersonalInfo.getEmail());
        password.setText(userPersonalInfo.getPassword());
        phoneNumber.setText(userPersonalInfo.getPhoneNumber());
        setCart();
    }

    public void setCart() {
        HashMap<Pair<Product, Seller>, Integer> cart = ((Buyer) buyerProcessor.getUser()).getNewBuyerCart();
        for (Pair<Product, Seller> productSellerPair : cart.keySet()) {
            products.getItems().add(productSellerPair.getKey() + " " +
                    productSellerPair.getValue() + " " + cart.get(productSellerPair));
        }
        products.setCellFactory(param -> (ListCell) new XCell((Buyer) buyerProcessor.getUser()));

    }

    public void update() {
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo(firstName.getText(), lastName.getText(), email.getText()
                , phoneNumber.getText(), password.getText());
        buyerProcessor.editField(userPersonalInfo);
    }

    static class XCell extends ListCell<String> {
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
            System.out.println(getItem());
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
    }
}
