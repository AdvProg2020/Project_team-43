package View.console;

import model.UserType;

import java.util.HashMap;

public class LoggedInStatus extends Menu {

    public LoggedInStatus(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, new BossRolesMenu(this.parent, "Boss Login To Do List"));
        submenus.put(2, new BuyerRolesMenu(this.parent, "Buyer Login To Do List"));
        submenus.put(3, new SellerRolesMenu(this.parent, "Seller Login To Do List"));
        this.setSubmenus(submenus);
    }

    public void show() {
        if (processor.getUser().getUserType() == UserType.MANAGER) {
            submenus.get(1).show();
        } else if (processor.getUser().getUserType() == UserType.BUYER) {
            submenus.get(2).show();
        } else {
            submenus.get(3).show();
        }
    }

    public void run() {
        if (processor.getUser().getUserType() == UserType.MANAGER) {
            submenus.get(1).run();
        } else if (processor.getUser().getUserType() == UserType.BUYER) {
            submenus.get(2).run();
        } else {
            submenus.get(3).run();
        }
    }
}
