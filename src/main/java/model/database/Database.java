package model.database;

import model.Company;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Database {

    public static void load() throws FileNotFoundException {
        Company.load();
    }

    public static void save() throws IOException {
        Company.save();
    }
}
