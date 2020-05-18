package model.database;

import model.*;
import model.request.Request;

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
        Request.load();


        Buyer.loadFields();
        Request.loadFields();
        Category.loadFields();
        Off.loadFields();
        Comment.loadFields();
        Seller.loadAllFields();
        Product.loadFields();
        Order.loadAllFields();
    }

    public static void save() throws IOException {
        Buyer.saveFields();
        Request.saveFields();
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
        Request.save();
        Category.save();

    }
}
