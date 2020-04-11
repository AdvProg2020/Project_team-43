package View;

import java.util.HashMap;

public class LoggedOutStatus extends Menu implements LoginStatus {
    public LoggedOutStatus(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<>();
        submenus.put(1,getLoginMenu());
        submenus.put(2,getRegisterMenu());
        this.setSubmenus(submenus);
    }
    private Menu getLoginMenu(){
        return new Menu(this.parent,"login") {
            @Override
            public void show(){

            }
            @Override
            public void run() {
                //login
                ((UserPanelMenu)this.parent).changeStatus();
                this.parent.parent.show();
                this.parent.parent.run();
            }
        };
    }
    private Menu getRegisterMenu(){
        return new Menu(this.parent,"Register") {
            @Override
            public void show(){

            }
            @Override
            public void run() {
                //register
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
