package View;

public class Purchase extends Menu {
    private String userName;

    public Purchase(Menu parent, String name) {
        super(parent, name);
        doPurchase();
    }

    private void doPurchase(){
        receiveInformation();
        discountCode();
        payment();
    }

    private void receiveInformation(){

    }

    private void discountCode(){

    }
    private void payment(){


    }

}
