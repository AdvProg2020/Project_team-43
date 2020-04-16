package View;

import java.util.Date;
import java.util.HashMap;

public class SellerRolesMenu extends Menu {

    private String userName;

    public SellerRolesMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<Integer,Menu>();
        //TODO : add roles of Seller
        submenus.put(1, getPersonalInfo());
        submenus.put(2, getEdit());
        submenus.put(3, getViewCompanyInfo());
        submenus.put(4, getViewSalesHistory());
        ///handle kardan products
        submenus.put(6, getAddProduct());
        submenus.put(7, getRemoveProduct());
        submenus.put(8, getShowCategories());
        submenus.put(9, getViewOffs());
        submenus.put(10, getViewOff());
        submenus.put(11, getEditOff());
        submenus.put(12, getAddOff());
        submenus.put(13, getViewBalance());
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
        return new Menu(this, "add product") {
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

    private Menu getViewOffs() {
        return new Menu(this, "view all offs") {
            @Override
            public void show() {

                manager.viewOffs(userName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewOff() {
        return new Menu(this, "view off by Id") {
            @Override
            public void show() {
                String offId = scanner.nextLine();
                manager.viewOff(userName, offId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getAddOff() {
        return new Menu(this, "add off") {
            @Override
            public void show() {
                System.out.print("Off Id : ");
                String offId = scanner.nextLine();
                System.out.print("Start time : ");
                String startTime = scanner.nextLine();
                System.out.print("End time : ");
                String endTime = scanner.nextLine();
                System.out.print("Discount amount : ");
                double discountAmount= scanner.nextDouble();
                manager.addOff(userName, offId, startTime, endTime, discountAmount);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getEditOff() {
        return new Menu(this, "edit off by Id") {
            @Override
            public void show() {
                String offId = scanner.nextLine();
                manager.editOff(userName, offId);
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


