package View;

import java.util.HashMap;

public class MainMenu extends Menu{
    public MainMenu( String name) {
        super(null, name);
        HashMap <Integer,Menu> submenus=new HashMap<>();
        submenus.put(1,new UserPanelMenu(this,"UserPanel"));
        this.setSubmenus(submenus);
    }
}
