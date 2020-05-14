package View;

import java.util.HashMap;

public class OffPanel extends Menu {
    public OffPanel(Menu parent, String name) {
        super(parent, name);
        HashMap<Integer, Menu> submenus = new HashMap<Integer, Menu>();
        submenus.put(1, getOffs());
        submenus.put(2, new ProductPanel(this, "product Panel"));
        submenus.put(3, getFiltering());
        submenus.put(4, getSorting());
        setSubmenus(submenus);
    }

    private Menu getOffs() {

        return new Menu(this, "Offs") {
            @Override
            public void show() {
                processor.showOffs();

            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    public Menu getFiltering() {
        return new Menu(this, "filtering") {
            @Override
            public void show() {
                System.out.println("show available filters");
                System.out.println("select category [category name]");
                System.out.println("filter by []");
                System.out.println("current filters");
                System.out.println("disable filter [filter]/[feature name]");
                System.out.println("back");
                String command = scanner.nextLine();
                processor.filteringProcess(command);
                if (!command.equalsIgnoreCase("back"))
                    this.show();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

    private Menu getSorting() {

        return new Menu(this, "sorting") {
            @Override
            public void show() {
                System.out.println("show available sorts");
                System.out.println("sort [sort type]");
                System.out.println("current sort");
                System.out.println("disable sort");
                System.out.println("back");
                String command = scanner.nextLine();
                processor.sortingProcess(command);
                if (!command.equalsIgnoreCase("back"))
                    this.show();
            }

            @Override
            public void run() {
                this.parent.show();
                this.parent.run();
            }
        };
    }

}
