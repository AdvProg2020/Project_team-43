package model.request;

import model.Product;

public class EditProductRequest extends Request {
    String oldProductId;
    Product newProduct;

    public EditProductRequest(String oldProductId, Product newProduct) {
        super("edit product");
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
