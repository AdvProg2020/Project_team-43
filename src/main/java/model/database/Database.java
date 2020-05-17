package model.database;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Database {

    public static void load() throws FileNotFoundException {
        Company.load();
        User.load();
        Category.load();
        CodedDiscount.load();


        Buyer.loadFields();
        Seller.loadAllFields();
    }

    public static void save() throws IOException {
        Buyer.saveFields();
        Seller.saveAllFields();

        Company.save();
        User.save();
        Category.save();
        CodedDiscount.save();
    }
}
