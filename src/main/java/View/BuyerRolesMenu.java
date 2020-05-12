package View;

import model.InvalidCommandException;

import java.util.HashMap;

public class BuyerRolesMenu extends Menu {

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
                System.out.println("edit [field]");
                System.out.println("back");
                String command = scanner.nextLine();
                try {
                    System.out.println(buyerProcessor.editBuyerField(command));
                } catch (InvalidCommandException e) {
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

    private Menu getManageOrders() {
        return new Menu(this, "view orders") {
            @Override
            public void show() {
                buyerProcessor.viewOrders();
                System.out.println("show Order");
                System.out.println("rate product");
                System.out.println("back");
                String command = scanner.nextLine();
                try {
                    buyerProcessor.manageOrders(command);
                } catch (InvalidCommandException | NullPointerException e) {
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

    @Override
    public void run() {
        String command="";
        while (true) {
            command = scanner.nextLine();
            if(command.matches("\\d+")){
                break;
            } else{
                System.out.println("invalid command! please enter a number");
            }
        }
        int input = Integer.parseInt(command);

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

