package View;

import java.util.HashMap;

public class UserPanelMenu extends Menu {
    public UserPanelMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<>();
        submenus.put(1,new LoggedInStatus(this,"LoggedIn"));
        submenus.put(2,new LoggedOutStatus(this,"LoggedOut"));
        status=(LoginStatus)submenus.get(2);
        this.setSubmenus(submenus);
    }

    public void changeStatus() {
       if ( status == (LoginStatus)submenus.get(1))
           status=(LoginStatus)submenus.get(2);
       else
           status=(LoginStatus)submenus.get(1);
    }
    public void show(){}
    public void run(){
        status.execute();
    }
}
