package View;

import model.InvalidCommandException;

import java.util.HashMap;

public class SellerPersonalInfoMenu extends Menu {
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
                System.out.println("edit [field]");
                String field = scanner.nextLine();
                try {
                    System.out.println(sellerProcessor.editSellerField(field));
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }
    @Override
    public void show() {
        sellerProcessor.viewPersonalInfo();
        super.show();
    }
}
