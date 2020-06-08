package View;

import model.database.Database;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
    private static App instance = new App();

    private App() {

    }

    public static App getInstance() {
        return instance;
    }

    public void open() {
        try {
            Database.load();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading database");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void close() {
        try {
            Database.save();
        } catch (IOException e) {
            System.out.println("Error saving database");
            e.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }
}
