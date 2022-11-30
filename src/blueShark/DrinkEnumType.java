package blueShark;

public enum DrinkEnumType {
    // alco
    VODKA(true, 40), BLACK_RUSSIAN(true, 27),
    WINE(true, 14), CHAMPAGNE(true, 12),
    BLOODY_MARY(true, 6), BEER(true, 7.5),
    RUM(true, 40), WHISKEY(true, 40),
    MANHATTAN(true, 32.5), MOJITO(true, 7.2),
    PINA_COLADA(true, 22.5), BLUE_LAGOON(true, 12.5),
    // soft
    COLA(), TEA(), COFFEE(), WATER(), PELICAN(), GRENADINE(), ORANGE_JUICE(), MILKSHAKE();

    private final boolean alcoholic;
    private final double degrees;

    DrinkEnumType() {
        this.alcoholic = false;
        this.degrees = 0;
    }

    DrinkEnumType(boolean alcoholic, double degrees) {
        this.alcoholic = alcoholic;
        this.degrees = degrees;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }
    public String getDegrees() {
        return String.valueOf(degrees);
    }
}