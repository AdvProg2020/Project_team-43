package model.request;

import model.Manager;
import model.Product;
import model.Seller;

public class EditProductRequest extends Request {
    Product product;
    String field;
    String input;
    Seller seller;

    public EditProductRequest(Product product, String field, String input, Seller seller) {
        super("edit product");
        this.product = product;
        this.field = field;
        this.input = input;
        this.seller = seller;
        Manager.allRequest.add(this);
        allRequests.add(this);
    }


    public Product getProduct() {
        return product;
    }

    public String getField() {
        return field;
    }

    public String getInput() {
        return input;
    }
}
