package project.contacts.utils;

import project.contacts.account.Account;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AccountManagementUtil {

    // Disabling the option to create an object from AccountManagementUtil
    private AccountManagementUtil() {
    }

    public static final int MAX_RETRY_ATTEMPTS = 5;
    public static Scanner scanner = new Scanner(System.in);

    public static void logIn(Account account) {
        scanner = new Scanner(System.in);
        int attempts = MAX_RETRY_ATTEMPTS;
        while (attempts > 0) {
            System.out.println("Enter username: ");
            String name = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

//            String path = "src/project/contacts/credentialsFiles";
//            String fileName = account.getName() + "_credentials.txt";
//            String pathFileName = path + "/" + fileName;
//            verifyLogin(account.getName(), account.getPassword(), pathFileName);

            if (name.equals(account.getName()) && password.equals(account.getPassword())) {
                Logger.printSuccessMessage("Login successful");
                attempts = MAX_RETRY_ATTEMPTS;
                return;
            } else {
                attempts--;
                if (attempts != 0) {
                    Logger.printErrorMessage("Unsuccessful login");
                    System.out.println("You have " + attempts + " remaining attempts.");
                } else {
                    ProgramManagementUtil.stopTheSystem("You have locked your account! " +
                            "Please contact Security Administrator to unlock it!");
                }
            }
        }
    }

    public static Account createAccount(Account account) { // bug: validation for already existing account doesn't work
        int attempts = MAX_RETRY_ATTEMPTS;
        Account newAccount = null;
        while (attempts > 0) {
            scanner = new Scanner(System.in);
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
                    try {
                        String path = "src/project/contacts/credentialsFiles";
                        String fileName = newAccount.getName() + "_credentials.txt";
                        String pathFileName = path + "/" + fileName;
                        File accountCredentials = new File(path, fileName);
                        if (accountCredentials.createNewFile()) {
                            System.out.println("File created: " + accountCredentials.getName());

                            try {
                                FileWriter storeCredentials = new FileWriter(pathFileName);
                                storeCredentials.write(newAccount.getName());
                                storeCredentials.write(",");
                                storeCredentials.write(newAccount.getPassword());
                                storeCredentials.close();
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("File already exists.");
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    attempts = MAX_RETRY_ATTEMPTS;
                    System.out.println("Account " + newAccount.getName() + " is added successfully!");
//                    ProgramManagementUtil.startProgram(account);        // log in with admin
                    ProgramManagementUtil.startProgram(newAccount);     // log in with new user
                } else {
                    System.out.println("That name is already taken. Please choose another one!");
                }
            }
        }
        return newAccount;
    }

//    public static void verifyLogin(String username, String password, String filepath) {
//
//        boolean found = false;
//        String tempUserName = "";
//        String tempPassword = "";
//
//        try {
//            scanner = new Scanner(new File(filepath));
//            scanner.useDelimiter("[,\n]");
//
//            while (scanner.hasNext() && !found) {
//                tempUserName = scanner.next();
//                tempPassword = scanner.next();
//
//                if(tempUserName.trim().equals(username.trim()) && tempPassword.trim().equals(password.trim())){
//                    found = true;
//                }
//            }
//            scanner.close();
//            System.out.println(found);
//        } catch (Exception e) {
//            System.out.println("Error");
//        }
//    }

    public static void changePassword(Account account) {        // contains bugs
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
                    Logger.printSuccessMessage("You have successfully changed your password!");
                    attempts = MAX_RETRY_ATTEMPTS;
                    ProgramManagementUtil.startProgram(account);
                }
            }
        }
    }

    public static boolean validateString(String someString) {
        return someString != null && !someString.isEmpty();
    }
}
