package View.console;

import model.InvalidCommandException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;

public class BossRolesMenu extends Menu {
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Scanner scannerView = new Scanner(System.in);

    public BossRolesMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getPersonalInfo());
        submenus.put(2, getEdit());
        submenus.put(3, getManageUsers());
        submenus.put(4, getManageAllProducts());
        submenus.put(5, getCreateCodedDiscount());
        submenus.put(6, getViewCodedDiscounts());
        submenus.put(7, getAddCompany());
        submenus.put(8, getManageRequests());
        submenus.put(9, getManageCategories());
        submenus.put(10, getComments());
        this.setSubmenus(submenus);

    }

    private Menu getComments() {
        return new Menu(this, "comments menu") {
            @Override
            public void show() {
                processor.showInQueueComments();
                System.out.println("accept comment [comment number]");
                System.out.println("decline comment [comment number]");
                System.out.println("back");
                String command = scanner.nextLine();
                bossProcessor.manageComments(command);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getPersonalInfo() {
        return new Menu(this, "view personal info") {
            @Override
            public void show() {
                processor.viewPersonalInfo();
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
                System.out.println("FIELDS : ");
                System.out.println("1 . first name");
                System.out.println("2 . last name");
                System.out.println("3 . email");
                System.out.println("4 . phone number");
                System.out.println("5 . password");
                System.out.println("edit [field]");
                String field = scannerView.nextLine();
                field = field.trim();
                if (!checkField(field)) return;
                System.out.println(field + " to :");
                String changeField = scannerView.nextLine();
                changeField = changeField.trim();
                try {
                    bossProcessor.editField(field, changeField);
                    System.out.println("changed successfully");
                } catch (InvalidCommandException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }

            public boolean checkField(String field) {
                if (field.equalsIgnoreCase("edit first name") || field.equalsIgnoreCase("edit last name") || field.equalsIgnoreCase("edit email") || field.equalsIgnoreCase("edit phone number") || field.equalsIgnoreCase("edit password")) {
                    return true;
                }
                System.out.println("invalid command");
                return false;
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
                bossProcessor.getBossViewManager().viewAllUsers();
                System.out.println("1 . view user [username]");
                System.out.println("2 . delete user [username]");
                System.out.println("3 . create manager profile");
                System.out.println("4 . back");
                String command = scannerView.nextLine();
                try {
                    bossProcessor.manageUsers(command);
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

    private Menu getManageAllProducts() {
        return new Menu(this, "manage all products") {
            @Override
            public void show() {
                System.out.println("1 . remove product [productId]");
                System.out.println("2 . back");
                String command = scannerView.nextLine();
                try {
                    bossProcessor.manageAllProducts(command);
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

    private Menu getCreateCodedDiscount() {
        return new Menu(this, "create coded discount") {
            @Override
            public void show() {
                try {
                    bossProcessor.processCreateCodedDiscount();
                    System.out.println("coded discount created");
                } catch (InvalidCommandException | ParseException e) {
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

    private Menu getViewCodedDiscounts() {
        return new Menu(this, "view discount codes") {
            @Override
            public void show() {
                bossProcessor.getBossViewManager().viewCodedDiscounts();
                System.out.println("1 . view discount code [code]");
                System.out.println("2 . edit discount code [code]");
                System.out.println("3 . remove discount code [code]");
                System.out.println("4 . back");
                String command = scannerView.nextLine();
                try {
                    bossProcessor.manageCodedDiscounts(command);
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

    private Menu getManageRequests() {
        return new Menu(this, "manage requests") {
            @Override
            public void show() {
                bossProcessor.getBossViewManager().viewAllRequests();
                System.out.println("1 . details [requestId]");
                System.out.println("2 . accept request [requestId]");
                System.out.println("3 . decline request [requestId]");
                System.out.println("4 . back");
                String command = scannerView.nextLine();
                try {
                    bossProcessor.manageRequests(command);
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

    private Menu getAddCompany() {
        return new Menu(this, "add company") {
            @Override
            public void show() {
                System.out.println("1. add company [name]");
                System.out.println("2. back");
                String command = scanner.nextLine();
                try {
                    bossProcessor.addCompany(command.trim());
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void run() {
                parent.show();
                parent.run();
            }
        };
    }

    private Menu getManageCategories() {
        return new Menu(this, "manage categories") {
            @Override
            public void show() {
                bossProcessor.getBossViewManager().viewAllCategories();
                System.out.println("1 . edit category [category name]");
                System.out.println("2 . add category [category name]");
                System.out.println("3 . remove category [category name]");
                System.out.println("4 . back");
                String command = scannerView.nextLine();
                try {
                    bossProcessor.manageCategories(command);
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
}