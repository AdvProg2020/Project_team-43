package model.request;

import model.Manager;
import model.Product;

public class ProductRequest extends Request {
    private Product product;

    public ProductRequest(Product product) {
        super("product");
        Manager.allRequest.add(this);
        allRequests.add(this);
        this.product = product;
        this.requestType = "product";
    }

    public Product getProduct() {
        return product;
    }
}
