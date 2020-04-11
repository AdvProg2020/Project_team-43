package View;


public class ProductsPanel extends Menu {
    public ProductsPanel(Menu parent, String name) {
        super(parent, name);
        submenus.put(1, getCategory());
        submenus.put(2, getFiltering());
        submenus.put(3, getSorting());
        submenus.put(4, getProducts());
        submenus.put(5, new ProductPanel(this, "product Panel"));
    }
    private Menu getCategory() {

        return new Menu(this, "category") {
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
    private Menu getProducts() {

        return new Menu(this, "products") {
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



    public void execute() {
        this.show();
        this.run();
    }

}

