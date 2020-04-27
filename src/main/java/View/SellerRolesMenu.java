package View;

import java.util.HashMap;

public class SellerRolesMenu extends Menu {
    private String userName;

    public SellerRolesMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, new SellerPersonalInfoMenu(this, "Personal Info Menu"));
        submenus.put(2, getViewCompanyInfo());
        submenus.put(3, getViewSalesHistory());
        submenus.put(4, new ManageProductMenu(this, "Manage Product Menu"));
        submenus.put(5, getAddProduct());
        submenus.put(6, getRemoveProduct());
        submenus.put(7, getShowCategories());
        submenus.put(8, getManageOffs());
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

    private Menu getAddProduct() {
        return new Menu(this, "ADD product") {
            @Override
            public void show() {
                sellerProcessor.addProduct();
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
        return new Menu(this, "show all categories") {
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

    private Menu getManageOffs() {
        return new Menu(this, "view offs") {
            @Override
            public void show() {
                sellerProcessor.viewOffs(userName);
                System.out.println("1 . view off");
                System.out.println("3 . edit off");
                System.out.println("2 . ADD off");
                System.out.println("4 . back");
                String command = scanner.nextLine();
                sellerProcessor.manageOffs(userName, command);
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
                sellerProcessor.viewBalance(userName);
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
        int input = Integer.parseInt(scanner.nextLine());
        if (input == 1) {
            sellerProcessor.viewPersonalInfo();
        }
        if (input == 4) {
            sellerProcessor.viewProductList();
        }
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
                if (processor.isUserLoggedIn()) {
                    new Menu(this, "logout") {
                        @Override
                        public void run() {
                            //TODO : Logout
                            this.parent.show();
                            this.parent.run();
                        }
                    }.run();
                } else {
                    new Menu(this, "login") {
                        @Override
                        public void run() {
                            //TODO : Login
                            System.out.print("username : ");
                            String username = scanner.nextLine();
                            System.out.print("password : ");
                            String password = scanner.nextLine();
                            System.out.println(processor.login(username, password));
                            this.parent.show();
                            this.parent.run();
                        }
                    }.run();
                }
            }
        }
    }

}


