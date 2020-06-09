import View.console.App;
import View.console.MainMenu;

public class Main {
    public static void main(String[] args) {
        App.getInstance().open();
        MainMenu mainMenu = new MainMenu("main menu");
        mainMenu.show();
        mainMenu.run();
    }
}