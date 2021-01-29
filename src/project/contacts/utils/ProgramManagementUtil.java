package project.contacts.utils;

import project.contacts.account.Account;
import project.contacts.contact.Phonebook;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProgramManagementUtil {
    private static final Scanner scanner = new Scanner(System.in);
    public static final int INVALID_CHOICE = -1;

    // Disabling the option to create an object from ProgramManagementUtil
    private ProgramManagementUtil() {
    }

    public static void startProgram(Account account) {
        int choice;
        do {
            try {
                ProgramManagementUtil.printMainMenu(account);
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                choice = INVALID_CHOICE;
            }
            switch (choice) {
                case 1:
                    Phonebook.addContact();
                    break;
                case 2:
                    System.out.println("Option 2 not available yet");
                    break;
                case 3:
                    Phonebook.printAllContactsFromFile();
                    break;
                case 4:
                    Phonebook.searchRecordByName();
                    break;
                case 5:
                    Phonebook.searchRecordByPhoneNumber();
                    break;
                case 6:
                    AccountManagementUtil.createAccount();
                    break;
                case 7:
                    AccountManagementUtil.changePassword(account);
                    break;
                case 8:
                    AccountManagementUtil.logOut();
                    break;
                case 0:
                    Logger.printInfoMessage("See you soon!");
                    Logger.printInfoMessage("App made by Radi and Georgi");
                    System.exit(0);
                    break;
                default:
                    Logger.printErrorMessage("You have entered an invalid option! " +
                            "Please choose from the options listed in the menu");
            }
        } while (choice != 0);
    }

    public static void printMainMenu(Account account) {
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  Welcome " + account.getName());
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  Menu:                                        |");
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  1. Add a record                              |");
        System.out.println("|  2. Edit a record                             |");
        System.out.println("|  3. View All records                          |");
        System.out.println("|  4. View Specific record (search by name)     |");
        System.out.println("|  5. View Specific record (search by number)   |");
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  6. Create a new account                      |");
        System.out.println("|  7. Change password                           |");
        System.out.println("|  8. Log out                                   |");
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  0. Exit                                      |");
        System.out.println(" ----------------------------------------------- ");
    }

    public static void stopTheSystem(String message) {
        System.err.println(message);
        System.exit(-1);
    }
}
