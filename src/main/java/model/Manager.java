package model;


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
        Category category = product.getCategory();
        category.removeProduct(product);
        removeFromSellerProducts(product);
    }

    public void createDiscountCoded(ArrayList<String> discountCodedInfo) {
        String discountCode = discountCodedInfo.get(0);
        String startTime = discountCodedInfo.get(1);
        String endTime = discountCodedInfo.get(2);
        double discountAmount = Double.parseDouble(discountCodedInfo.get(3));
        int repeat = Integer.parseInt(discountCodedInfo.get(4));
        new CodedDiscount(discountCode, startTime, endTime, discountAmount, repeat);

    }

    public void removeCodedDiscount(CodedDiscount codedDiscount) {
        CodedDiscount.allCodedDiscount.remove(codedDiscount);
    }


    public void acceptRequest(Request request) {
        if (request.getRequestType().equals("sellerType")) {
            acceptSellerRequest(request);
        } else if (request.getRequestType().equals("offType")) {
            acceptOffRequest((OffRequest) request);
        } else if (request.getRequestType().equals("productType")) {
            acceptProductRequest((ProductRequest) request);
        }
        allRequest.remove(request);
    }

    public void acceptProductRequest(ProductRequest request) {
        Product product = request.getProduct();
        Product.allProductsInList.add(product);
        Product.allProductsInQueueExpect.remove(product);
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
        if(request.getRequestType().equalsIgnoreCase("offType")){
            Off off = ((OffRequest)request).getOff();
            declineOffRequest(off);
        } else if(request.getRequestType().equalsIgnoreCase("productType")){
            Product product = ((ProductRequest)request).getProduct();
            declineProductRequest(product);

        }
        allRequest.remove(request);
    }

    public void declineProductRequest(Product product){
        Seller seller = product.getSeller();
        seller.getProducts().remove(product);
        Product.allProductsInQueueExpect.remove(product);
    }

    public void declineOffRequest(Off off){
        Off.inQueueExpectionOffs.remove(off);
        Seller seller = off.getSeller();
        seller.getOffs().remove(off);
        // TODO : remove from product.off
    }

    public void editCategory(Category category) {

    }

    public void removeCategory(Category category){
        ArrayList<Category>categoriesToBeRemoved = new ArrayList<>();
        removeSuperCategory(category, categoriesToBeRemoved);
        for (Category categoryToBeRemoved : categoriesToBeRemoved) {
            Category.getAllCategories().remove(categoryToBeRemoved);
        }

    }
    public void removeSuperCategory(Category category, ArrayList<Category> categoriesToBeRemoved) {
        if(category.getSubcategories().size()!=0){
            for (Category subcategory : category.getSubcategories()) {
                removeSuperCategory(subcategory, categoriesToBeRemoved);
            }
        } else {
            removeLeafCategory(category);
        }
        categoriesToBeRemoved.add(category);
    }

    public void removeLeafCategory(Category category) {
        for (Product product : category.getProducts()) {
            if (Product.allProductsInList.contains(product)) {
                Product.allProductsInList.remove(product);
            } else {
                Product.allProductsInQueueExpect.remove(product);
                removeProductRequest(product);
            }
            removeFromSellerProducts(product);

        }
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
