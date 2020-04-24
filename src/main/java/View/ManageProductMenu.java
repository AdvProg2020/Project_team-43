package View;

import java.util.HashMap;

public class ManageProductMenu extends Menu {

    public ManageProductMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getViewProduct());
        submenus.put(2, new ProductsPanel(this, "ProductsPanel"));
        submenus.put(3, new OffPanel(this, "OffPanel"));
        this.setSubmenus(submenus);
    }

    private Menu getViewProduct() {
        return new Menu(this, "View Product Info") {

        };
    }
}
