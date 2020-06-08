package Controller.console;

import View.BossView;
import model.*;
import model.request.Request;

import java.lang.NullPointerException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BossProcessor extends Processor {
    private final static BossProcessor instance = new BossProcessor();

    public static BossProcessor getInstance() {
        return instance;
    }

    private BossView bossViewManager;

    public BossProcessor() {
        bossViewManager = new BossView();
    }

    public BossView getBossViewManager() {
        return bossViewManager;
    }

    public void editField(String field, String changeField) throws InvalidCommandException {
        field = field.trim();
        Pattern firstNamePattern = Pattern.compile("^(?i)edit firstname$");
        Matcher firstNameMatcher = firstNamePattern.matcher(field);
        Pattern lastNamePattern = Pattern.compile("^(?i)edit lastname$");
        Matcher lastNameMatcher = lastNamePattern.matcher(field);
        Pattern emailPattern = Pattern.compile("^(?i)edit email$");
        Matcher emailMatcher = emailPattern.matcher(field);
        Pattern phoneNumberPattern = Pattern.compile("^(?i)edit phonenumber$");
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(field);
        Pattern passwordPattern = Pattern.compile("^(?i)edit password$");
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
        Pattern viewUserPattern = Pattern.compile("view user (.+)");
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
        ArrayList<String> managerInfo = new ArrayList<String>();
        bossViewManager.getManagerInfo(managerInfo);
        ((Manager) user).createManagerProfile(managerInfo);
    }

    public void manageAllProducts(String command) throws InvalidCommandException {
        if (command.equals("back")) {
            return;
        }
        Pattern removeProductPattern = Pattern.compile("^(?i)remove product (.+)$");
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

    public void processCreateCodedDiscount() throws InvalidCommandException, ParseException {
        ArrayList<String> codedDiscountInfo = new ArrayList<String>();
        bossViewManager.getCodedDiscountInfo(codedDiscountInfo);
        if (checkCodedDiscountInfo(codedDiscountInfo)) {
            ((Manager) user).createDiscountCoded(codedDiscountInfo);
        }

    }

    public boolean checkCodedDiscountInfo(ArrayList<String> codedDiscountInfo) throws InvalidCommandException {
        if (!codedDiscountInfo.get(2).matches("^\\d*\\.?\\d*$")) {
            throw new InvalidCommandException("invalid discount amount");
        }
        if (!codedDiscountInfo.get(3).matches("\\d+")) {
            throw new InvalidCommandException("invalid repeat number");
        }
        return true;
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
            } catch (InvalidCommandException | NullPointerException | ParseException e) {
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

    public void processEditCodedDiscountFirst(String discountCode) throws NullPointerException, InvalidCommandException, ParseException {
        if (CodedDiscount.getDiscountById(discountCode) == null) {
            throw new NullPointerException("coded discount with this code doesn't exist");
        }
        String field = bossViewManager.getEditCodedDiscountField();
        if (field.equalsIgnoreCase("back")) {
            return;
        } else {
            String changeField = bossViewManager.getEditCodedDiscountInField();
            processEditCodedDiscountSecond(field, discountCode, changeField);
        }
    }

    public void processEditCodedDiscountSecond(String field, String discountCode, String changeField) throws InvalidCommandException, NullPointerException, ParseException {
        CodedDiscount codedDiscount = CodedDiscount.getDiscountById(discountCode);
        if (codedDiscount == null) {
            throw new NullPointerException("codedDiscount with this Id doesn't exist");
        }
        Pattern startTimePattern = Pattern.compile("^(?i)start time$");
        Matcher startTimeMatcher = startTimePattern.matcher(field);
        Pattern endTimePattern = Pattern.compile("^(?i)end time$");
        Matcher endTimeMatcher = endTimePattern.matcher(field);
        Pattern amountPattern = Pattern.compile("^(?i)discount amount$");
        Matcher amountMatcher = amountPattern.matcher(field);
        Pattern remainingPattern = Pattern.compile("^(?i)remaining time$");
        Matcher remainingMatcher = remainingPattern.matcher(field);
        if (startTimeMatcher.matches() && changeField.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d")) {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(changeField);
            if (date.before(codedDiscount.getEndTime())) {
                codedDiscount.setStartTime(date);
            } else {
                throw new InvalidCommandException("startTime must be before endTime");
            }
        } else if (endTimeMatcher.matches() && changeField.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d")) {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(changeField);
            if (date.after(codedDiscount.getStartTime())) {
                codedDiscount.setEndTime(date);
            } else {
                throw new InvalidCommandException("endTime must be after startTime");
            }
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
        Pattern detailsPattern = Pattern.compile("details request (.+)");
        Matcher detailsMatcher = detailsPattern.matcher(command);
        Pattern acceptRequestPattern = Pattern.compile("accept request (.+)");
        Matcher acceptRequestMatcher = acceptRequestPattern.matcher(command);
        Pattern declineRequestPattern = Pattern.compile("decline request (.+)");
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
            } catch (NullPointerException | ParseException e) {
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

    public void acceptRequest(String requestId) throws NullPointerException, ParseException, InvalidCommandException {
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

    public void addCompany(String command) throws InvalidCommandException {
        if (command.equalsIgnoreCase("back")) {
            return;
        }
        Pattern addCompanyPattern = Pattern.compile("add company (.+)");
        Matcher addCompanyMatcher = addCompanyPattern.matcher(command);
        if (addCompanyMatcher.matches()) {
            String info = bossViewManager.getCompanyInfo();
            new Company(addCompanyMatcher.group(1), info);
        } else {
            throw new InvalidCommandException("invalid command");
        }
    }

    public void manageCategories(String command) throws InvalidCommandException {
        if (command.equals("back")) {
            return;
        }
        Pattern editCategoryPattern = Pattern.compile("edit category (.+)");
        Matcher editCategoryMatcher = editCategoryPattern.matcher(command);
        Pattern addCategoryPattern = Pattern.compile("add category (.+)");
        Matcher addCategoryMatcher = addCategoryPattern.matcher(command);
        Pattern removeCategoryPattern = Pattern.compile("remove category (.+)");
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

    public void manageComments(String command) {
        if (command.matches("accept comment (\\d+)")) {
            Comment.getInQueueExpectation().get(Integer.parseInt(command.split(" ")[2])).accept();
        } else if (command.matches("decline comment (\\d+)")) {
            Comment.getInQueueExpectation().get(Integer.parseInt(command.split(" ")[2])).decline();
        } else if (command.matches("back")) {
            return;
        } else
            errorMessage("invalid command");
    }


}