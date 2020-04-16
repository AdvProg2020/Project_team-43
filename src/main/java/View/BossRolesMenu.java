package View;

import java.util.HashMap;

public class BossRolesMenu extends Menu {
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BossRolesMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getPersonalInfo());
        submenus.put(2, getEdit());
        submenus.put(3, getManageUsers());
        submenus.put(4, getManageAllProducts());
        submenus.put(5, getCreateDiscountCode());
        submenus.put(6, getViewDiscountCodes());
        submenus.put(7, getManageRequests());
        submenus.put(8, getManageCategories());
        this.setSubmenus(submenus);

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

    private Menu getManageUsers() {
        return new Menu(this, "manage users") {
            @Override
            public void show() {
                System.out.println("1 . view user");
                System.out.println("2 . delete user");
                System.out.println("3 . create manager profile");
                System.out.println("4 . back");
                String command = scanner.nextLine();
                manager.manageUsers(command);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getManageAllProducts() {
        return new Menu(this, "manage all products") {
            @Override
            public void show() {
                System.out.println("1 . remove products");
                System.out.println("2 . back");
                String command = scanner.nextLine();
                manager.manageAllProducts(command);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getCreateDiscountCode() {
        return new Menu(this, "create discount code") {
            @Override
            public void show() {
                manager.createDiscountCode();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewDiscountCodes() {
        return new Menu(this, "view discount codes") {
            @Override
            public void show() {
                manager.viewBossDiscountCodes();
                System.out.println("1 . view discount code");
                System.out.println("2 . edit discount code");
                System.out.println("3 . remove discount code");
                System.out.println("4 . back");
                String command = scanner.nextLine();
                manager.manageDiscountCodes(command);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getManageRequests() {
        return new Menu(this, "manage requests") {
            @Override
            public void show() {
                manager.viewRequests();
                System.out.println("1 . details of request");
                System.out.println("2 . accept request");
                System.out.println("3 . decline request");
                System.out.println("4 . back");
                String command = scanner.nextLine();
                manager.manageRequests(command);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getManageCategories() {
        return new Menu(this, "manage categories") {
            @Override
            public void show() {
                manager.viewCategories();
                System.out.println("1 . edit category");
                System.out.println("2 . ADD category");
                System.out.println("3 . remove category");
                System.out.println("4 . back");
                String command = scanner.nextLine();
                manager.manageCategories(command);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }
}