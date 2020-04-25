package View;

import java.util.HashMap;

public class ManageProductMenu extends Menu {

    public ManageProductMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getViewProduct());
        submenus.put(2, getViewBuyers());
        submenus.put(3, getEditProduct());
        this.setSubmenus(submenus);
    }

    private Menu getViewProduct() {
        return new Menu(this, "View Product Info") {
            @Override
            public void show() {
                System.out.println("Please enter product id");
                sellerProcessor.viewProductInfo(scanner.nextLine());
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewBuyers() {
        return new Menu(this, "View Buyers") {
            @Override
            public void show() {
                System.out.println("Please enter product id");
                sellerProcessor.viewBuyers(scanner.nextLine());
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getEditProduct() {
        return new Menu(this, "Edit Product Info") {
            @Override
            public void show() {
                System.out.println("Please enter product id");
                sellerProcessor.editProductInfo(scanner.nextLine());
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }
}
