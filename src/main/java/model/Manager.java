package model;


import model.request.*;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.text.ParseException;
import java.util.ArrayList;

public class Manager extends User {
    public static ArrayList<Request> allRequest = new ArrayList<Request>();

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

    public void createDiscountCoded(ArrayList<String> discountCodedInfo) {
        String startTime = discountCodedInfo.get(0);
        String endTime = discountCodedInfo.get(1);
        double discountAmount = Double.parseDouble(discountCodedInfo.get(2));
        int repeat = Integer.parseInt(discountCodedInfo.get(3));
        new CodedDiscount(startTime, endTime, discountAmount, repeat);
    }

    public void removeCodedDiscount(CodedDiscount codedDiscount) {
        CodedDiscount.allCodedDiscount.remove(codedDiscount);
    }


    public void acceptRequest(Request request) throws InvalidCommandException, ParseException {
        if (request.getRequestType().equalsIgnoreCase("sellerType")) {
            acceptSellerRequest(request);
        } else if (request.getRequestType().equalsIgnoreCase("offType")) {
            acceptOffRequest((OffRequest) request);
        } else if (request.getRequestType().equalsIgnoreCase("productType")) {
            acceptProductRequest((ProductRequest) request);
        } else if (request.getRequestType().equalsIgnoreCase("editOffType")) {
            acceptEditOffRequest((EditOffRequest) request);
        } else if (request.getRequestType().equalsIgnoreCase("editProductType")) {
            acceptEditProductRequest((EditProductRequest) request);
        }
        allRequest.remove(request);
    }

    public void acceptEditOffRequest(EditOffRequest editOffRequest) throws InvalidCommandException, ParseException {
        Off off = editOffRequest.getOff();
        off.editField(editOffRequest.getField(), editOffRequest.getInput());
    }

    public void acceptEditProductRequest(EditProductRequest editProductRequest) throws InvalidCommandException {
        Product product = editProductRequest.getProduct();
        product.editField(editProductRequest.getField(), editProductRequest.getInput());
    }

    public void acceptProductRequest(ProductRequest request) {
        Product product = request.getProduct();
        Product.allProductsInList.add(product);
        Product.allProductsInQueueExpect.remove(product);
        product.getSellers().add(product.getSeller());
        Seller seller = product.getSeller();
        seller.getProducts().add(product);
        if (seller.getProductsNumber().containsKey(product)) {
            int numberOfProduct = seller.getProductsNumber().get(product);
            seller.getProductsNumber().replace(product, numberOfProduct + request.getNumber());
        } else {
            seller.getProductsNumber().put(product, request.getNumber());
        }
        product.setProductState(State.ProductState.CONFIRMED);
    }

    public void acceptOffRequest(OffRequest request) {
        Off off = request.getOff();
        Off.acceptedOffs.add(off);
        Off.inQueueExpectionOffs.remove(off);
        off.setOffState(State.OffState.CONFIRMED);
    }

    public void acceptSellerRequest(Request request) {
        Seller seller = ((SellerRequest) request).getSeller();
        User.allUsers.add(seller);
    }

    public void declineRequest(Request request) {
        if (request.getRequestType().equalsIgnoreCase("offType")) {
            declineOffRequest(request);
        } else if (request.getRequestType().equalsIgnoreCase("productType")) {
            declineProductRequest(request);
        }
        allRequest.remove(request);
    }

    public void declineProductRequest(Request productRequest) {
        Product product = ((ProductRequest) productRequest).getProduct();
        Seller seller = product.getSeller();
        seller.getProducts().remove(product);
        Product.allProductsInQueueExpect.remove(product);
    }

    public void declineOffRequest(Request offRequest) {
        Off off = ((OffRequest) offRequest).getOff();
        Off.inQueueExpectionOffs.remove(off);
        Seller seller = off.getSeller();
        seller.getOffs().remove(off);
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
        Seller seller = product.getSeller();
        seller.getProducts().remove(product);
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


}
