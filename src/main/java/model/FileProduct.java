package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileProduct implements Serializable {
    private static final ArrayList<FileProduct> allFiles = new ArrayList<>();
    private int price;
    private String fileName;
    private String address;
    private String sellerUsername;
    private String extension;


    public FileProduct(String fileName, String address, String sellerUsername, String extension, int price) {
        this.fileName = fileName;
        this.price = price;
        this.address = address;
        this.sellerUsername = sellerUsername;
        this.extension = extension;
        allFiles.add(this);
    }

    public static List<FileProduct> getAllFiles() {
        return Collections.unmodifiableList(allFiles);
    }

    public static void removeFile(FileProduct fileProduct) {
        allFiles.remove(fileProduct);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }


    public String getFileName() {
        return fileName;
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


    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

}