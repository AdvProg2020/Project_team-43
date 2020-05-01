package View;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class BossView {
    public Scanner scanner;

    public BossView() {
        scanner = Menu.scanner;
    }

    public void viewAllUsers() {
        for (User user : User.allUsers) {
            System.out.println(user.getUsername());
        }
    }

    public void viewUser(User user) {
        System.out.println(user);
    }

    public void getManagerInfo(ArrayList<String> managerInfo) {
        System.out.print("user name : ");
        String userName = scanner.nextLine();
        managerInfo.add(userName);
        System.out.print("first name : ");
        String firstName = scanner.nextLine();
        managerInfo.add(firstName);
        System.out.print("last name : ");
        String lastName = scanner.nextLine();
        managerInfo.add(lastName);
        System.out.print("email : ");
        String email = scanner.nextLine();
        managerInfo.add(email);
        System.out.print("phone number : ");
        String phoneNumber = scanner.nextLine();
        managerInfo.add(phoneNumber);
        System.out.print("password : ");
        String password = scanner.nextLine();
        managerInfo.add(password);
    }

    public void getCodedDiscountInfo(ArrayList<String> discountCodedInfo) {
        System.out.print("Discount code : ");
        String discountCode = scanner.nextLine();
        discountCodedInfo.add(discountCode);
        System.out.print("Start time : ");
        String startTime = scanner.nextLine();
        discountCodedInfo.add(startTime);
        System.out.print("End time : ");
        String endTime = scanner.nextLine();
        discountCodedInfo.add(endTime);
        System.out.print("Discount amount : ");
        String discountAmount = scanner.nextLine();
        discountCodedInfo.add(discountAmount);
        System.out.print("repeat : ");
        String repeat = scanner.nextLine();
        discountCodedInfo.add(repeat);
    }

    public void viewCodedDiscounts() {
        int counter = 1;
        for (CodedDiscount codedDiscount : CodedDiscount.allCodedDiscount) {
            System.out.println(counter + " . ");
            System.out.println(codedDiscount);
            counter += 1;
        }

    }

    public void viewCodedDiscount(CodedDiscount codedDiscount) {
        System.out.println(codedDiscount);
    }

    public void getEditCodedDiscountInfo(ArrayList<String> codedDiscountInfo) {
        System.out.println("FIELDS : ");
        System.out.println("1 . discount code");
        System.out.println("2 . start time");
        System.out.println("3 . end time");
        System.out.println("4 . discount amount");
        System.out.println("5 . remaining time");
        while (true) {
            System.out.println("enter the field's name or enter finish: ");
            String command = scanner.nextLine();
            command = command.trim();
            if (command.equalsIgnoreCase("finish")) {
                break;
            } else {
                System.out.println("change to : ");
                String changeTo = scanner.nextLine();
                codedDiscountInfo.add(command);
                codedDiscountInfo.add(changeTo);
            }
        }
    }

    public void viewAllRequests() {
        int counter = 1;
        for (Request request : Manager.allRequest) {
            System.out.print(counter + " . ");
            System.out.println(request);
            counter += 1;
        }
    }

    public void viewRequestDetails(Request request) {
        System.out.println(request);
    }

    public void viewAllCategories() {
        int counter = 1;
        for (Category category : Category.getAllCategories()) {
            System.out.print(counter + " . ");
            System.out.print(category);
        }
    }

    public void getEditCategoryField() {
        //TODO : fill

    }

    public ArrayList<String> getCategoryInfo(ArrayList<String> categoryInfo) {
        ArrayList<String> features = new ArrayList<>();
        System.out.print("category name : ");
        String categoryName = scanner.nextLine();
        categoryInfo.add(categoryName);
        System.out.println("features : [feature's name/finish]");
        while (true) {
            String command = scanner.nextLine();
            command = command.trim();
            if (command.equalsIgnoreCase("finish")) {
                break;
            }
            features.add(command);
        }
        System.out.println("does it have a super category ? [yes/no]");
        String command = scanner.nextLine();
        if (command.equalsIgnoreCase("yes")) {
            System.out.print("super category's name : ");
            String superCategoryName = scanner.next();
            categoryInfo.add(superCategoryName);
        }
        return features;
    }

}