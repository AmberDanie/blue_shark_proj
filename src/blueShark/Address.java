package blueShark;

final public class Address {
    final private String city;
    final private int zipCode;
    final private String street;
    final private int buildingNumber;
    final private char buildingLetter;
    final private int apartmentNumber;

    final public static Address EMPTY_ADDRESS = new Address("", 0,
            "", 0, '-', 0);

    public Address(String city, int zipCode, String street, int buildingNumber, char buildingLetter, int apartmentNumber) {
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.buildingLetter = buildingLetter;
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public char getBuildingLetter() {
        return buildingLetter;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }
}