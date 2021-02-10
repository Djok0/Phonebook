package project.contacts.utils;

import project.contacts.account.Account;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Scanner;

public class AccountManagementUtil {

    public static final String PATH_TO_THE_FILES_WITH_ACCOUNT_CREDENTIALS = "src/resources/project/contacts/account/credentials";
    public static final int PASSWORD_PLACE_FROM_FILE = 1;

    // Disabling the option to create an object from AccountManagementUtil
    private AccountManagementUtil() {
    }

    public static final int MAX_RETRY_ATTEMPTS = 5;
    public static Scanner scanner = new Scanner(System.in);

    public static void logIn() {
        int attempts = MAX_RETRY_ATTEMPTS;
        String name;
        String password;
        String encodedPassword;
        Account account;

        String fileName;
        String pathFileName;

        boolean userExists;
        boolean checkCredentials;

        while (attempts != 0) {
            Logger.printInfoMessage("Enter username: ");
            name = scanner.nextLine();
            Logger.printInfoMessage("Enter password: ");
            password = scanner.nextLine();

            fileName = name + "_credentials.txt";
            pathFileName = PATH_TO_THE_FILES_WITH_ACCOUNT_CREDENTIALS + "/" + fileName;
            userExists = userExists(pathFileName);

            if (!userExists) {
                attempts--;
                if (attempts == 0) {
                    ProgramManagementUtil.stopTheSystem("You ran out of attempts!");
                }
                Logger.printErrorMessage("You have entered an invalid username or password! " +
                        "Remaining attempts: " + attempts);
            } else {
                encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
                checkCredentials = verifyLoginCredentials(name, encodedPassword, pathFileName);
                if (checkCredentials) {
                    attempts = MAX_RETRY_ATTEMPTS;
                    Logger.printSuccessMessage("You have successfully logged in with user " + name);
                    account = new Account(name, password);
                    ProgramManagementUtil.startProgram(account);
                } else {
                    attempts--;
                    if (attempts == 0) {
                        ProgramManagementUtil.stopTheSystem("You ran out of attempts!");
                    }
                    Logger.printErrorMessage("You have entered an invalid username or password! " +
                            "Remaining attempts: " + attempts);
                }
            }
        }
    }

