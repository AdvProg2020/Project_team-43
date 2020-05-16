package View;


import model.Product;

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
                System.out.println("ADD to cart");
                System.out.println("back");
                String command = scanner.nextLine();
                try {
                    processor.manageDigest(command, productId);
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }

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
                try {
                    processor.showAttributes(productId);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
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
                System.out.println("second product Id : ");
                String secondProductId = scanner.nextLine();
                try {
                    processor.compareProcess(productId, secondProductId);
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }

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
                try {
                    processor.manageComments(command, productId);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

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
        Product product = Product.getProductById(productId);
        if (product == null) {
            System.out.println("product with this Id doesn't exist");
            return;
        }
        product.addVisit();
    }

    public void show() {
        getIdProductFromUser();
        System.out.println(this.name + ":");
        for (Integer menuNum : submenus.keySet()) {
            System.out.println(menuNum + ". " + submenus.get(menuNum).name);
        }
        if (processor.isUserLoggedIn()) {//////////////////////////////////////////////chi shode in error dare??
            System.out.println((submenus.size() + 1) + ". logout");
        } else {
            System.out.println((submenus.size() + 1) + ". login");
        }
        if (this.parent != null)
            System.out.println((submenus.size() + 2) + ". Back");
        else
            System.out.println((submenus.size() + 2) + ". Exit");
    }
}



