package project.contacts.utils;

import project.contacts.account.Account;
import project.contacts.contact.Phonebook;

import java.io.IOException;
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
                ProgramManagementUtil.printMainMenu();
                Logger.printInfoMessage("Please enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                choice = INVALID_CHOICE;
            }
            switch (choice) {
                case 1:
                    Phonebook.addContact(account);
                    break;
                case 2:
                    Phonebook.editRecord(account);
                    break;
                case 3:
                    Phonebook.printAllContactsFromFile(account);
                    break;
                case 4:
                    Phonebook.searchRecordByName(account);
                    break;
                case 5:
                    Phonebook.searchRecordByPhoneNumber(account);
                    break;
                case 6:
                    Phonebook.deleteRecord(account);
                    break;
                case 7:
                    AccountManagementUtil.createAccount();
                    break;
                case 8:
                    AccountManagementUtil.changePassword(account);
                    break;
                case 9:
                    AccountManagementUtil.logOut();
                    break;
                case 0:
                    Logger.printInfoMessage("See you soon! \n");
                    Logger.printInfoMessage("Phonebook application by Radi and Georgi \n");
                    Logger.printInfoMessage("Source Code: github.com/Djok0/Phonebook");
                    System.exit(0);
                    break;
                default:
                    Logger.printErrorMessage("You have entered an invalid option! " +
                            "Please choose from the options listed in the menu \n");
            }
        } while (choice != 0);
    }

    public static void printMainMenu() {
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  Menu:                                        |");
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  1. Add a record                              |");
        System.out.println("|  2. Edit a record                             |");
        System.out.println("|  3. View All records                          |");
        System.out.println("|  4. View Specific record (search by name)     |");
        System.out.println("|  5. View Specific record (search by number)   |");
        System.out.println("|  6. Delete a record                           |");
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  7. Create a new account                      |");
        System.out.println("|  8. Change password                           |");
        System.out.println("|  9. Log out                                   |");
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  0. Exit                                      |");
        System.out.println(" ----------------------------------------------- ");
    }

    public static void openEditMenu(Account account, int row) {
        int choice;
        do {
            try {
                ProgramManagementUtil.printEditMenu();
                Logger.printInfoMessage("Please enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                choice = INVALID_CHOICE;
            }
            switch (choice) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    Phonebook.editPropertyInContact(account, choice, row);
                    break;
                case 9:
                    return;
                case 0:
                    Logger.printInfoMessage("See you soon! \n");
                    Logger.printInfoMessage("Phonebook application by Radi and Georgi \n");
                    Logger.printInfoMessage("Source Code: github.com/Djok0/Phonebook");
                    System.exit(0);
                    break;
                default:
                    Logger.printErrorMessage("You have entered an invalid option! " +
                            "Please choose from the options listed in the menu \n");
            }
        } while (choice != 0);
    }

    public static void printEditMenu() {
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  Edit Menu:                                   |");
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  1. Edit Contact's First Name                 |");
        System.out.println("|  2. Edit Contact's Last Name                  |");
        System.out.println("|  3. Edit Contact's Personal Phone Number      |");
        System.out.println("|  4. Edit Contact's Work Phone Number          |");
        System.out.println("|  5. Edit Contact's Address                    |");
        System.out.println("|  6. Edit Contact's Birthday                   |");
        System.out.println(" ----------------------------------------------- ");
        System.out.println("|  9. Return to Main Menu                       |");
        System.out.println("|  0. Exit                                      |");
        System.out.println(" ----------------------------------------------- ");
    }

    public static void stopTheSystem(String message) {
        System.err.println(message);
        System.exit(-1);
    }
}
