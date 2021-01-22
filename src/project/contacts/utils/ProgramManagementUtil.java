package project.contacts.utils;

import project.contacts.account.Account;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProgramManagementUtil {

    // Disabling the option to create an object from ProgramManagementUtil
    private ProgramManagementUtil() {
    }

    public static void startProgram(Account account) {
        ProgramManagementUtil.printMenu(account);

        Scanner scanner = new Scanner(System.in);
        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    AccountManagementUtil.createAccount(account);     // do we want the user to log with the new account?
                case 7:
                    AccountManagementUtil.changePassword(account);
                case 0:
                    Logger.printInfoMessage("See you soon!");
                    Logger.printInfoMessage("App made by Radi and Georgi");
                    System.exit(0);
                    break;
                default:
                    Logger.printErrorMessage("You have entered an invalid option! " +
                            "Please choose from the options listed in the menu");
                    ProgramManagementUtil.startProgram(account);
            }

        } catch (InputMismatchException e) {
            Logger.printErrorMessage("You have entered an invalid option! " +
                    "Please choose from the options listed in the menu");
            ProgramManagementUtil.startProgram(account);
        }
    }

    public static void printMenu(Account account) {
        System.out.println("-------------------------------------------------");
        System.out.println("|  Welcome " + account.getName());
        System.out.println("-------------------------------------------------");
        System.out.println("|  Menu:                                        |");
        System.out.println("-------------------------------------------------");
        System.out.println("|  1. Add a record                              |");
        System.out.println("|  2. View All records                          |");
        System.out.println("|  3. View Specific record (search by id)       |");
        System.out.println("|  4. View Specific record (search by name)     |");
        System.out.println("|  5. View Specific record (search by number)   |");
        System.out.println("|  6. Create a new account                      |");
        System.out.println("|  7. Change password                           |");
        System.out.println("|  0. Exit                                      |");
        System.out.println("-------------------------------------------------");
    }
}
