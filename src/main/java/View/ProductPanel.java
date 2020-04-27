package View;


import java.util.HashMap;

public class ProductPanel extends Menu {
    private String productId;

    public ProductPanel(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getDigest());
        submenus.put(2, getAttributes());
        submenus.put(3, getCompare());
        submenus.put(4, getComments());
        this.setSubmenus(submenus);
    }

    private Menu getDigest() {

        return new Menu(this, "digest") {
            @Override
            public void show() {
                processor.showDigest(productId);
                System.out.println("1 . ADD to cart");
                System.out.println("2 . select seller");
                System.out.println("3 . back");
                String command = scanner.nextLine();
                processor.manageDigest(command, productId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getAttributes() {

        return new Menu(this, "attributes") {
            @Override
            public void show() {
                processor.showAttributes(productId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getCompare() {

        return new Menu(this, "comparing") {
            @Override
            public void show() {
                String secondProductId = scanner.nextLine();
                processor.compareProcess(productId, secondProductId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getComments() {

        return new Menu(this, "comments") {
            @Override
            public void show() {
                processor.showComments(productId);
                System.out.println("1 . ADD comment");
                System.out.println("2 . back");
                String command = scanner.nextLine();
                processor.manageComments(command);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private void getIdProductFromUser() {
        System.out.println("product's Id : ");
        this.productId = scanner.nextLine();
    }
    public void run(String productId) {
        this.productId = productId;
    }
}



