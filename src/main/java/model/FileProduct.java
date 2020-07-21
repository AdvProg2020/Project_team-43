package model;


public class FileProduct extends Product {
    private String address;
    private String sellerUsername;
    private String extension;


    public FileProduct(String fileName, String address, String sellerUsername, String extension, int price, Company company, Category category) {
        super(fileName, company, price, category);
        this.address = address;
        this.sellerUsername = sellerUsername;
        super.setAvailableCount(2100000000);
        super.addSeller((Seller) User.getUserByUserName(sellerUsername));

        this.extension = extension;
        allProductsInList.add(this);
        allProductsInQueueExpect.remove(this);
        this.setProductState(State.ProductState.CONFIRMED);
    }


    public static void removeFile(FileProduct fileProduct) {
        allProductsInList.remove(fileProduct);
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }


    public String getAddress() {
        return address;
    }


    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

}