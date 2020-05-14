package View;

import Controller.BossProcessor;
import Controller.BuyerProcessor;
import Controller.Processor;
import Controller.SellerProcessor;
import model.App;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    public static Processor processor = new Processor();
    public static BuyerProcessor buyerProcessor = BuyerProcessor.getInstance();
    public static SellerProcessor sellerProcessor = SellerProcessor.getInstance();
    public static BossProcessor bossProcessor = new BossProcessor();
    String name;
    protected Menu parent;
    protected static Scanner scanner = new Scanner(System.in);
    protected HashMap<Integer, Menu> submenus;

    public Menu(Menu parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public void setSubmenus(HashMap<Integer, Menu> submenus) {
        this.submenus = submenus;
    }

    public void show() {
        System.out.println(name + ":");
        for (Integer menuNum : submenus.keySet()) {
            System.out.println(menuNum + ". " + submenus.get(menuNum).name);
        }
        if (processor.isUserLoggedIn()) {
            System.out.println((submenus.size() + 1) + ". logout");
        } else {
            System.out.println((submenus.size() + 1) + ". login");
        }
        if (this.parent != null)
            System.out.println((submenus.size() + 2) + ". Back");
        else
            System.out.println((submenus.size() + 2) + ". Save And Exit");
    }

    public void run() {
        String command;
        while (true) {
            command = scanner.nextLine();
            if (command.matches("\\d+")) {
                break;
            } else {
                System.out.println("invalid command! please enter a number");
            }
        }
        int input = Integer.parseInt(command);
        if (input <= submenus.size()) {
            submenus.get(input).show();
            submenus.get(input).run();
        } else {
            if (input == submenus.size() + 2) {
                if (this.parent == null) {
                    App.getInstance().close();
                } else {
                    parent.show();
                    parent.run();
                }
            } else {
                if (processor.isUserLoggedIn()) {
                    new Menu(this, "logout") {
                        @Override
                        public void run() {
                            processor.logout();
                            Menu ans = this;
                            Menu menu = this;
                            while (menu != null) {
                                if (menu.name.equalsIgnoreCase("Boss Login To Do List") ||
                                        menu.name.equalsIgnoreCase("Buyer Login To Do List") ||
                                        menu.name.equalsIgnoreCase("Seller Login To Do List")) {
                                    ans = menu;
                                }
                                menu = menu.parent;
                            }
                            ans.parent.show();
                            ans.parent.run();
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
