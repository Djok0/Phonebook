package project.contacts.contact;

public class AddressBook extends Contact {
    private String address;

    public AddressBook(String address) {
        this.address = address;
    }

    public AddressBook(String firstName, String lastName, String address) {
        super(firstName, lastName);
        this.address = address;
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
