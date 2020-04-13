package View;

import model.Category;
import model.Product;

import java.util.ArrayList;

public class ProductsOfCategory extends Menu {
    private ArrayList<Product> products;
    public ProductsOfCategory(Menu parent, String name) {
        super(parent, name);
        submenus.put(1, new ProductPanel(this, "product Panel"));
    }
    public void show(String categoryName){
        Category category=Category.getCategoryByName(categoryName);
        products=category.getProducts();
        int i=1;
        for (Product product : products) {
            System.out.println((i+1)+". "+product.getProductId()+" "+product.getName());
        }
        System.out.println((products.size()+1)+". back");
    }
    public void run(String categoryName){
        int input=scanner.nextInt();
        if (input<=products.size()){
            submenus.get(1).show();
            ((ProductPanel)submenus.get(1)).run(products.get(input-1).getProductId());
        }
        else {
            this.parent.show();
            this.parent.run();
        }
    }

}
