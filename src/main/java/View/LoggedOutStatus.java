package View;

import java.util.HashMap;

public class LoggedOutStatus extends Menu {
    public LoggedOutStatus(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(2, getLoginMenu());
        submenus.put(1, getRegisterMenu());
        this.setSubmenus(submenus);
    }

    private Menu getLoginMenu() {
        return new Menu(this, "Login") {
            @Override
            public void show() {
                //TODO
            }

            @Override
            public void run() {
                System.out.print("username : ");
                String username = scanner.nextLine();
                System.out.print("password : ");
                String password = scanner.nextLine();
                System.out.println(manager.login(username, password));
                this.parent.parent.submenus.get(1).show();
                this.parent.parent.submenus.get(1).run();
            }
        };
    }

    private Menu getRegisterMenu() {
        return new Menu(this, "Register") {
            @Override
            public void show() {
                System.out.println("register : " + "\n" + "create account [type] [username]");
            }

            @Override
            public void run() {
                //TODO : register
                String command = scanner.nextLine();
                System.out.println(manager.registerUser(command));
                this.parent.parent.show();
                this.parent.parent.run();
            }
        };
    }

    public void show() {
        System.out.println(this.name + ":");
        for (Integer menuNum : submenus.keySet()) {
            System.out.println(menuNum + ". " + submenus.get(menuNum).name);
        }
        if (this.parent != null)
            System.out.println((submenus.size() + 1) + ". Back");
        else
            System.out.println((submenus.size() + 1) + ". Exit");
    }

    public void run() {
        int input = Integer.parseInt(scanner.nextLine());
        if (input <= submenus.size()) {
            submenus.get(input).show();
            submenus.get(input).run();
        } else {
            if (this.parent == null)
                System.exit(0);
            else {
                parent.show();
                parent.run();
            }

        }

    }
}
