package View;

import java.util.HashMap;

public class BuyerRolesMenu extends Menu {
    private String userName;

    public BuyerRolesMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getPersonalInfo());
        submenus.put(2, new ManageCartMenu(this, "Cart"));
        submenus.put(3, getManageOrders());
        submenus.put(4, getViewBalance());
        submenus.put(5, getViewDiscountCodes());
        this.setSubmenus(submenus);
    }

    private Menu getPersonalInfo() {
        return new Menu(this, "view personal info") {
            @Override
            public void show() {
                processor.viewPersonalInfo();
                System.out.println("1 . edit [field]");
                System.out.println("2 . back");
                String command = scanner.nextLine();
                System.out.println(buyerProcessor.editBuyerField(command));
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
                buyerProcessor.editField(field);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getManageCart() {
        return new Menu(this, "view product in cart") {
            @Override
            public void show() {

                processor.viewProductInCart(userName);
                System.out.println("1 . show products");
                System.out.println("2 . view product panel");
                System.out.println("3 . increase product");
                System.out.println("4 . decrease product");
                System.out.println("5 . show total price");
                System.out.println("6 . back");
                String command = scanner.nextLine();
                processor.manageCart(userName, command);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getManageOrders() {
        return new Menu(this, "view orders") {
            @Override
            public void show() {
                buyerProcessor.viewOrders();
                System.out.println("1 . show Order");
                System.out.println("2 . rate product");
                String command = scanner.nextLine();
                buyerProcessor.manageOrders(command);
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
                buyerProcessor.viewBalance();
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
                buyerProcessor.viewBuyerDiscountCodes();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    public void run() {
        int input = Integer.parseInt(scanner.nextLine());
        if (input <= submenus.size()) {
            submenus.get(input).show();
            submenus.get(input).run();
        } else {
            if (input == submenus.size() + 2) {
                if (this.parent == null)
                    System.exit(0);
                else {
                    parent.show();
                    parent.run();
                }
            } else {
                new Menu(this, "logout") {
                    @Override
                    public void run() {
                        //TODO : Logout
                        processor.logout();
                        this.parent.parent.show();
                        this.parent.parent.run();
                    }
                }.run();
            }
        }
    }
}

