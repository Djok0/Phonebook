package project.contacts.contact;

import project.contacts.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Phonebook {
    private static List<Contact> contacts = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static Contact contact;
    private static Birthday birthday;
    private static Address address;

    private static String firstName = null;
    private static String lastName = null;

    private static int dayOfBirth = 0;
    private static int monthOfBirth = 0;
    private static int yearOfBirth = 0;

    public Phonebook(List<Contact> contacts) {
        Phonebook.contacts = contacts;
    }

    public static void addContact() {

        String personalNumber = null;
        String workNumber = null;

        String country = null;
        String city = null;
        String streetName = null;
        String streetNumber = null;

        firstName = ValidationUtil.validateStringFromUserInput
                ("First Name", "[A-Z]{1}[a-zA-Z]{1,}");
        lastName = ValidationUtil.validateStringFromUserInput
                ("Last Name", "[A-Z]{1}[a-zA-Z]{1,}-?[A-Z]?[a-zA-Z]*");

        System.out.println("Do you want to enter Contact's personal phone number? [Y/N]");
        String personalPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (personalPhoneNumberChoice.equals("Y")) {
            personalNumber = ValidationUtil.validateStringFromUserInput
                    ("Personal Number", "[0][8]{1}[7,8,9]{1}\\d{7}");
        }
        System.out.println("Do you want to enter Contact's work phone number? [Y/N]");
        String workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            workNumber = ValidationUtil.validateStringFromUserInput
                    ("Work Number", "[0][8]{1}[7,8,9]{1}\\d{7}");
        }
        System.out.println("Do you want to enter Contact's address? [Y/N]");
        String addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            country = ValidationUtil.validateStringFromUserInput
                    ("Country", "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*");
            city = ValidationUtil.validateStringFromUserInput
                    ("City", "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*");
            streetName = ValidationUtil.validateStringFromUserInput("Street Name",
                    "([A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*|[1-9]{1}[0-9]{0,4})");
            streetNumber = ValidationUtil.validateStringFromUserInput
                    ("Street Number", "[1-9]{1}[0-9]{0,3}[A-Z]{1}");
        }
        System.out.println("Do you want to enter Contact's birthday? [Y/N]");
        String birthdayChoice = scanner.nextLine().toUpperCase();
        if (birthdayChoice.equals("Y")) {
            dayOfBirth = ValidationUtil.validateIntegerFromUserInput("Day of Birth ", "0[1-9]|[12]\\d|3[01]");
            monthOfBirth = ValidationUtil.validateIntegerFromUserInput("Month of Birth ", "0[1-9]|1[012]");
            yearOfBirth = ValidationUtil.validateIntegerFromUserInput("Year of Birth ", "(19|20)\\d{2}$");
        }
        birthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
        address = new Address(country, city, streetName, streetNumber);
        contact = new Contact(firstName, lastName, birthday, address, personalNumber, workNumber);
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }


    public static void printAllContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public boolean hasContact(Contact contact) {
        return contacts.contains(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        Phonebook.contacts = contacts;
    }
}

