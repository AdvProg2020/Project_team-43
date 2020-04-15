package View;

import model.User;
import model.UserType;

import java.util.HashMap;
public class LoggedInStatus extends Menu {

    public LoggedInStatus(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus= new HashMap<Integer,Menu>();
        submenus.put(1,new BossRolesMenu(this,"Boss Login To Do List"));
        submenus.put(2,new BuyerRolesMenu(this,"Buyer Login To Do List"));
        submenus.put(3,new SellerRolesMenu(this,"Seller Login To Do List"));
        this.setSubmenus(submenus);
    }
    public void show(){
        if (manager.getUser().getUserType()== UserType.MANAGER) {
            submenus.get(1).show();
        }
        else if (manager.getUser().getUserType()== UserType.BUYER){
            submenus.get(2).show();
        }
        else {
            submenus.get(3).show();
        }
    }
    public void run(){
        if (manager.getUser().getUserType()== UserType.MANAGER) {
            submenus.get(1).run();
        }
        else if (manager.getUser().getUserType()== UserType.BUYER){
            submenus.get(2).run();
        }
        else {
            submenus.get(3).run();
        }
    }
}
