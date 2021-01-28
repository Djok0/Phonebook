package project.contacts.utils;

import project.contacts.account.Account;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class AccountManagementUtil {

    // Disabling the option to create an object from AccountManagementUtil
    private AccountManagementUtil() {
    }

    public static final int MAX_RETRY_ATTEMPTS = 5;
    public static Scanner scanner = new Scanner(System.in);

    public static void logIn() {
        scanner = new Scanner(System.in);
        int attempts = MAX_RETRY_ATTEMPTS;

        while (attempts != 0) {
            System.out.println("Enter username: ");
            String name = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            String path = "src/resources/project/contacts/account/credentials";
            String fileName = name + "_credentials.txt";
            String pathFileName = path + "/" + fileName;
            boolean userExists = userExists(pathFileName);

            if (!userExists) {
                attempts--;
                if (attempts == 0) {
                    ProgramManagementUtil.stopTheSystem("You have locked your account. " +
                            "Please contact Security Administrator.");
                }
                System.out.println("You have entered an invalid username or password! Remaining attempts: " + attempts);
            } else {
                boolean checkCredentials = verifyLoginCredentials(name, password, pathFileName);
                if (checkCredentials) {
                    attempts = MAX_RETRY_ATTEMPTS;
                    System.out.println("You have successfully logged in with user " + name);
                    Account account = new Account(name, password);
                    ProgramManagementUtil.startProgram(account);
                } else {
                    attempts--;
                    if (attempts == 0) {
                        ProgramManagementUtil.stopTheSystem("You have locked your account. " +
                                "Please contact Security Administrator.");
                    }
                    System.out.println("You have entered an invalid username or password! Remaining attempts: " + attempts);
                }
            }
        }
    }

    private static boolean verifyLoginCredentials(String username, String password, String filepath) {
        boolean credentials = false;
        String str;
        String tempUserName = null;
        String tempPassword = null;

        try {
            FileInputStream fstream = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            while ((str = br.readLine()) != null) {
                String[] values = str.split(",");
                tempUserName = values[0];
                tempPassword = values[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        while (attempts != 0) {
            System.out.println("Enter username: ");
            String name = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            String path = "src/resources/project/contacts/account/credentials";
            String fileName = name + "_credentials.txt";
            String pathFileName = path + "/" + fileName;
            boolean userExists = userExists(pathFileName);

            if (userExists) {
                System.out.println("That username is already taken. Please choose another one!");
            } else {
                if (!ValidationUtil.validatePassword(password)) {
                    attempts--;
                } else {
                    Account newAccount = new Account(name, password);
                    try {
                        File accountCredentials = new File(path, fileName);
                        if (accountCredentials.createNewFile()) {
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
                    break;
                }
            }
        }
    }

    public static void changePassword(Account account) {
        int attempts = MAX_RETRY_ATTEMPTS;
        while (attempts != 0) {
            System.out.println("Enter your old password: ");
            String oldPassword = scanner.nextLine();
            Logger.printInfoMessage("Enter your new password");
            String newPassword = scanner.nextLine();
            Logger.printInfoMessage("Repeat your new password");
            String repeatNewPassword = scanner.nextLine();

            String path = "src/resources/project/contacts/account/credentials";
            String fileName = account.getName() + "_credentials.txt";
            String pathFileName = path + "/" + fileName;
            boolean oldPasswordMatches = validateOldPassword(oldPassword, pathFileName);

            if (!oldPasswordMatches) {
                attempts--;
                System.out.println("You have entered incorrect password. Remaining attempts: " + attempts);
            } else {
                if (!ValidationUtil.validatePassword(newPassword)) {
                    attempts--;
                    System.out.println("Remaining attempts: " + attempts);
                } else {
                    if (!newPassword.equals(repeatNewPassword)) {
                        attempts--;
                        System.out.println("The new passwords you have entered do not match");
                        System.out.println("Remaining attempts: " + attempts);
                    } else {
                        File accountCredentials = new File(path, fileName);
                        try {
                            FileWriter storeCredentials = new FileWriter(accountCredentials);
                            storeCredentials.write(account.getName());
                            storeCredentials.write(",");
                            storeCredentials.write(newPassword);
                            storeCredentials.close();
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                        attempts = MAX_RETRY_ATTEMPTS;
                        System.out.println("Password is changed successfully!");
                        break;
                    }
                }
            }
        }
    }

    private static boolean validateOldPassword(String oldPassword, String filePath) {
        boolean oldPasswordMatches = false;
        String str;
        String tempPassword = null;

        try {
            FileInputStream fstream = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            while ((str = br.readLine()) != null) {
                String[] values = str.split(",");
                tempPassword = values[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempPassword.trim().equals(oldPassword.trim())) {
            oldPasswordMatches = true;
        }
        return oldPasswordMatches;
    }

    public static void logOut() {
        System.out.println("You have successfully logged out!");
        AccountManagementUtil.logIn();
    }
}
