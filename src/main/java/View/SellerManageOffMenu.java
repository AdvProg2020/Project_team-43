package View;

import Controller.Processor;
import model.InvalidCommandException;
import model.Off;
import model.Seller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerManageOffMenu extends Menu {

    public SellerManageOffMenu(Menu parent, String name) {
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
                for (Off off : ((Seller) Processor.user).getOffs()) {
                    System.out.println(off.getOffId());
                }
                System.out.println("Please enter off id");
                try {
                    sellerProcessor.viewOff(scanner.nextLine());
                } catch (NullPointerException e){
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

    private Menu getEditOff() {
        return new Menu(this, "Edit Off") {
            @Override
            public void show() {
                System.out.println("Please Enter Off Id");
                String id = scanner.nextLine();
                System.out.println("edit [field]");
                String field = scanner.nextLine();
                try {
                    System.out.println(sellerProcessor.editOff(id, field));
                } catch (Exception e) {
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

    private Menu getAddOff() {
        return new Menu(this, "Add Off") {
            @Override
            public void show() {
                System.out.println("Please Enter Off Start Time [dd/MM/yyyy]:");
                String startTime = scanner.nextLine();
                System.out.println("Off End Time [dd/MM/yyyy]:");
                String endTime = scanner.nextLine();
                System.out.println("Discount Amount(Must be integer):");
                Double discountAmount = Double.parseDouble(scanner.nextLine());
                ArrayList<String> productIds = getProductIds();
                try {
                    sellerProcessor.addOff(startTime, endTime, discountAmount, productIds);
                    System.out.println("Off sent to manger to confirm");
                } catch (ParseException |InvalidCommandException e){
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

    private ArrayList<String> getProductIds() {
        System.out.println("Now please enter productIDs. At the end type 'finish'");
        String productId = scanner.nextLine();
        ArrayList<String> productIds = new ArrayList<>();
        while (!productId.equalsIgnoreCase("finish")) {
            if(sellerProcessor.checkProduct(productId)) {
                productIds.add(productId);
            }
            productId = scanner.nextLine();
        }
        return productIds;
    }

    @Override
    public void show() {
        sellerProcessor.viewOffs();
        super.show();
    }
}
