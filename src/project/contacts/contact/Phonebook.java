package project.contacts.contact;

import project.contacts.contact.Contact;

public class Phonebook extends Contact {
    private String personalNumber;
    private String workNumber;

    public Phonebook(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Phonebook(String personalNumber, String workNumber) {
        this.personalNumber = personalNumber;
        this.workNumber = workNumber;
    }

    public Phonebook(String firstName, String lastName, String personalNumber) {
        super(firstName, lastName);
        this.personalNumber = personalNumber;
    }

    public Phonebook(String firstName, String lastName, String personalNumber, String workNumber) {
        super(firstName, lastName);
        this.personalNumber = personalNumber;
        this.workNumber = workNumber;
    }

    // Getters and Setters
    public String getPersonalNumber() {
        return personalNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }
}
