package View;

public class Purchase extends Menu {
    private String userName;

    public Purchase(Menu parent, String name, String userName) {
        super(parent, name);
        this.userName = userName;
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
