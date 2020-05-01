package Controller;

import View.BossView;
import model.*;
import model.request.Request;

import java.lang.NullPointerException;
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

    public void editField(String userName, String field, String changeField) throws InvalidCommandException {
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
        } else {
            throw new InvalidCommandException("invalid command");
        }
    }

    public void manageUsers(String command) throws InvalidCommandException {
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
            try {
                processViewUser(viewUserMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (deleteUserMatcher.matches()) {
            try {
                processDeleteUser(deleteUserMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (createManagerMatcher.matches()) {
            try {
                processCreateManagerProfile();
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else {
            throw new InvalidCommandException("invalid command");
        }

    }

    public void processViewUser(String userName) throws NullPointerException {
        User user = User.getUserByUserName(userName);
        if (user == null) {
            throw new NullPointerException("user with this user name doesn't exist");
        }

        bossViewManager.viewUser(user);

    }

    public void processDeleteUser(String userName) throws NullPointerException {
        User userToBeDeleted = User.getUserByUserName(userName);
        if (userToBeDeleted == null) {
            throw new NullPointerException("user with this user name doesn't exist");
        }
        ((Manager) user).deleteUser(userToBeDeleted);
    }

    public void processCreateManagerProfile() {
        //TODO : error handling
        ArrayList<String> managerInfo = new ArrayList<String>();
        bossViewManager.getManagerInfo(managerInfo);
        ((Manager) user).createManagerProfile(managerInfo);
    }

    public void manageAllProducts(String command) throws InvalidCommandException {
        if (command.equals("back")) {
            return;
        }
        Pattern removeProductPattern = Pattern.compile("^(?i)remove (.+)$");
        Matcher removeProductMatcher = removeProductPattern.matcher(command);
        if (removeProductMatcher.matches()) {
            try {
                processRemoveProduct(removeProductMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else {
            throw new InvalidCommandException("invalid command");
        }
    }

    public void processRemoveProduct(String productId) throws NullPointerException {
        Product product = Product.getProductById(productId);
        if (product == null) {
            throw new NullPointerException("product with this productId doesn't exist");
        }
        ((Manager) user).removeProduct(product);
    }

    public void processCreateCodedDiscount() {
        //TODO : error handling
        ArrayList<String> codedDiscountInfo = new ArrayList<String>();
        bossViewManager.getCodedDiscountInfo(codedDiscountInfo);
        ((Manager) user).createDiscountCoded(codedDiscountInfo);

    }

    public void manageCodedDiscounts(String command) throws InvalidCommandException {
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
            try {
                processViewCodedDiscount(viewCodedDiscountMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (editCodedDiscountMatcher.matches()) {
            try {
                processEditCodedDiscountFirst(editCodedDiscountMatcher.group(1));
            } catch (InvalidCommandException | NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (removeCodedDiscountMatcher.matches()) {
            try {
                processRemoveCodedDiscount(removeCodedDiscountMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else {
            throw new InvalidCommandException("invalid command");
        }
    }

    public void processViewCodedDiscount(String discountCode) throws NullPointerException {
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        if (codedDiscount == null) {
            throw new NullPointerException("coded discount with this code doesn't exist");
        }
        bossViewManager.viewCodedDiscount(codedDiscount);

    }

    public void processEditCodedDiscountFirst(String discountCode) throws NullPointerException, InvalidCommandException {
        if (CodedDiscount.getDiscountById(discountCode) == null) {
            throw new NullPointerException("coded discount with this code doesn't exist");
        }
        String field = bossViewManager.getEditCodedDiscountField();
        if (field.equalsIgnoreCase("back")) {
            return;
        } else {
            processEditCodedDiscountSecond(field, discountCode);
        }
    }

    public void processEditCodedDiscountSecond(String field, String discountCode) throws InvalidCommandException {
        String changeField = bossViewManager.getEditCodedDiscountInField();
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
        } else {
            throw new InvalidCommandException("invalid field");
        }
    }

    public void processRemoveCodedDiscount(String discountCode) throws NullPointerException {
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        if (codedDiscount == null) {
            throw new NullPointerException("coded discount with this code doesn't exist");
        }
        ((Manager) user).removeCodedDiscount(codedDiscount);
    }

    public void manageRequests(String command) throws InvalidCommandException {
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
            try {
                viewRequestDetails(detailsMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (acceptRequestMatcher.matches()) {
            try {
                acceptRequest(acceptRequestMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (declineRequestMatcher.matches()) {
            try {
                declineRequest(declineRequestMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else {
            throw new InvalidCommandException("invalid command");
        }
    }

    public void viewRequestDetails(String requestId) throws NullPointerException {
        Request request = Manager.getRequestById(requestId);
        if (request == null) {
            throw new NullPointerException("request with this Id doesn't exist");
        }
        bossViewManager.viewRequestDetails(request);

    }

    public void acceptRequest(String requestId) throws NullPointerException {
        Request request = Manager.getRequestById(requestId);
        if (request == null) {
            throw new NullPointerException("request with this Id doesn't exist");
        }
        ((Manager) user).acceptRequest(request);
    }

    public void declineRequest(String requestId) throws NullPointerException {
        Request request = Manager.getRequestById(requestId);
        if (request == null) {
            throw new NullPointerException("request with this Id doesn't exist");
        }
        ((Manager) user).declineRequest(request);

    }


    public void manageCategories(String command) throws InvalidCommandException {
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
            try {
                editCategory(editCategoryMatcher.group(1));
            } catch (InvalidCommandException | NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (addCategoryMatcher.matches()) {
            try {
                addCategory(addCategoryMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (removeCategoryMatcher.matches()) {
            try {
                removeCategory(removeCategoryMatcher.group(1));
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else {
            throw new InvalidCommandException("invalid command");
        }
    }

    public void editCategory(String categoryName) throws InvalidCommandException {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null) {
            throw new NullPointerException("category with this name doesn't exist");
        }
        String editTask = bossViewManager.editCategoryTask();
        if (editTask.equalsIgnoreCase("1")) {
            String newName = bossViewManager.getCategoryNewName();
            try {
                ((Manager) user).editCategoryName(category, newName);
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (editTask.equalsIgnoreCase("2")) {
            String oldFeatureName = bossViewManager.getFeatureNameForChangeOrDelete(category);
            String newFeatureName = bossViewManager.getFeatureNameForAddOrChange();
            try {
                ((Manager) user).editFeatureName(category, oldFeatureName, newFeatureName);
            } catch (InvalidCommandException | NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (editTask.equalsIgnoreCase("3")) {
            String featureName = bossViewManager.getFeatureNameForChangeOrDelete(category);
            try {
                ((Manager) user).deleteFeature(category, featureName);
            } catch (NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else if (editTask.equalsIgnoreCase("4")) {
            String featureName = bossViewManager.getFeatureNameForAddOrChange();
            try {
                ((Manager) user).addCategoryFeature(category, featureName);
            } catch (InvalidCommandException | NullPointerException e) {
                viewManager.showErrorMessage(e.getMessage());
            }
        } else {
            throw new InvalidCommandException("invalid number");
        }

    }

    public void addCategory(String categoryName) {
        ArrayList<String> features = bossViewManager.getCategoryFeatures();
        new Category(categoryName, features);
    }

    public void removeCategory(String categoryName) throws NullPointerException {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null) {
            throw new NullPointerException("category with this name doesn't exist");
        }
        ((Manager) user).removeCategory(category);
    }


}
