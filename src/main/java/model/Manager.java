package model;


import model.database.Loader;
import model.database.Saver;
import model.request.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Manager extends User {
    private static String fileAddress = "database/Manager.dat";
    public static ArrayList<Request> allRequest = new ArrayList<>();

    public Manager(String username, UserPersonalInfo userPersonalInfo) {
        super(username, userPersonalInfo);
        allUsers.add(this);
    }

    @Override
    public void setUserType() {
        userType = UserType.MANAGER;
    }

    public static Request getRequestById(String requestId) {
        Request request = Request.getRequestById(requestId);
        if (request == null) {
            throw new NullPointerException("request with this Id doesn't exist");
        }
        return request;
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
        Category category = product.getCategory();
        category.removeProduct(product);
        removeFromSellerProducts(product);
    }

    public void createDiscountCoded(ArrayList<String> discountCodedInfo) throws ParseException {
        String startTime = discountCodedInfo.get(0);
        String endTime = discountCodedInfo.get(1);
        Date startTimeDate = new SimpleDateFormat("dd/MM/yyyy").parse(startTime);
        Date endTimeDate = new SimpleDateFormat("dd/MM/yyyy").parse(endTime);
        double discountAmount = Double.parseDouble(discountCodedInfo.get(2));
        int repeat = Integer.parseInt(discountCodedInfo.get(3));
        CodedDiscount codedDiscount = new CodedDiscount(startTimeDate, endTimeDate, discountAmount, repeat);
        for (User user : allUsers) {
            if (user instanceof Buyer){
                ((Buyer)user).addDiscountCode(codedDiscount);
            }
        }
    }

    public void removeCodedDiscount(CodedDiscount codedDiscount) {
        CodedDiscount.remove(codedDiscount);
    }


    public void acceptRequest(Request request) throws InvalidCommandException, ParseException {
        if (request.getRequestType().equalsIgnoreCase("sellerType")) {
            acceptSellerRequest(request);
        } else if (request.getRequestType().equalsIgnoreCase("offType")) {
            acceptOffRequest((OffRequest) request);
        } else if (request.getRequestType().equalsIgnoreCase("productType")) {
            acceptProductRequest((ProductRequest) request);
        } else if (request.getRequestType().equalsIgnoreCase("edit off")) {
            acceptEditOffRequest((EditOffRequest) request);
        } else if (request.getRequestType().equalsIgnoreCase("edit product")) {
            acceptEditProductRequest((EditProductRequest) request);
        }
        allRequest.remove(request);
    }

    public void acceptEditOffRequest(EditOffRequest editOffRequest) throws InvalidCommandException, ParseException {
        Off off = editOffRequest.getOff();
        off.editField(editOffRequest.getField(), editOffRequest.getInput());
        off.setOffState(State.OffState.CONFIRMED);
        Off.acceptedOffs.add(off);
        Off.allOffsInQueueEdit.remove(off);
    }

    public void acceptEditProductRequest(EditProductRequest editProductRequest) throws InvalidCommandException {
        Product product = editProductRequest.getProduct();
        product.editField(editProductRequest.getField(), editProductRequest.getInput());
        product.setProductState(State.ProductState.CONFIRMED);
        Product.allProductsInList.add(product);
        Product.allProductsInQueueEdit.remove(product);
    }

    public void acceptProductRequest(ProductRequest request) {
        Product product = request.getProduct();
        Product.allProductsInList.add(product);
        Product.allProductsInQueueExpect.remove(product);
        product.getSellers().add(request.getSeller());
        ArrayList<Seller> sellers = product.getSellers();
        for (Seller seller : sellers) {
            if (seller.getProductsNumber().containsKey(product)) {
                int numberOfProduct = seller.getProductsNumber().get(product);
                seller.getProductsNumber().replace(product, numberOfProduct + request.getNumber());
            } else {
                seller.getProductsNumber().put(product, request.getNumber());
            }
        }
        product.setProductState(State.ProductState.CONFIRMED);
    }

    public void acceptOffRequest(OffRequest request) {
        Off off = request.getOff();
        Seller seller = off.getSeller();
        seller.getOffs().add(off);
        Off.acceptedOffs.add(off);
        Off.inQueueExpectionOffs.remove(off);
        off.setOffState(State.OffState.CONFIRMED);
    }

    public void acceptSellerRequest(Request request) {
        Seller seller = ((SellerRequest) request).getSeller();
        allUsers.add(seller);
    }

    public void declineRequest(Request request) {
        if (request.getRequestType().equalsIgnoreCase("offType")) {
            declineOffRequest(request);
        } else if (request.getRequestType().equalsIgnoreCase("productType")) {
            declineProductRequest(request);
        } else if (request.getRequestType().equalsIgnoreCase("edit off")) {
            declineEditOffRequest(((EditOffRequest) request));
        } else if (request.getRequestType().equalsIgnoreCase("edit product")) {
            declineEditProductRequest((EditProductRequest) request);
        }
        allRequest.remove(request);
    }

    public void declineEditOffRequest(EditOffRequest request) {
        Off off = request.getOff();
        request.getOff().setOffState(State.OffState.CONFIRMED);
        Off.acceptedOffs.add(off);
        Off.allOffsInQueueEdit.remove(off);
    }

    public void declineEditProductRequest(EditProductRequest request) {
        Product product = request.getProduct();
        request.getProduct().setProductState(State.ProductState.CONFIRMED);
        Product.allProductsInList.add(product);
        Product.allProductsInQueueEdit.remove(product);
    }

    public void declineProductRequest(Request productRequest) {
        Product product = ((ProductRequest) productRequest).getProduct();
        Product.allProductsInQueueExpect.remove(product);
    }

    public void declineOffRequest(Request offRequest) {
        Off off = ((OffRequest) offRequest).getOff();
        Off.inQueueExpectionOffs.remove(off);
    }

    public void editCategoryName(Category category, String newName) {
        category.setName(newName);
    }

    public void addCategoryFeature(Category category, String newFeature) throws InvalidCommandException {
        if (!category.hasFeature(newFeature)) {
            category.addFeatures(newFeature);
        } else {
            throw new InvalidCommandException("category already has this feature");
        }
    }

    public void editFeatureName(Category category, String oldFeatureName, String newFeatureName) throws InvalidCommandException {
        if (category.hasFeature(oldFeatureName)) {
            category.changeFeatureName(oldFeatureName, newFeatureName);
        } else {
            throw new InvalidCommandException("category doesn't have this feature");
        }
    }

    public void deleteFeature(Category category, String featureName) {
        category.removeFeature(featureName);
    }

    public void removeCategory(Category category) {
        for (Product product : category.getProducts()) {
            removeProduct(product);
        }
        Category.getAllCategories().remove(category);
    }

    public void removeFromSellerProducts(Product product) {
        ArrayList<Seller> sellers = product.getSellers();
        for (Seller seller : sellers) {
            seller.getProductsNumber().remove(product);
        }
    }

    private void removeProductRequest(Product product) {
        for (int i = 0; i < allRequest.size(); i++) {
            if (allRequest.get(i).getRequestType().equalsIgnoreCase("productType")) {
                if (((ProductRequest) allRequest.get(i)).getProduct() == product) {
                    allRequest.remove(allRequest.get(i));
                    return;
                }
            }
        }
    }


    public static void load() throws FileNotFoundException {
        Manager[] managers = (Manager[]) Loader.load(Manager[].class, fileAddress);
        if (managers != null) {
            ArrayList<Manager> allManagers = new ArrayList<>(Arrays.asList(managers));
            allUsers.addAll(allManagers);
        }
    }


    public static void save() throws IOException {
        ArrayList<Manager> allManagers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.userType == UserType.MANAGER) {
                allManagers.add((Manager) user);
            }
        }
        Saver.save(allManagers, fileAddress);
    }


}