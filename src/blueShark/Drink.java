package blueShark;

final public class Drink extends MenuItem implements Alcoholable {
    final private double degrees;
    final private DrinkEnumType type;

    public Drink(String name, String description, DrinkEnumType type) {
        super(name, description);

        this.type = type;
        this.degrees = 0;
    }

    public Drink(String name, String description, DrinkEnumType type, int cost) {
        super(name, description, cost);

        this.type = type;
        this.degrees = 0;
    }

    public Drink(String name, String description, DrinkEnumType type, int cost, double degrees) {
        super(name, description, cost);

        this.type = type;

        if (degrees > 0 && !type.isAlcoholic())
            throw new IllegalArgumentException("Cannot add alcohol into non-alcoholic drink. YOLO");

        this.degrees = degrees;
    }

    @Override
    public double getDegrees() {
        return degrees;
    }

    @Override
    public boolean isAlcoholic() {
        return this.type.isAlcoholic();
    }

    public DrinkEnumType getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + this.getType() + ", " + (this.isAlcoholic() ? "alcoholic" : "non-alcoholic");
    }
}