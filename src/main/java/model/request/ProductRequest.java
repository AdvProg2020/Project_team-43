package model.request;

import model.Manager;
import model.Product;
import model.Seller;

public class ProductRequest extends Request {
    private Product product;
    private Seller seller;
    private int number;

    public ProductRequest(Product product, Seller seller, int number) {
        super("product");
        this.product = product;
        this.requestType = "product";
        this.seller = seller;
        this.number = number;
        Manager.allRequest.add(this);
        allRequests.add(this);
    }

    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getNumber() {
        return number;
    }
}
