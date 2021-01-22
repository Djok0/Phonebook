package project.contacts.account;

import project.contacts.utils.Logger;

public class Account {
    private String name;
    private String password;

    public Account(String name, String password) {
        if (name == null || name.isEmpty()) {
            Logger.printErrorMessage("You need to enter a username!");
        }
        if (password == null || password.isEmpty()) {
            Logger.printErrorMessage("You need to enter a password!");
        }
        this.name = name;
        this.password = password;
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
}
