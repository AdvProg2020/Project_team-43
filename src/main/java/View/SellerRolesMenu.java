package View;


import java.util.HashMap;

public class SellerRolesMenu extends Menu {

    public SellerRolesMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new SellerPersonalInfoMenu(this, "Personal Info Menu"));
        submenus.put(2, getViewCompanyInfo());
        submenus.put(3, getViewSalesHistory());
        submenus.put(4, new SellerManageProductMenu(this, "Manage Product Menu"));
        submenus.put(5, new SellerAddProductMenu(this, "Add Product Menu"));
        submenus.put(6, getRemoveProduct());
        submenus.put(7, getShowCategories());
        submenus.put(8, new SellerManageOffMenu(this, "Manage Off Menu"));
        submenus.put(9, getViewBalance());
        this.setSubmenus(submenus);
    }

    private Menu getViewCompanyInfo() {
        return new Menu(this, "View company information") {
            @Override
            public void show() {
                sellerProcessor.viewCompanyInfo();
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
                sellerProcessor.viewSalesHistory();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getRemoveProduct() {
        return new Menu(this, "Remove product by Id") {
            @Override
            public void show() {
                String productId = scanner.nextLine();
                sellerProcessor.removeProduct(productId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getShowCategories() {
        return new Menu(this, "Show all categories") {
            @Override
            public void show() {
                sellerProcessor.viewCategories();
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
                sellerProcessor.viewBalance();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

}


