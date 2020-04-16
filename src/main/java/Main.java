import View.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu("main menu");
        mainMenu.setScanner(new Scanner(System.in));
        mainMenu.show();
        mainMenu.run();
    }
}
