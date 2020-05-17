package model.database;

import model.*;

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
        Product.load();


        Buyer.loadFields();
        Category.loadFields();
        Off.loadFields();
        Comment.loadFields();
        Seller.loadAllFields();
        Product.loadFields();
    }

    public static void save() throws IOException {
        Buyer.saveFields();
        Seller.saveAllFields();
        Category.saveFields();
        Off.saveFields();
        Comment.saveFields();
        Company.save();
        Product.save();

        User.save();
        Comment.save();
        Off.save();
        Category.save();
        CodedDiscount.save();
        Product.saveFields();
    }
}
