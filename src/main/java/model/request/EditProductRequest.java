package model.request;

import model.Product;

public class EditProductRequest extends Request {
    String oldProductId;
    Product newProduct;

    public EditProductRequest(String requestType, String oldProductId, Product newProduct) {
        super(requestType);
        this.oldProductId = oldProductId;
        this.newProduct = newProduct;
    }

    public String getOldProductId() {
        return oldProductId;
    }

    public Product getNewProduct() {
        return newProduct;
    }
}
