package project.contacts.utils;

import project.contacts.account.Account;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountManagementUtil {

    // Disabling the option to create an object from AccountManagementUtil
    private AccountManagementUtil() {
    }

    public static final int MAX_RETRY_ATTEMPTS = 5;

    public static void logIn(Account account) {
        Scanner scanner = new Scanner(System.in);
        int attempts = MAX_RETRY_ATTEMPTS;
        while (attempts > 0) {
            System.out.println("Enter username: ");
            String name = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            if (name.equals(account.getName()) && password.equals(account.getPassword())) {
                Logger.printSuccessMessage("Login successful");
                attempts = MAX_RETRY_ATTEMPTS;
                ProgramManagementUtil.startProgram(account);
            } else {
                attempts--;
                if (attempts != 0) {
                    Logger.printErrorMessage("Unsuccessful login");
                    System.out.println("You have " + attempts + " remaining attempts.");
                } else {
                    Logger.printErrorMessage("You have locked your account! " +
                            "Please contact Security Administrator to unlock it!");
                    System.exit(-1);
                }
            }
        }
    }

    public static Account createAccount(Account account) { // bug: validation for already existing account doesn't work
        int attempts = MAX_RETRY_ATTEMPTS;
        Account newAccount = null;
        while (attempts > 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a name for the new account");
            String name = scanner.nextLine();
            System.out.println("Enter a password for the new account");
            String password = scanner.nextLine();

            if (!validateString(name) && !validateString(password)) {
                attempts--;
                Logger.printErrorMessage("You need to enter valid data! Remaining attempts: " + attempts);
            } else {
                if (!account.getName().equals(name)) {
                    newAccount = new Account(name, password);
                    attempts = MAX_RETRY_ATTEMPTS;
                    System.out.println("Account " + newAccount.getName() + " is added successfully!");
                    ProgramManagementUtil.startProgram(account);
                } else {
                    System.out.println("That name is already taken. Please choose another one!");
                }
            }
        }
        return newAccount;
    }

    public static void changePassword(Account account) {
        int attempts = MAX_RETRY_ATTEMPTS;
        while (attempts > 0) {
            Scanner scanner = new Scanner(System.in);
            Logger.printInfoMessage("Enter your old password");
            String oldPassword = scanner.nextLine();

            if (!oldPassword.equals(account.getPassword())) {
                attempts--;
                System.out.println("You have entered incorrect password. Remaining attempts: " + attempts);
            } else {
                Logger.printInfoMessage("Enter your new password");
                String newPassword = scanner.nextLine();
                Logger.printInfoMessage("Repeat your new password");
                String repeatNewPassword = scanner.nextLine();
                if (!validateString(newPassword) || !newPassword.equals(repeatNewPassword)) {
                    attempts--;
                    Logger.printErrorMessage("You have entered an incorrect password! Remaining attempts: " + attempts);
                } else {
                    account.setPassword(repeatNewPassword);
                    Logger.printSuccessMessage("You have successfully changed your password " +
                            "and are automatically logged out");
                    attempts = MAX_RETRY_ATTEMPTS;
                    AccountManagementUtil.logIn(account);
                }
            }
        }
    }

    public static boolean validateString(String string) {
        return string != null && !string.isEmpty();
    }
}
