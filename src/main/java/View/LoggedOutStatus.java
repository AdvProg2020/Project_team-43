package View;

import java.util.HashMap;

public class LoggedOutStatus extends Menu  {
    public LoggedOutStatus(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer,Menu> submenus=new HashMap<Integer, Menu>();
        submenus.put(1,getLoginMenu());
        submenus.put(2,getRegisterMenu());
        this.setSubmenus(submenus);
    }
    private Menu getLoginMenu(){
        return new Menu(this.parent,"Login") {
            @Override
            public void show(){
                //TODO
            }
            @Override
            public void run() {
                //TODO : login
                //TODo : set user in loggedInStatus

                this.parent.show();
                this.parent.run();
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
                //TODO : register
                this.parent.parent.show();
                this.parent.parent.run();
            }
        };
    }

}
