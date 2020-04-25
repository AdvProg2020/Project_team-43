package View;

import java.util.HashMap;

public class SellerPersonalInfoMenu extends Menu{
    public SellerPersonalInfoMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getEdit());
        this.setSubmenus(submenus);
    }

    private Menu getEdit() {
        return new Menu(this, "Edit fields") {
            @Override
            public void show() {
                System.out.println("Please enter the fields which you want to edit");
                String field = scanner.nextLine();
                sellerProcessor.editField(field);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }
}
