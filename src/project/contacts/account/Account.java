package project.contacts.account;

import project.contacts.contact.Phonebook;

public class Account {
    private static final String CREDENTIALS_PATH = "src/resources/project/contacts/account/credentials";
    private static final String PHONEBOOK_PATH = "src/resources/project/contacts/phonebook";

    private final String credentialsPath;
    private final String phonebookPath;

    private String username;
    private String password;
    private Phonebook phonebook;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.credentialsPath = CREDENTIALS_PATH + "/" + this.username + "_credentials.txt";
        this.phonebookPath = PHONEBOOK_PATH + "/" + this.username + "_phonebook.txt";
    }

    public Account(String username, String password, Phonebook phonebook) {
        this(username, password);
        this.phonebook = phonebook;
    }

    public String getCredentialsPath() {
        return credentialsPath;
    }

    public String getPhonebookPath() {
        return phonebookPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Phonebook getPhonebook() {
        return phonebook;
    }

    public void setPhonebook(Phonebook phonebook) {
        this.phonebook = phonebook;
    }
}
