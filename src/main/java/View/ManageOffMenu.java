package View;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageOffMenu extends Menu {

    public ManageOffMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewOff());
        submenus.put(2, getEditOff());
        submenus.put(3, getAddOff());
        this.setSubmenus(submenus);
    }

    private Menu getViewOff() {
        return new Menu(this, "View Off Info") {
            @Override
            public void show() {
                System.out.println("Please enter off id");
                sellerProcessor.viewOff(scanner.nextLine());
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getEditOff() {
        return new Menu(this, "Edit Off") {
            @Override
            public void show() {
                System.out.println("Please Enter Off Id");
                sellerProcessor.editOff(scanner.nextLine());
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getAddOff() {
        return new Menu(this, "Add Off") {
            @Override
            public void show() {
                System.out.println("Please Enter Off Start Time:");
                String startTime = scanner.nextLine();
                System.out.println("Off End Time:");
                String endTime = scanner.nextLine();
                System.out.println("Discount Amount(Must be integer):");
                Double discountAmount = Double.parseDouble(scanner.nextLine());
                ArrayList<String> productIds = getProductIds();
                sellerProcessor.addOff(startTime, endTime, discountAmount, productIds);
                System.out.println("Off sent to manger to confirm");
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private ArrayList<String> getProductIds() {
        System.out.println("Now please enter productIDs.At the end type 'finnish'");
        String productId = scanner.nextLine();
        ArrayList<String> productIds = new ArrayList<>();
        while (!productId.equalsIgnoreCase("finnish")) {
            productIds.add(productId);
            productId = scanner.nextLine();
        }
        return productIds;
    }
}