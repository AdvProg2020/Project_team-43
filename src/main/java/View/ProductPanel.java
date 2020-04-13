package View;


public class ProductPanel extends Menu{
    private String Id;
    public ProductPanel(Menu parent, String name) {
        super(parent, name);
        this.Id = getId();
        submenus.put(1, getDigest());
        submenus.put(2, getAttributes());
        submenus.put(3, getCompare());
        submenus.put(4, getComments());
    }
    private Menu getDigest() {

        return new Menu(this, "digest") {
            @Override
            public void show() {
                String command = scanner.nextLine();
                manager.showDigest(command, Id);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }
    private Menu getAttributes() {

        return new Menu(this, "attributes") {
            @Override
            public void show() {
                manager.showAttributes(Id);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }
    private Menu getCompare() {

        return new Menu(this, "comparing") {
            @Override
            public void show() {
                String secondProductId = scanner.nextLine();
                manager.compareProcess(Id, secondProductId);
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }
    private Menu getComments() {

        return new Menu(this, "comments") {
            @Override
            public void show() {
                manager.showComments(Id);
                /////////////////////////////
                String command = scanner.nextLine();
                // vorodi gereftanesh dar che sooratie???
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }
    private String getId(){
        System.out.println("product's Id : ");
        String Id = scanner.nextLine();
        return Id;
    }
    public void run(String productId){
        this.Id=productId;
    }
}



