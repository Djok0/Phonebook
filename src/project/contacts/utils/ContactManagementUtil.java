package project.contacts.utils;

import project.contacts.account.Account;
import project.contacts.contact.Contact;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactManagementUtil {
    private static Scanner scanner = new Scanner(System.in);

    // Disabling the option to create an object from ContactManagementUtil
    private ContactManagementUtil() {
    }

    public static void addContact(Account account) throws IOException {
        List<Contact> contacts = new ArrayList<>();

        String firstName;
        String lastName;

        String personalNumber = null;
        String workNumber = null;

        String country = null;
        String city = null;
        String streetName = null;
        String streetNumber = null;

        int dayOfBirth = 0;
        int monthOfBirth = 0;
        int yearOfBirth = 0;

        System.out.println("Enter Contact's frist name: ");
        firstName = scanner.nextLine();
        System.out.println("Enter Contact's last name: ");
        lastName = scanner.nextLine();
        System.out.println("Do you want to enter Contact's personal phone number? [Y/N]");
        String personalPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (personalPhoneNumberChoice.equals("Y")) {
            System.out.println("Enter Contact's personal number: ");
            personalNumber = scanner.nextLine();
        }
        System.out.println("Do you want to enter Contact's work phone number? [Y/N]");
        String workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            System.out.println("Enter Contact's work number: ");
            workNumber = scanner.nextLine();
        }
        System.out.println("Do you want to enter Contact's address? [Y/N]");
        String addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            System.out.println("Enter Contact's county: ");
            country = scanner.nextLine();
            System.out.println("Enter Contact's city: ");
            city = scanner.nextLine();
            System.out.println("Enter Contact's Street Name: ");
            streetName = scanner.nextLine();
            System.out.println("Enter Contact's Street Number: ");
            streetNumber = scanner.nextLine();
        }
        System.out.println("Do you want to enter Contact's birthday? [Y/N]");
        String birthdayChoice = scanner.nextLine().toUpperCase();
        if (birthdayChoice.equals("Y")) {
            System.out.println("Enter Contact's Day of Birth: ");
            dayOfBirth = scanner.nextInt();
            System.out.println("Enter Contact's Month of Birth: ");
            monthOfBirth = scanner.nextInt();
            System.out.println("Enter Contact's Year of Birth: ");
            yearOfBirth = scanner.nextInt();
        }
        Contact contact = new Contact(firstName, lastName, dayOfBirth,
        monthOfBirth, yearOfBirth, country, city, streetName,
                streetNumber, personalNumber, workNumber);
        contacts.add(contact);
        addContactToContactList(contact);
    }

    public static void addContactToContactList(Contact contact) throws IOException {
        File contactsDetails = new File("src/resources/project/contacts/contact/ContactsDetails.txt");
//        FileWriter myWriter = new FileWriter(contactsDetails);
//        myWriter.write(contact);
    }

//    public static void addContact(List<Contact> contacts, Account account) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter Contact's first name");
//        String firstName = scanner.nextLine();
//        System.out.println("Enter Contact's last name");
//        String lastName = scanner.nextLine();
//        Contact contact = new Contact(firstName, lastName);
//        contacts.add(contact);
//        System.out.println("Contact added successfully");
//        ProgramManagementUtil.startProgram(account);
//    }
//
//    public static void getRecords(List<Contact> contacts, Account account) {
//        for (Contact contact : contacts) {
//            System.out.println(contact.toString());
//        }
//        ProgramManagementUtil.startProgram(account);
//    }

}
