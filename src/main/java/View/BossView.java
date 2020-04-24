package View;

import model.Category;
import model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class BossView {
    public Scanner scanner;

    public BossView() {
        scanner = Menu.scanner;
    }

    public void viewAllUsers(){
        for (User user : User.allUsers) {
            System.out.println(user.getUsername());
        }
    }

    public void viewUser(User user) {
        System.out.println("user name : " + user.getUsername());
        System.out.println("first name : " + user.getUserPersonalInfo().getFirstName());
        System.out.println("last name : " + user.getUserPersonalInfo().getLastName());
        System.out.println("email : " + user.getUserPersonalInfo().getEmail());
        System.out.println("phone number : " + user.getUserPersonalInfo().getPhoneNumber());
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

    public void viewDiscountCodes(){

    }
    public void getCategoryInfo() {

    }

    public void showCategories(ArrayList<Category> categories) {
        // main -> sub
    }

}
