package project.contacts.account;

import project.contacts.contact.Phonebook;
import project.contacts.utils.Logger;

public class Account {
    private String name;
    private String password;
    private Phonebook phonebook;

    public Account(String name, String password, Phonebook phonebook) {
        if (name == null || name.isEmpty()) {
            Logger.printErrorMessage("You need to enter a username!");
        }
        if (password == null || password.isEmpty()) {
            Logger.printErrorMessage("You need to enter a password!");
        }
        if (phonebook == null) {
            // TODO
            Logger.printErrorMessage("TBD!");
        }
        this.name = name;
        this.password = password;
        this.phonebook = phonebook;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
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