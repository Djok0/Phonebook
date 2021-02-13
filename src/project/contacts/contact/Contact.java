package project.contacts.contact;

public class Contact implements Comparable {

    private String firstName;
    private String lastName;
    private String personalPhoneNumber;

    private String workPhoneNumber;
    private Address address;
    private Birthday birthday;

    public Contact(String firstName, String lastName, String personalPhoneNumber,
                   String workPhoneNumber, Address address, Birthday birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalPhoneNumber = personalPhoneNumber;
        this.workPhoneNumber = workPhoneNumber;
        this.address = address;
        this.birthday = birthday;
    }

    @Override
    public int compareTo(Object o) {
        int compare = this.getFirstName().compareToIgnoreCase(((Contact) o).getFirstName());
        return compare == 0 ? getLastName().compareToIgnoreCase(((Contact) o).getLastName()) : compare;
    }

    @Override
    public String toString() {
        return "[Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalPhoneNumber='" + personalPhoneNumber + '\'' +
                ", workPhoneNumber='" + workPhoneNumber + '\'' +
                address +
                birthday +
                "}]";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalPhoneNumber() {
        return personalPhoneNumber;
    }

    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }
}
