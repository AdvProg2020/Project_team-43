package View;

import model.Category;
import model.InvalidCommandException;

import java.util.HashMap;

public class SellerAddProductMenu extends Menu {
    public SellerAddProductMenu(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getAddExistingProduct());
        submenus.put(2, getAddNewProduct());
        this.setSubmenus(submenus);
    }

    private Menu getAddNewProduct() {
        return new Menu(this, "Add new product") {
            @Override
            public void show() {
                SellerShowAndCatch.getInstance().getProductsInformation(sellerProcessor);
                /*
                System.out.println("Please enter product information\nname : ");
                String name = scanner.nextLine();
                System.out.print("company : ");
                String company = scanner.nextLine();
                System.out.print("category : ");
                String category;
                while (true) {
                    category = scanner.nextLine();
                    if (Category.hasCategoryWithName(category))
                        break;
                    System.out.println("category name is invalid");
                }
                System.out.print("price : ");
                String price = scanner.nextLine();
                System.out.print("number : ");
                String number = scanner.nextLine();
                try {
                    System.out.println(sellerProcessor.addNewProduct(name, company, category, price, number));
                } catch (NullPointerException | InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }*/
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getAddExistingProduct() {
        return new Menu(this, "Add existing product") {
            @Override
            public void show() {

                System.out.println("Please enter product id");
                String id = scanner.nextLine();
                System.out.println("amount : ");
                String number = scanner.nextLine();
                try {
                    System.out.println(sellerProcessor.addExistingProduct(id, number));
                } catch (NullPointerException | InvalidCommandException e) {
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
}
