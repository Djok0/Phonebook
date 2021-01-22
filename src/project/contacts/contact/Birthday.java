package project.contacts.contact;

import java.util.Date;

public class Birthday extends Contact {
    private Date dateOfBirth;

    public Birthday(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Birthday(String firstName, String lastName, Date dateOfBirth) {
        super(firstName, lastName);
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
