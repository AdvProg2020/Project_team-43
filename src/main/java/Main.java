import View.App;
import View.MainMenu;

public class Main {
    public static void main(String[] args) {
        App.getInstance().open();
        MainMenu mainMenu = new MainMenu("main menu");
        mainMenu.show();
        mainMenu.run();
    }
}