package View;

import Controller.Processor;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    public static Processor manager = new Processor();
    String name;
    protected Menu parent;
    protected static Scanner scanner;
    protected HashMap<Integer, Menu> submenus;
    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public Menu(Menu parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public void setSubmenus(HashMap<Integer, Menu> submenus) {
        this.submenus = submenus;
    }

    public void show() {
        System.out.println(this.name + ":");
        for (Integer menuNum : submenus.keySet()) {
            System.out.println(menuNum + ". " + submenus.get(menuNum).name);
        }
        if (manager.isUserLoggedIn()){//////////////////////////////////////////////chi shode in error dare??
            System.out.println((submenus.size() + 1)+". logout");
        }
        else {
            System.out.println((submenus.size() + 1)+". login");
        }
        if (this.parent != null)
            System.out.println((submenus.size() + 2) + ". Back");
        else
            System.out.println((submenus.size() + 2) + ". Exit");
    }

    public void run() {
        int input = Integer.parseInt(scanner.nextLine());
        if (input <= submenus.size()) {
            submenus.get(input).show();
            submenus.get(input).run();
        } else {
            if (input==submenus.size()+2) {
                if (this.parent == null)
                    System.exit(0);
                else {
                    parent.show();
                    parent.run();
                }
            }
            else {
                if (manager.isUserLoggedIn()){
                    new Menu(this,"logout"){
                        @Override
                        public void run() {
                            //TODO : Logout
                            this.parent.show();
                            this.parent.run();
                        }
                    }.run();
                }
                else{
                    new Menu(this,"login"){
                        @Override
                        public void run() {
                            //TODO : Login
                            this.parent.show();
                            this.parent.run();
                        }
                    }.run();
                }
            }
        }
    }
}
