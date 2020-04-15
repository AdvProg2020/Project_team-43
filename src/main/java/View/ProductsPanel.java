package View;

import model.Category;

import java.util.ArrayList;

public class ProductsPanel extends Menu {
    public ProductsPanel(Menu parent, String name) {
        super(parent, name);
        submenus.put(1,viewCategory());
        submenus.put(2,getFiltering());
        submenus.put(3,getSorting());
        submenus.put(4,showProducts());
        submenus.put(5,new ProductPanel(this,"show product with Id"));
    }

    private Menu viewCategory() {

        return new Menu(this, "view categories") {

            @Override
            public void show() {
                manager.viewCategories();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    public Menu getFiltering() {

        return new Menu(this, "filtering") {
            @Override
            public void show() {
                String command = scanner.nextLine();
                manager.filteringProcess(command);
                //
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getSorting() {

        return new Menu(this, "sorting") {
            @Override
            public void show() {
                String command = scanner.nextLine();
                manager.sortingProcess(command);
                //
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu showProducts() {

        return new Menu(this, "show products") {
            @Override
            public void show() {
                manager.showProducts();
            }
            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

}

