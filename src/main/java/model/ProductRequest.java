package model;

public class ProductRequest extends Request {
    private Product product;

    public ProductRequest(String requestId, Product product) {
        super(requestId,"product");
        Manager.allRequest.add(this);
        this.product = product;
        this.requestType = "product";
    }
}
