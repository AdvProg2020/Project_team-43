package View;

import java.util.HashMap;
public class LoggedInStatus extends Menu implements LoginStatus {
    public LoggedInStatus(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<>();
        submenus.put(1,getLogoutMenu());
        this.setSubmenus(submenus);
    }
    private Menu getLogoutMenu(){
        return new Menu(this.parent,"logoutMenu") {
            @Override
            public void show(){

            }
            @Override
            public void run() {
                ((UserPanelMenu)this.parent).changeStatus();
                this.parent.parent.show();
                this.parent.parent.run();
            }
        };
    }
    public void execute(){
        this.show();
        this.run();
    }
}
