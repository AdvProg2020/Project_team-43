package model.database;

import model.*;
import model.request.*;

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
        Supporter.load();

        User.loadFields();
        Request.loadFields();
        Category.loadFields();
        Off.loadFields();
        Comment.loadFields();
        Product.loadFields();
        Order.loadAllFields();
    }

    public static void save() throws IOException {
        Request.saveFields();
        Category.saveFields();
        Off.saveFields();
        Comment.saveFields();
        Order.saveAllFields();
        Product.saveFields();
        User.saveFields();


        CodedDiscount.save();
        Company.save();
        Product.save();
        Order.save();
        User.save();
        Comment.save();
        Off.save();
        Request.save();
        Category.save();
        Supporter.save();

    }
}
