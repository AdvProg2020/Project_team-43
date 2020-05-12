import View.MainMenu;
import model.Company;

public class Main {
    public static void main(String[] args) {
        new Company("dashagh", "none");
        MainMenu mainMenu = new MainMenu("main menu");
        mainMenu.show();
        mainMenu.run();
    }
}