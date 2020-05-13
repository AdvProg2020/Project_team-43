package View;


import Controller.BuyerProcessor;
import model.CodedDiscount;

public class Purchase extends Menu {
    private String address;
    private String phoneNumber;
    private String discountCode;
    private boolean isDiscountCodeValid = false;

    public Purchase(Menu parent, String name) {
        super(parent, name);
    }

    public void show() {
        if (!BuyerProcessor.getInstance().isCartEmpty())
            doPurchase();
        System.out.println("your cart is empty");
    }

    public void run() {
        this.parent.parent.show();
        this.parent.parent.run();
    }

    private void doPurchase() {
        receiveInformation();
    }

    private void receiveInformation() {

        System.out.println("address: ");
        address = scanner.nextLine();
        System.out.println("phone number: ");
        phoneNumber = scanner.nextLine();
        discountCode();
    }

    private void discountCode() {
        System.out.println("discount code (print none if you don't have): ");
        discountCode = scanner.nextLine();
        while (!discountCode.equals("none") && !isDiscountCodeValid) {
            if (buyerProcessor.checkDiscountCode(discountCode)) {
                isDiscountCodeValid = true;
            } else {
                System.out.println("discount code is invalid");
            }
            System.out.println("discount code (print none if you don't have): ");
            discountCode = scanner.nextLine();
        }
        payment();
    }

    private void payment() {
        double discount = 0;
        if (isDiscountCodeValid)
            discount = CodedDiscount.getDiscountById(discountCode).getDiscountAmount();
        System.out.println(buyerProcessor.payment(address, phoneNumber, discount));

    }

}
