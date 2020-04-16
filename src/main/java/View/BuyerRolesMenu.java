package View;

import java.util.HashMap;

public class BuyerRolesMenu extends Menu {
    private String userName;
    public BuyerRolesMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<Integer,Menu>();
        submenus.put(1, getPersonalInfo());
        submenus.put(2, getEdit());
        submenus.put(3, getViewProductInCart());
        //vared shodan be safe mahsool  morede nazar
        submenus.put(5, getIncreaseProduct());
        submenus.put(6, getDecreaseProduct());
        submenus.put(7, getshowTotalPrice());
        submenus.put(8, new Purchase(this, "purchase panel"));
        submenus.put(9, getViewOrders());
        submenus.put(10, getShowOrder());
        submenus.put(11, getRateProduct());
        submenus.put(12, getViewBalance());
        submenus.put(13, getViewDiscountCodes());
        this.setSubmenus(submenus);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Menu getPersonalInfo() {
        return new Menu(this, "view personal info") {
            @Override
            public void show() {
                manager.viewPersonalInfo(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getEdit() {
        return new Menu(this, "edit fields") {
            @Override
            public void show() {
                String field = scanner.nextLine();
                manager.editField(userName, field);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewProductInCart() {
        return new Menu(this, "view product in cart") {
            @Override
            public void show() {
                manager.viewProductInCart(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getIncreaseProduct() {
        return new Menu(this, "increase one product in cart") {
            @Override
            public void show() {
                String productId = scanner.nextLine();
                manager.increaseProduct(userName, productId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getDecreaseProduct() {
        return new Menu(this, "decrease one product in cart") {
            @Override
            public void show() {
                String productId = scanner.nextLine();
                manager.decreaseProduct(userName, productId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getshowTotalPrice() {
        return new Menu(this, "show total price of cart") {
            @Override
            public void show() {

                manager.showTotalPrice(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewOrders() {
        return new Menu(this, "view orders") {
            @Override
            public void show() {
                manager.viewOrders(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getShowOrder() {
        return new Menu(this, "show order by orderId") {
            @Override
            public void show() {
                String orderId = scanner.nextLine();
                manager.showOrder(userName, orderId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getRateProduct() {
        return new Menu(this, "rate product") {
            @Override
            public void show() {
                String productId = scanner.nextLine();
                int score = scanner.nextInt();
                manager.rateProduct(userName, productId, score);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewBalance() {
        return new Menu(this, "view balance") {
            @Override
            public void show() {
                manager.viewBalance(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewDiscountCodes() {
        return new Menu(this, "view discount Codes") {
            @Override
            public void show() {
                manager.viewBuyerDiscountCodes(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

}
