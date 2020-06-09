package View.console;


import java.util.HashMap;

public class MainMenu extends Menu {
    public MainMenu(String name) {
        super(null, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, new UserPanelMenu(this, "UserPanel"));
        submenus.put(2, new ProductsPanel(this, "ProductsPanel"));
        submenus.put(3, new OffPanel(this, "OffPanel"));
        this.setSubmenus(submenus);
    }

    public void show() {
        super.show();
        processor.newProductFilter();
    }
}
