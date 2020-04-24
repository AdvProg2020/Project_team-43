package model;

import java.util.ArrayList;

public class Manager extends User {
    public static ArrayList<Request> allRequest = new ArrayList<Request>();

    public Manager(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
    }

    @Override
    public void setUserType() {
        userType = UserType.MANAGER;
    }

    public static Request getRequestById(String requestId) {

        return null;
    }

    public UserPersonalInfo getUserPersonalInfo() {
        return userPersonalInfo;
    }

    public void deleteUser(User user) {
        User.allUsers.remove(user);
    }

    public void createManagerProfile(ArrayList<String> managerInfo) {
        String userName = managerInfo.get(0);
        String firstName = managerInfo.get(1);
        String lastName = managerInfo.get(2);
        String email = managerInfo.get(3);
        String phoneNumber = managerInfo.get(4);
        String password = managerInfo.get(5);
        UserPersonalInfo newPersonalInfo = new UserPersonalInfo(firstName, lastName, email, phoneNumber, password);
        new Manager(userName, newPersonalInfo);
    }

    public void removeProduct(Product product) {
        Product.allProductsInList.remove(product);
    }

    public void createDiscountCoded(ArrayList<String> discountCodedInfo) {
        String discountCode = discountCodedInfo.get(0);
        String startTime = discountCodedInfo.get(1);
        String endTime = discountCodedInfo.get(2);
        double discountAmount = Double.parseDouble(discountCodedInfo.get(3));
        int repeat = Integer.parseInt(discountCodedInfo.get(4));
        new CodedDiscount(discountCode, startTime, endTime, discountAmount, repeat);

    }

    public void viewDiscountCode(String discountId) {

    }

    public void editDiscount(String discountId) {

    }

    public void deleteDiscount(String discountId) {

    }

    public void detailRequest(String requestId) {

    }

    public void acceptRequest(String requestId) {

    }

    public void declineRequest(String requestId) {

    }

    public void editCategory(Category category) {

    }

    public void addCategory(Category category) {

    }

    public void removeCategory(Category category) {

    }


}
