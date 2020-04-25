package View;

import java.util.HashMap;

public class ProductsPanel extends Menu {
    public ProductsPanel(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<Integer,Menu>();
        submenus.put(1,viewCategory());
        submenus.put(2,getFiltering());
        submenus.put(3,getSorting());
        submenus.put(4,showProducts());
        submenus.put(5,new ProductPanel(this,"show product with Id"));
        this.setSubmenus(submenus);
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
                System.out.println("1 . show available filters");
                System.out.println("2 . filter");
                System.out.println("3 . current filters");
                System.out.println("4 . disable filter");
                System.out.println("5 . back");
                String command = scanner.nextLine();
                manager.filteringProcess(command);
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
                System.out.println("1 . show available sorts");
                System.out.println("2 . sort");
                System.out.println("3 . current sort");
                System.out.println("4 . disable sort");
                System.out.println("5 . back");
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

