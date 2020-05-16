package model;

import View.MainMenu;
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
            e.printStackTrace();
        }
        MainMenu mainMenu = new MainMenu("main menu");
        mainMenu.show();
        mainMenu.run();
    }

    public void close() {
        try {
            Database.save();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
