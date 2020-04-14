package View;

import model.Category;

import java.util.ArrayList;

public class ProductsPanel extends Menu {
    private ArrayList<Category> categories = manager.viewCategories();
    public ProductsPanel(Menu parent, String name) {
        super(parent, name);
        submenus.put(1, new ProductsOfCategory(this,"productsInCategory"));
    }

    private Menu getCategory() {

        return new Menu(this, "category") {

            @Override
            public void show() {
                ArrayList<Category> categories = manager.viewCategories();
                for (int i = 0; i < categories.size(); i++)
                    System.out.println((i + 1) + ".  " + categories.get(i).getName());
                System.out.println((categories.size() + 1) + " " + "back");
            }

            @Override
            public void run() {
                int input = Integer.parseInt(scanner.nextLine());



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

    public void show(){

        for (int i = 0; i < categories.size(); i++)
            System.out.println((i + 1) + ".  " + categories.get(i).getName());
        System.out.println((categories.size() + 1) + " " + "back");
    }
    public void run(){
        int input=scanner.nextInt();
        if (categories.size()>=input){
            ((ProductsOfCategory)submenus.get(1)).show(categories.get(input-1).getName());
            ((ProductsOfCategory)submenus.get(1)).run(categories.get(input-1).getName());
        }
        else {
            this.parent.show();
            this.parent.run();
        }

    }
}