    private static boolean verifyLoginCredentials(String username, String password, String filepath) {
        boolean credentials = false;
        String str;
        String tempUserName = "";
        String tempPassword = "";
        FileInputStream fstream;
        BufferedReader br;
        String[] values;

        try {
            fstream = new FileInputStream(filepath);
            br = new BufferedReader(new InputStreamReader(fstream));
            while ((str = br.readLine()) != null) {
                values = str.split(",");
                tempUserName = values[0];
                tempPassword = values[1];
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred");
        }
        if (tempUserName.trim().equals(username.trim()) && tempPassword.trim().equals(password.trim())) {
            credentials = true;
        }
        return credentials;
    }

    private static boolean userExists(String pathFileName) {
        File user = new File(pathFileName);
        return user.exists() && !user.isDirectory();
    }

    public static void createAccount() {
        int attempts = MAX_RETRY_ATTEMPTS;
        String name;
        String password;
        String encodedPassword;
        String path;
        String fileName;
        String pathFileName;
        boolean userExists;
        Account newAccount;

        while (attempts != 0) {
            System.out.println("Enter username: ");
            name = scanner.nextLine().trim();
            System.out.println("Enter password: ");
            password = scanner.nextLine();

            path = PATH_TO_THE_FILES_WITH_ACCOUNT_CREDENTIALS;
            fileName = name + "_credentials.txt";
            pathFileName = path + "/" + fileName;
            userExists = userExists(pathFileName);

            if (userExists || name.contains(",")) {
                Logger.printErrorMessage("That username is already taken or invalid. Please choose another one!");
            } else {
                if (!ValidationUtil.validateUsername(name) || !ValidationUtil.validatePassword(password)) {
                    attempts--;
                } else {
                    encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
                    newAccount = new Account(name, encodedPassword);
                    addAccountToFile(path, fileName, pathFileName, newAccount);
                    Logger.printSuccessMessage("Account " + newAccount.getName() + " is added successfully!");
                    // System.out.println("Account " + newAccount.getName() + " is added successfully!");
                    break;
                }
            }
        }
    }

    public static void addAccountToFile(String path, String fileName, String pathFileName, Account newAccount) {
        File accountCredentials;
        FileWriter storeCredentials;

        try {
            accountCredentials = new File(path, fileName);
            if (accountCredentials.createNewFile()) {
                storeCredentials = new FileWriter(pathFileName);
                storeCredentials.write(newAccount.getName());
                storeCredentials.write(",");
                storeCredentials.write(newAccount.getPassword());
                storeCredentials.close();
            } else {
                Logger.printInfoMessage("File already exists.");
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred");
        }
    }

    public static void changePassword(Account account) {
        int attempts = MAX_RETRY_ATTEMPTS;
        String oldPassword;
        String encodedOldPassword;
        String newPassword;
        String repeatNewPassword;
        String encodedNewPassword;

        String path;
        String fileName;
        String pathFileName;
        boolean oldPasswordMatches;

        while (attempts != 0) {
            System.out.println("Enter your old password: ");
            oldPassword = scanner.nextLine();
            Logger.printInfoMessage("Enter your new password");
            newPassword = scanner.nextLine();
            Logger.printInfoMessage("Repeat your new password");
            repeatNewPassword = scanner.nextLine();

            path = PATH_TO_THE_FILES_WITH_ACCOUNT_CREDENTIALS;
            fileName = account.getName() + "_credentials.txt";
            pathFileName = path + "/" + fileName;
            encodedOldPassword = Base64.getEncoder().encodeToString(oldPassword.getBytes());
            oldPasswordMatches = validateOldPassword(encodedOldPassword, pathFileName);
            File accountCredentials;
            FileWriter storeCredentials;

            if (!oldPasswordMatches) {
                attempts--;
                Logger.printErrorMessage("You have entered incorrect password. Remaining attempts: " + attempts);
            } else {
                if (!ValidationUtil.validatePassword(newPassword)) {
                    attempts--;
                    Logger.printInfoMessage("Remaining attempts: " + attempts);
                } else {
                    if (!newPassword.equals(repeatNewPassword)) {
                        attempts--;
                        Logger.printErrorMessage("The new passwords you have entered do not match");
                        Logger.printInfoMessage("Remaining attempts: " + attempts);
                    } else {
                        encodedNewPassword = Base64.getEncoder().encodeToString(newPassword.getBytes());
                        accountCredentials = new File(path, fileName);
                        try {
                            storeCredentials = new FileWriter(accountCredentials);
                            storeCredentials.write(account.getName());
                            storeCredentials.write(",");
                            storeCredentials.write(encodedNewPassword);
                            storeCredentials.close();
                        } catch (IOException e) {
                            Logger.printErrorMessage("Error reading from / writing to file has occurred");
                        }
                        Logger.printSuccessMessage("Password is changed successfully!");
                        break;
                    }
                }
            }
        }
    }

    private static boolean validateOldPassword(String oldPassword, String filePath) {
        boolean oldPasswordMatches = false;
        String str;
        String tempPassword = "";
        FileInputStream fstream;
        BufferedReader br;
        String[] values;

        try {
            fstream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(fstream));
            while ((str = br.readLine()) != null) {
                values = str.split(",");
                tempPassword = values[PASSWORD_PLACE_FROM_FILE];
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred");
        }
        if (tempPassword.trim().equals(oldPassword.trim())) {
            oldPasswordMatches = true;
        }
        return oldPasswordMatches;
    }

    public static void logOut() {
        Logger.printSuccessMessage("You have successfully logged out!");
        AccountManagementUtil.logIn();
    }
}
