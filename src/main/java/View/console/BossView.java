package View.console;

import model.*;
import model.request.Request;

import java.util.ArrayList;
import java.util.Scanner;

public class BossView {
    public Scanner scannerView;

    public BossView() {
        scannerView = new Scanner(System.in);
    }

    public void viewAllUsers() {
        for (User user : User.getAllUsers()) {
            System.out.println(user.getUsername());
        }
    }

    public void viewUser(User user) {
        System.out.println(user);
    }

    public void getManagerInfo(ArrayList<String> managerInfo) {
        System.out.print("user name : ");
        String userName = scannerView.nextLine();
        managerInfo.add(userName);
        System.out.print("first name : ");
        String firstName = scannerView.nextLine();
        managerInfo.add(firstName);
        System.out.print("last name : ");
        String lastName = scannerView.nextLine();
        managerInfo.add(lastName);
        System.out.print("email : ");
        String email = scannerView.nextLine();
        managerInfo.add(email);
        System.out.print("phone number : ");
        String phoneNumber = scannerView.nextLine();
        managerInfo.add(phoneNumber);
        System.out.print("password : ");
        String password = scannerView.nextLine();
        managerInfo.add(password);
    }

    public void getCodedDiscountInfo(ArrayList<String> discountCodedInfo) {
        System.out.print("Start time [dd/MM/yyyy]: ");
        String startTime = scannerView.nextLine();
        discountCodedInfo.add(startTime);
        System.out.print("End time [dd/MM/yyyy]: ");
        String endTime = scannerView.nextLine();
        discountCodedInfo.add(endTime);
        System.out.print("Discount amount (must be Integer(double)): ");
        String discountAmount = scannerView.nextLine();
        discountCodedInfo.add(discountAmount);
        System.out.print("repeat (must be Integer): ");
        String repeat = scannerView.nextLine();
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

    public String getEditCodedDiscountField() {
        System.out.println("FIELDS : ");
        System.out.println("1 . remaining time");
        System.out.println("2 . start time");
        System.out.println("3 . end time");
        System.out.println("4 . discount amount");
        System.out.println("5 . back");
        System.out.println("[field]");
        String command = scannerView.nextLine();
        return command;
    }
    public String getEditCodedDiscountInField(){
        System.out.println("change to : ");
        String command = scannerView.nextLine();
        return command;
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

    public String getCompanyInfo(){
        System.out.println("info : ");
        String info = scannerView.nextLine();
        return info.trim();
    }

    public void viewAllCategories() {
        int counter = 1;
        for (Category category : Category.getAllCategories()) {
            System.out.print(counter + " . ");
            System.out.print(category);
        }
        System.out.println();
    }

    public ArrayList<String> getCategoryFeatures() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> features = new ArrayList<>();
        System.out.println("features : [feature's name]/[finish]");
        while (true) {
            String command = scanner.nextLine();
            command = command.trim();
            if (command.equalsIgnoreCase("finish")) {
                break;
            }
            features.add(command);
        }
        return features;
    }

    public String editCategoryTask() {
        System.out.println("please enter a number !!");
        System.out.println("1 . change name of category");
        System.out.println("2 . change a feature's name of category");
        System.out.println("3 . remove a feature of category");
        System.out.println("4 . add a feature to category");
        String number = scannerView.nextLine();
        number = number.trim();
        return number;
    }

    public String getFeatureNameForChangeOrDelete(Category category) {
        int counter = 1;
        System.out.println("please enter name of the feature! ");
        for (String feature : category.getFeatures()) {
            System.out.println(counter + " . " + feature);
            counter += 1;
        }
        String feature = scannerView.nextLine();
        feature = feature.trim();
        return feature;
    }

    public String getFeatureNameForAddOrChange() {
        System.out.println("please enter the new feature's name ! ");
        String newFeature = scannerView.nextLine();
        newFeature = newFeature.trim();
        return newFeature;

    }

    public String getCategoryNewName() {
        System.out.print("new name : ");
        String newName = scannerView.nextLine();
        return newName;
    }

    public void showAllRequests(ArrayList<Request> allRequests){
        for (Request request : allRequests) {
            System.out.println(request);
        }
    }

}