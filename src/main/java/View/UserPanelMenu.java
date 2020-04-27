package View;

import java.util.HashMap;

public class UserPanelMenu extends Menu {
    public UserPanelMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<Integer, Menu>();
        submenus.put(1,new LoggedInStatus(this.parent,"LoggedIn"));
        submenus.put(2,new LoggedOutStatus(this.parent,"LoggedOut"));
        this.setSubmenus(submenus);
    }

    public void show(){}
    public void run() {
        if (processor.isUserLoggedIn()){
            this.submenus.get(1).show();
            this.submenus.get(1).run();
        }
        else {
            this.submenus.get(2).show();
            this.submenus.get(2).run();
        }
    }
}
