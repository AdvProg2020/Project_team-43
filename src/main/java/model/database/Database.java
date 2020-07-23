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
        Sqlite sqlite = new Sqlite();
        sqlite.loadBuyer();
        sqlite.loadSeller();
        sqlite.loadManager();
        //User.load();
        Category.load();
        CodedDiscount.load();
        sqlite.loadBuyOrder();
        sqlite.loadSellOrder();
        //Order.load();
        sqlite.loadProduct();
        //Product.load();
        sqlite.loadFileProduct();
        Request.load();
        //Supporter.load();
        sqlite.loadSupporter();


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
