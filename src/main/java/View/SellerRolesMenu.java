package View;

import java.util.Date;
import java.util.HashMap;

public class SellerRolesMenu extends Menu {

    private String userName;

    public SellerRolesMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        //TODO : ADD roles of Seller
        submenus.put(1, getPersonalInfo());
        submenus.put(2, getEdit());
        submenus.put(3, getViewCompanyInfo());
        submenus.put(4, getViewSalesHistory());
        ///handle kardan products
        submenus.put(6, getAddProduct());
        submenus.put(7, getRemoveProduct());
        submenus.put(8, getShowCategories());
        submenus.put(9, getManageOffs());
        submenus.put(10, getViewBalance());
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
                manager.editField(field);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewCompanyInfo() {
        return new Menu(this, "view company information") {
            @Override
            public void show() {

                manager.viewCompanyInfo(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewSalesHistory() {
        return new Menu(this, "view sales history") {
            @Override
            public void show() {
                manager.viewSalesHistory(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

//////TODO : ghesmate porducts bayad zade she


    private Menu getAddProduct() {
        return new Menu(this, "ADD product") {
            @Override
            public void show() {
                manager.addProduct(userName);//baghie field ha byd dade shavad
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getRemoveProduct() {
        return new Menu(this, "remove product by Id") {
            @Override
            public void show() {
                String productId = scanner.nextLine();
                manager.removeProduct(userName, productId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getShowCategories() {
        return new Menu(this, "show all categories") {
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

    private Menu getManageOffs() {
        return new Menu(this, "view offs") {
            @Override
            public void show() {
                manager.viewOffs(userName);
                System.out.println("1 . view off");
                System.out.println("3 . edit off");
                System.out.println("2 . ADD off");
                System.out.println("4 . back");
                String command = scanner.nextLine();
                manager.manageOffs(userName, command);
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
}


