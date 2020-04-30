package Controller;

import View.BossView;
import model.*;
import model.request.Request;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BossProcessor extends Processor {
    private BossView bossViewManager;

    public BossProcessor() {
        bossViewManager = new BossView();
    }

    public BossView getBossViewManager() {
        return bossViewManager;
    }

    public void editField(String userName, String field, String changeField) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        field = field.trim();
        Pattern firstNamePattern = Pattern.compile("^(?i)first name$");
        Matcher firstNameMatcher = firstNamePattern.matcher(field);
        Pattern lastNamePattern = Pattern.compile("^(?i)last name$");
        Matcher lastNameMatcher = lastNamePattern.matcher(field);
        Pattern emailPattern = Pattern.compile("^(?i)email$");
        Matcher emailMatcher = emailPattern.matcher(field);
        Pattern phoneNumberPattern = Pattern.compile("^(?i)phone number$");
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(field);
        Pattern passwordPattern = Pattern.compile("^(?i)first name$");
        Matcher passwordMatcher = passwordPattern.matcher(field);
        if (firstNameMatcher.matches()) {
            user.getUserPersonalInfo().setFirstName(changeField);
        } else if (lastNameMatcher.matches()) {
            user.getUserPersonalInfo().setLastName(changeField);
        } else if (emailMatcher.matches()) {
            user.getUserPersonalInfo().setEmail(changeField);
        } else if (phoneNumberMatcher.matches()) {
            user.getUserPersonalInfo().setPhoneNumber(changeField);
        } else if (passwordMatcher.matches()) {
            user.getUserPersonalInfo().setPassword(changeField);
        }
    }

    public void manageUsers(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern viewUserPattern = Pattern.compile("view (.+)");
        Matcher viewUserMatcher = viewUserPattern.matcher(command);
        Pattern deleteUserPattern = Pattern.compile("delete user (.+)");
        Matcher deleteUserMatcher = deleteUserPattern.matcher(command);
        Pattern createManagerPattern = Pattern.compile("create manager profile");
        Matcher createManagerMatcher = createManagerPattern.matcher(command);
        if (viewUserMatcher.matches()) {
            processViewUser(viewUserMatcher.group(1));
        } else if (deleteUserMatcher.matches()) {
            processDeleteUser(deleteUserMatcher.group(1));
        } else if (createManagerMatcher.matches()) {
            processCreateManagerProfile();
        }

    }

    public void processViewUser(String userName) {
        //TODO : error handling
        User user = User.getUserByUserName(userName);
        bossViewManager.viewUser(user);

    }

    public void processDeleteUser(String userName) {
        //TODO : error handling
        User userToBeDeleted = User.getUserByUserName(userName);
        ((Manager) user).deleteUser(userToBeDeleted);
    }

    public void processCreateManagerProfile() {
        //TODO : error handling
        ArrayList<String> managerInfo = new ArrayList<String>();
        bossViewManager.getManagerInfo(managerInfo);
        ((Manager) user).createManagerProfile(managerInfo);
    }

    public void manageAllProducts(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern removeProductPattern = Pattern.compile("^(?i)remove (.+)$");
        Matcher removeProductMatcher = removeProductPattern.matcher(command);
        if (removeProductMatcher.matches()) {
            processRemoveProduct(removeProductMatcher.group(1));
        }
    }

    public void processRemoveProduct(String productId) {
        //TODO : error handling
        Product product = Product.getProductById(productId);
        ((Manager) user).removeProduct(product);
    }

    public void processCreateCodedDiscount() {
        //TODO : error handling
        ArrayList<String> codedDiscountInfo = new ArrayList<String>();
        bossViewManager.getCodedDiscountInfo(codedDiscountInfo);
        ((Manager) user).createDiscountCoded(codedDiscountInfo);

    }

    public void manageCodedDiscounts(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern viewCodedDiscountPattern = Pattern.compile("view discount code (.+)");
        Matcher viewCodedDiscountMatcher = viewCodedDiscountPattern.matcher(command);
        Pattern editCodedDiscountPattern = Pattern.compile("edit discount code (.+)");
        Matcher editCodedDiscountMatcher = editCodedDiscountPattern.matcher(command);
        Pattern removeCodedDiscountPattern = Pattern.compile("remove discount code (.+)");
        Matcher removeCodedDiscountMatcher = removeCodedDiscountPattern.matcher(command);
        if (viewCodedDiscountMatcher.matches()) {
            processViewCodedDiscount(viewCodedDiscountMatcher.group(1));
        } else if (editCodedDiscountMatcher.matches()) {
            processEditCodedDiscountFirst(editCodedDiscountMatcher.group(1));
        } else if (removeCodedDiscountMatcher.matches()) {
            processRemoveCodedDiscount(removeCodedDiscountMatcher.group(1));
        }
    }

    public void processViewCodedDiscount(String discountCode) {
        //TODO : error handling
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        bossViewManager.viewCodedDiscount(codedDiscount);

    }

    public void processEditCodedDiscountFirst(String discountCode) {
        //TODO : error handling
        ArrayList<String> discountInfo = new ArrayList<>();
        bossViewManager.getEditCodedDiscountInfo(discountInfo);
        for (int i = 0; i < discountInfo.size(); i += 2) {
            processEditCodedDiscountSecond(discountInfo.get(i), discountInfo.get(i + 1), discountCode);
        }
    }

    public void processEditCodedDiscountSecond(String field, String changeField, String discountCode) {
        //TODO : error handling
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        Pattern discountCodePattern = Pattern.compile("^(?i)discount code$");
        Matcher discountCodeMatcher = discountCodePattern.matcher(field);
        Pattern startTimePattern = Pattern.compile("^(?i)start time$");
        Matcher startTimeMatcher = startTimePattern.matcher(field);
        Pattern endTimePattern = Pattern.compile("^(?i)end time$");
        Matcher endTimeMatcher = endTimePattern.matcher(field);
        Pattern amountPattern = Pattern.compile("^(?i)discount amount$");
        Matcher amountMatcher = amountPattern.matcher(field);
        Pattern remainingPattern = Pattern.compile("^(?i)remaining time$");
        Matcher remainingMatcher = remainingPattern.matcher(field);
        if (discountCodeMatcher.matches()) {
            codedDiscount.setDiscountCode(changeField);
        } else if (startTimeMatcher.matches()) {
            codedDiscount.setStartTime(changeField);
        } else if (endTimeMatcher.matches()) {
            codedDiscount.setEndTime(changeField);
        } else if (amountMatcher.matches()) {
            codedDiscount.setDiscountAmount(changeField);
        } else if (remainingMatcher.matches()) {
            codedDiscount.setRepeat(changeField);
        }


    }

    public void processRemoveCodedDiscount(String discountCode) {
        //TODO : error handling
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        ((Manager) user).removeCodedDiscount(codedDiscount);
    }

    public void manageRequests(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern detailsPattern = Pattern.compile("details (.+)");
        Matcher detailsMatcher = detailsPattern.matcher(command);
        Pattern acceptRequestPattern = Pattern.compile("accept (.+)");
        Matcher acceptRequestMatcher = acceptRequestPattern.matcher(command);
        Pattern declineRequestPattern = Pattern.compile("decline (.+)");
        Matcher declineRequestMatcher = declineRequestPattern.matcher(command);
        if (detailsMatcher.matches()) {
            viewRequestDetails(detailsMatcher.group(1));
        } else if (acceptRequestMatcher.matches()) {
            acceptRequest(acceptRequestMatcher.group(1));
        } else if (declineRequestMatcher.matches()) {
            declineRequest(declineRequestMatcher.group(1));
        }
    }

    public void viewRequestDetails(String requestId) {
        //TODO : error handling
        Request request = Manager.getRequestById(requestId);
        bossViewManager.viewRequestDetails(request);

    }

    public void acceptRequest(String requestId) {
        //TODO : error handling
        Request request = Manager.getRequestById(requestId);
        ((Manager) user).acceptRequest(request);
    }

    public void declineRequest(String requestId) {
        //TODO : error handling
        Request request = Manager.getRequestById(requestId);
        ((Manager) user).declineRequest(request);

    }


    public void manageCategories(String command) {
        //TODO : error handling
        if (command.equals("back")) {
            return;
        }
        Pattern editCategoryPattern = Pattern.compile("edit (.+)");
        Matcher editCategoryMatcher = editCategoryPattern.matcher(command);
        Pattern addCategoryPattern = Pattern.compile("add (.+)");
        Matcher addCategoryMatcher = addCategoryPattern.matcher(command);
        Pattern removeCategoryPattern = Pattern.compile("remove (.+)");
        Matcher removeCategoryMatcher = removeCategoryPattern.matcher(command);
        if (editCategoryMatcher.matches()) {
            editCategory(editCategoryMatcher.group(1));
        } else if (addCategoryMatcher.matches()) {
            addCategory(addCategoryMatcher.group(1));
        } else if (removeCategoryMatcher.matches()) {
            removeCategory(removeCategoryMatcher.group(1));
        }
    }

    public void editCategory(String categoryName) {
        //TODO : error handling
        Category category = Category.getCategoryByName(categoryName);
        String editTask = bossViewManager.editCategoryTask();
        if (editTask.equalsIgnoreCase("1")) {
            String newName = bossViewManager.getCategoryNewName();
            ((Manager) user).editCategoryName(category, newName);
        } else if (editTask.equalsIgnoreCase("2")) {
            String oldFeatureName = bossViewManager.getFeatureNameForChangeOrDelete(category);
            String newFeatureName = bossViewManager.getFeatureNameForAddOrChange();
            ((Manager) user).editFeatureName(category, oldFeatureName, newFeatureName);
        } else if (editTask.equalsIgnoreCase("3")) {
            String featureName = bossViewManager.getFeatureNameForChangeOrDelete(category);
            ((Manager) user).deleteFeature(category, featureName);
        } else if (editTask.equalsIgnoreCase("4")) {
            String featureName = bossViewManager.getFeatureNameForAddOrChange();
            ((Manager) user).addCategoryFeature(category, featureName);
        }

    }

    public void addCategory(String categoryName) {
        //TODO : error handling
        ArrayList<String> features = bossViewManager.getCategoryFeatures();
        new Category(categoryName, features);
    }

    public void removeCategory(String categoryName) {
        //TODO : error handling
        Category category = Category.getCategoryByName(categoryName);
        ((Manager) user).removeCategory(category);

    }


}
