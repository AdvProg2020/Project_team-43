package model.request;

import model.*;
import model.database.Loader;
import model.database.Saver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EditProductRequest extends Request {
    private static String fileAddress = "database/EditProductRequest.dat";
    private transient Product product;
    private String productId;
    private String field;
    private String input;
    private Seller seller;

    public EditProductRequest(Product product, String field, String input, Seller seller) {
        super("edit product");
        this.product = product;
        this.field = field;
        this.input = input;
        this.seller = seller;
        product.setProductState(State.ProductState.EDITING_PROCESS);
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

    public static void load() throws FileNotFoundException {
        EditProductRequest[] editProductRequests = (EditProductRequest[]) Loader.load(EditProductRequest[].class, fileAddress);
        if (editProductRequests != null) {
            Request.addAll(new ArrayList<>(Arrays.asList(editProductRequests)));
        }
    }


    public static void save() throws IOException {
        ArrayList<EditProductRequest> editProductRequests = new ArrayList<>();
        for (Request request : allRequests) {
            if (request.getRequestType().equals("edit product")) {
                editProductRequests.add((EditProductRequest) request);
            }
        }
        Saver.save(editProductRequests, fileAddress);
    }

    private void loadProduct() {
        this.product = Product.getAllProductById(productId);
    }

    private void saveProduct() {
        this.productId = product.getProductId();
    }

    private static void loadAllProducts() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("edit product")) {
                ((EditProductRequest) request).loadProduct();
            }
        }
    }

    private static void saveAllProducts() {
        for (Request request : allRequests) {
            if (request.getRequestType().equals("edit product")) {
                ((EditProductRequest) request).saveProduct();
            }
        }
    }

    public static void loadFields() {
        loadAllProducts();
    }

    public static void saveFields() {
        saveAllProducts();
    }

    @Override
    public String toString() {
        return "EditProductRequest{" +
                "requestId='" + requestId + '\'' +
                ", product=" + product +
                ", field='" + field + '\'' +
                ", input='" + input + '\'' +
                ", seller=" + seller +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}
