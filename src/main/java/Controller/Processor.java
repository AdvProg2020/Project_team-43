package Controller;

import model.Category;
import model.Off;
import model.Product;

import java.util.ArrayList;

public class Processor {
    public Processor() {
    }

    public void viewCategories() {
        ArrayList<Category> categories = Category.allCategories;
        //TODO : send to view //how?? (optional)
    }

    public void filteringProcess(String command) {


    }

    public void sortingProcess(String command) {


    }

    public void showProducts(){
        ArrayList<Product> products = Product.allProductsInList;


    }
    public void showProductById(String Id){
        Product product = Product.getProductById(Id);


    }

    public void showDigest(String command, String Id){
        Product product = Product.getProductById(Id);

    }

    public void showAttributes(String Id){
        Product product = Product.getProductById(Id);

    }

    public void compareProcess(String firstProductId, String secondProductId){
        Product firstProduct = Product.getProductById(firstProductId);
        Product secondProduct = Product.getProductById(secondProductId);


    }

    public void showComments(String Id){
        Product product = Product.getProductById(Id);

    }

    public void showOffs(){
        ArrayList<Off> offs = Off.acceptedOffs;


    }


}
