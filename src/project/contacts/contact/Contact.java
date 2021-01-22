package project.contacts.contact;

public class Contact {
    private String firstName;
    private String lastName;
    private int contactId;

    public Contact() {
        contactId = contactId++;
    }

    public Contact(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getContactId() {
        return contactId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
