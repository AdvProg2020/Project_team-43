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
            System.exit(-1);
        }
        MainMenu mainMenu = new MainMenu("main menu");
        mainMenu.show();
        mainMenu.run();
    }

    public void close() {
        try {
            Database.save();
        } catch (IOException e) {
            System.exit(-1);
        } finally {
            System.exit(0);
        }
    }
}
