package View;

import model.User;

import java.util.HashMap;
public class LoggedInStatus extends Menu implements LoginStatus {
    private User user;
    public LoggedInStatus(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus= new HashMap<Integer,Menu>();
        submenus.put(1,new BossRolesMenu(this,"Boss Login To Do List"));
        submenus.put(2,new BuyerRolesMenu(this,"Buyer Login To Do List"));
        submenus.put(3,new SellerRolesMenu(this,"Seller Login To Do List"));
        this.setSubmenus(submenus);
    }
    public void show(){
        submenus.get(this.user.getUserType()).show();
    }
    public void run(){
        submenus.get(this.user.getUserType()).run();
    }
    public void execute(){
        this.show();
        this.run();
    }
}
