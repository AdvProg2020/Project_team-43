package View.console;

import java.util.HashMap;

public class ManageCartMenu extends Menu {
    public ManageCartMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, showProducts());
        submenus.put(2, new ProductPanel(this, "product panel"));
        submenus.put(3, increaseProduct());
        submenus.put(4, decreaseProduct());
        submenus.put(5, showTotalPrice());
        submenus.put(6, new Purchase(this, "purchase"));
        this.setSubmenus(submenus);
    }

    private Menu showProducts() {
        return new Menu(this, "showProducts") {
            @Override
            public void show() {
                buyerProcessor.showProductsInCart();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu increaseProduct() {
        return new Menu(this, "increase product") {
            @Override
            public void show() {
                System.out.print("product Id : ");
                String productId = scanner.nextLine();
                System.out.println("seller name : ");
                String sellerName = scanner.nextLine();
                try {
                    buyerProcessor.increaseProduct(productId, sellerName);
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

    private Menu decreaseProduct() {
        return new Menu(this, "decrease product") {
            @Override
            public void show() {
                System.out.print("product Id : ");
                String productId = scanner.nextLine();
                System.out.println("seller name : ");
                String sellerName = scanner.nextLine();
                try {
                    buyerProcessor.decreaseProduct(productId, sellerName);
                } catch (NullPointerException e) {
                    e.getMessage();
                }
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu showTotalPrice() {
        return new Menu(this, "total price") {
            @Override
            public void show() {
                System.out.println(buyerProcessor.showTotalPrice());
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

}
