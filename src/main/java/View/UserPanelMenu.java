package View;

import java.util.HashMap;

public class UserPanelMenu extends Menu {
    public UserPanelMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<Integer, Menu>();
        submenus.put(1,new LoggedInStatus(this,"LoggedIn"));
        submenus.put(2,new LoggedOutStatus(this,"LoggedOut"));
        submenus.put(2, new ProductsPanel(this, "ProductsPanel"));
        submenus.put(3, new OffPanel(this, "OffPanel"));

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
