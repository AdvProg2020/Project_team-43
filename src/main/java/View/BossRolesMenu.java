package View;

import model.User;

import java.util.HashMap;

public class BossRolesMenu extends Menu {
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BossRolesMenu(Menu parent, String name) {
        super(parent, name);
        submenus.put(1, getPersonalInfo());
        submenus.put(2, getEdit());
        submenus.put(3, getViewUser());
        submenus.put(4, getDeleteUser());
        submenus.put(5, getCreateManagerProfile());
        submenus.put(6, getRemoveProduct());
        submenus.put(7, getCreateDiscountCode());
        submenus.put(8, getViewDiscountCodes());
        submenus.put(9, getViewDiscountCode());
        submenus.put(10, getEditDiscountCode());
        submenus.put(11, getRemoveDiscountCodes());
        submenus.put(12, getViewRequests());
        submenus.put(13, getRequestDetail());
        submenus.put(14, getAcceptRequest());
        submenus.put(15, getDeclineRequest());
        submenus.put(16, getViewCategories());
        submenus.put(17, getEditCategory());
        submenus.put(18, getAddCategory());
        submenus.put(19, getRemoveCategory());

    }

    private Menu getPersonalInfo() {
        return new Menu(this.parent, "view personal info") {
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
        return new Menu(this.parent, "edit fields") {
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

    private Menu getViewUser() {
        return new Menu(this.parent, "view user") {
            @Override
            public void show() {
                String userNameToBeView = scanner.nextLine();
                manager.viewUser(userNameToBeView);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getDeleteUser() {
        return new Menu(this.parent, "delete user") {
            @Override
            public void show() {
                String userNameToBeDelete = scanner.nextLine();
                manager.deleteUser(userNameToBeDelete);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getCreateManagerProfile() {
        return new Menu(this.parent, "create manager profile") {
            @Override
            public void show() {
                manager.createManagerProfile();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getRemoveProduct() {
        return new Menu(this.parent, "remove product") {
            @Override
            public void show() {
                String productId = scanner.nextLine();
                manager.removeProduct(productId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getCreateDiscountCode() {
        return new Menu(this.parent, "create discount code") {
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
        return new Menu(this.parent, "view all discount codes") {
            @Override
            public void show() {
                manager.viewBossDiscountCodes();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewDiscountCode() {
        return new Menu(this.parent, "view discount code by Id") {
            @Override
            public void show() {
                String discountCode = scanner.nextLine();
                manager.viewDiscountCode(discountCode);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getEditDiscountCode() {
        return new Menu(this.parent, "edit discount code by Id") {
            @Override
            public void show() {
                String discountCode = scanner.nextLine();
                manager.editDiscountCode(discountCode);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getRemoveDiscountCodes() {
        return new Menu(this.parent, "remove discount code") {
            @Override
            public void show() {
                String discountCode = scanner.nextLine();
                manager.removeDiscountCode(discountCode);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewRequests() {
        return new Menu(this.parent, "view all requests") {
            @Override
            public void show() {
                manager.viewRequests();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getRequestDetail() {
        return new Menu(this.parent, "view request by Id") {
            @Override
            public void show() {
                String requestId = scanner.nextLine();
                manager.viewRequestDetails(requestId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getAcceptRequest() {
        return new Menu(this.parent, "accept request by Id") {
            @Override
            public void show() {
                String requestId = scanner.nextLine();
                manager.acceptRequest(requestId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getDeclineRequest() {
        return new Menu(this.parent, "decline request by Id") {
            @Override
            public void show() {
                String requestId = scanner.nextLine();
                manager.declineRequest(requestId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getViewCategories() {
        return new Menu(this.parent, "view all categories") {
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

    private Menu getEditCategory() {
        return new Menu(this.parent, "edit category by name") {
            @Override
            public void show() {
                String categoryName = scanner.nextLine();
                manager.editCategory(categoryName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getAddCategory() {
        return new Menu(this.parent, "add category") {
            @Override
            public void show() {
                //field haye marboote ro begirim
                manager.addCategory();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getRemoveCategory() {
        return new Menu(this.parent, "remove category by name") {
            @Override
            public void show() {
                String categoryName = scanner.nextLine();
                manager.removeCategory(categoryName);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }


}