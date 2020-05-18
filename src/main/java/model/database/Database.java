package model.database;

import model.*;
import model.request.SellerRequest;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Database {

    public static void load() throws FileNotFoundException {
        Company.load();
        Off.load();
        Comment.load();
        User.load();
        Category.load();
        CodedDiscount.load();
        Order.load();
        Product.load();
        SellerRequest.load();


        Buyer.loadFields();
        Category.loadFields();
        Off.loadFields();
        Comment.loadFields();
        Seller.loadAllFields();
        Product.loadFields();
        Order.loadAllFields();
        SellerRequest.loadAllFields();
    }

    public static void save() throws IOException {
        Buyer.saveFields();
        Seller.saveAllFields();
        Category.saveFields();
        Off.saveFields();
        Comment.saveFields();
        Order.saveAllFields();
        Product.saveFields();


        CodedDiscount.save();
        Company.save();
        Product.save();
        Order.save();
        User.save();
        Comment.save();
        Off.save();
        Category.save();
        SellerRequest.save();

    }
}
