package View;

import model.Off;
import model.Product;

import java.util.ArrayList;
import java.util.Scanner;

public class ShowAndCatch {
    private static ShowAndCatch ourInstance = new ShowAndCatch();
    private Scanner scanner = Menu.scanner;

    public static ShowAndCatch getInstance() {
        return ourInstance;
    }

    private ShowAndCatch() {
    }

    public void getOffProducts(ArrayList<Product> products) {
        while (true) {
            //fill products
        }
    }

    public void showOffs(ArrayList<Off> offs) {
        for (int i = 1; i <= offs.size(); i++) {
            System.out.println(i + " : " + "\n" + "Off Id : " + offs.get(i - 1).getOffId());
            System.out.println("Seller user name : " + offs.get(i - 1).getSellerName());
            System.out.println("Discount amount : " + offs.get(i - 1).getDiscountAmount());
            System.out.println("Date : " + offs.get(i - 1).getStartTime() + " - " + offs.get(i - 1).getEndTime());
            System.out.println("Products : ");
            for (Product product : offs.get(i - 1).getProducts()) {
                System.out.println(product.getName());
            }

        }
    }

    public void showOff(Off off) {
        System.out.println("Off Id : " + off.getOffId());
        System.out.println("Seller user name : " + off.getSellerName());
        System.out.println("Discount amount : " + off.getDiscountAmount());
        System.out.println("Date : " + off.getStartTime() + " - " + off.getEndTime());
        System.out.println("Products : ");
        for (Product product : off.getProducts()) {
            System.out.println(product.getName());
        }
    }

    public void getProductInfo(ArrayList<String> productInfo){
        System.out.print("Id : ");
        String productId = scanner.nextLine();
        productInfo.add(productId);
        System.out.print("name : ");
        String name = scanner.nextLine();
        productInfo.add(name);
        System.out.print("company : ");
        String company = scanner.nextLine();
        productInfo.add(company);
        System.out.print("category : ");
        String category = scanner.nextLine();
        productInfo.add(category);
        System.out.print("price : ");
        String price= scanner.nextLine();
        productInfo.add(price);
    }
}
