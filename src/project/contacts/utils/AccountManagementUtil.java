package project.contacts.utils;

import project.contacts.contact.Phonebook;
import project.contacts.account.Account;

import java.io.IOException;
import java.util.Scanner;

public class AccountManagementUtil {
    public static final int MAX_RETRY_ATTEMPTS = 5;

    public static void logIn() {
        int attempts = MAX_RETRY_ATTEMPTS;
        Scanner scanner = new Scanner(System.in);

        String username;
        String password;

        boolean accountExists;
        boolean checkCredentials;

        Logger.printInfoMessage("Phonebook started, please enter credentials to log in! \n");
        while (attempts != 0) {
            Logger.printInfoMessage("Enter username: ");
            username = scanner.nextLine().trim();
            Logger.printInfoMessage("Enter password: ");
            password = scanner.nextLine().trim();

            Account account = new Account(username, password);

            accountExists = FileManagementUtil.accountExist(account);

            if (!accountExists) {
                attempts--;
                if (attempts == 0) {
                    ProgramManagementUtil.stopTheSystem("You ran out of attempts! Closing Phonebook...");
                }
                Logger.printErrorMessage("You have entered an invalid username or password! " +
                        "Remaining attempts: " + attempts + "\n");
            } else {
                checkCredentials = FileManagementUtil.verifyLoginCredentials(account, account.getPassword());
                if (checkCredentials) {
                    attempts = MAX_RETRY_ATTEMPTS;
                    FileManagementUtil.createPhonebookFileForAccount(account);

                    Phonebook phonebook = FileManagementUtil.readPhonebookFromFile(account);
                    account = new Account(username, password, phonebook);

                    Logger.printSuccessMessage("You have successfully logged in with user " + account.getUsername());
                    ProgramManagementUtil.startProgram(account);
                } else {
                    attempts--;
                    if (attempts == 0) {
                        ProgramManagementUtil.stopTheSystem("You ran out of attempts! Closing Phonebook...");
                    }
                    Logger.printErrorMessage("You have entered an invalid username or password! " +
                            "Remaining attempts: " + attempts + "\n");
                }
            }
        }
    }

    public static void logOut() {
        Logger.printSuccessMessage("You have successfully logged out!");
        ProgramManagementUtil.cleanConsole();
        AccountManagementUtil.logIn();
    }

    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);
        int attempts = MAX_RETRY_ATTEMPTS;
        String username;
        String password;
        boolean userExists;

        while (attempts != 0) {
            Logger.printInfoMessage("Enter username: ");
            username = scanner.nextLine().trim();
            Logger.printInfoMessage("Enter password: ");
            password = scanner.nextLine().trim();

            Account newAccount = new Account (username, password);
            userExists = FileManagementUtil.accountExist(newAccount);

            if (userExists || username.contains(",")) {
                Logger.printErrorMessage("That username is already taken or invalid. Please choose another one! \n");
            } else {
                if (!ValidationUtil.validateUsername(username) || !ValidationUtil.validatePassword(password)) {
                    attempts--;
                } else {
                    newAccount = new Account(username, password);
                    FileManagementUtil.addAccountToFile(newAccount);
                    Logger.printSuccessMessage("Account " + newAccount.getUsername() + " is added successfully!");
                    ProgramManagementUtil.cleanConsole();
                    break;
                }
            }
        }
    }

    public static void changePassword(Account account) {
        Scanner scanner = new Scanner(System.in);
        int attempts = MAX_RETRY_ATTEMPTS;
        String oldPassword;
        String newPassword;
        String repeatNewPassword;

        boolean oldPasswordMatches = false;

        while (attempts != 0) {
            Logger.printInfoMessage("Enter your old password: ");
            oldPassword = scanner.nextLine().trim();
            Logger.printInfoMessage("Enter your new password: ");
            newPassword = scanner.nextLine().trim();
            Logger.printInfoMessage("Repeat your new password: ");
            repeatNewPassword = scanner.nextLine().trim();

            if (oldPassword.equals(account.getPassword())) {
                oldPasswordMatches = true;
            }

            if (!oldPasswordMatches) {
                attempts--;
                Logger.printErrorMessage("You have entered incorrect password. Remaining attempts: " + attempts + "\n");
            } else {
                if (!ValidationUtil.validatePassword(newPassword)) {
                    attempts--;
                    Logger.printErrorMessage("Remaining attempts: " + attempts + "\n");
                } else {
                    if (!newPassword.equals(repeatNewPassword)) {
                        attempts--;
                        Logger.printErrorMessage("The new passwords you have entered do not match \n");
                        Logger.printErrorMessage("Remaining attempts: " + attempts + "\n");
                    } else {
                        account.setPassword(newPassword);
                        Logger.printSuccessMessage("Password is changed successfully!");
                        ProgramManagementUtil.cleanConsole();
                        break;
                    }
                }
            }
        }
    }
}
