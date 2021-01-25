package project.contacts.contact;

public class Address {
    private String county;
    private String city;
    private String streetName;
    private String streetNumber;

    public Address(String county, String city, String streetName, String streetNumber) {
        this.county = county;
        this.city = city;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                '}';
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
