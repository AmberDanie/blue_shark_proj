package blueShark;

final public class Dish extends MenuItem {
    public Dish(String name, String description) {
        super(name, description);
    }

    public Dish(String name, String description, int cost) {
        super(name, description, cost);
    }

    public Dish(InterfaceWidget itemWidget) {
        super(itemWidget.textLabel.getText(), "", itemWidget.price);
    };
}