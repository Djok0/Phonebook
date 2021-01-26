package project.contacts.contact;

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

    private static String telephonNumber = null;
    private static String personalNumber = null;
    private static String workNumber = null;

    private static String country = null;
    private static String city = null;
    private static String streetName = null;
    private static String streetNumber = null;

    private static int dayOfBirth = 0;
    private static int monthOfBirth = 0;
    private static int yearOfBirth = 0;

    public Phonebook(List<Contact> contacts) {
        Phonebook.contacts = contacts;
    }

    public static void addContact() {

        firstName = Phonebook.validateStringFromUserInput
                ("First Name", "[A-Z]{1}[a-zA-Z]{1,}");
        lastName = Phonebook.validateStringFromUserInput
                ("Last Name", "[A-Z]{1}[a-zA-Z]{1,}-?[A-Z]?[a-zA-Z]*");

        System.out.println("Do you want to enter Contact's personal phone number? [Y/N]");
        String personalPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (personalPhoneNumberChoice.equals("Y")) {
            personalNumber = Phonebook.validateStringFromUserInput
                    ("Personal Number", "[0][8]{1}[7,8,9]{1}\\d{7}");
        }
        System.out.println("Do you want to enter Contact's work phone number? [Y/N]");
        String workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            workNumber = Phonebook.validateStringFromUserInput
                    ("Work Number", "[0][8]{1}[7,8,9]{1}\\d{7}");
        }
        System.out.println("Do you want to enter Contact's address? [Y/N]");
        String addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            country = Phonebook.validateStringFromUserInput
                    ("Country", "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*");
            city = Phonebook.validateStringFromUserInput
                    ("City", "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*");
            streetName = Phonebook.validateStringFromUserInput("Street Name",
                    "([A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*|[1-9]{1}[0-9]{0,4})");
            streetNumber = Phonebook.validateStringFromUserInput
                    ("Street Number", "[1-9]{1}[0-9]{0,3}[A-Z]{1}");
        }
        System.out.println("Do you want to enter Contact's birthday? [Y/N]");
        String birthdayChoice = scanner.nextLine().toUpperCase();
        if (birthdayChoice.equals("Y")) {
            dayOfBirth = validateIntegerFromUserInput("Day of Birth ", "0[1-9]|[12]\\d|3[01]");
            monthOfBirth = validateIntegerFromUserInput("Month of Birth ", "0[1-9]|1[012]");
            yearOfBirth = validateIntegerFromUserInput("Year of Birth ","(19|20)\\d{2}$");
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
    
    public static int validateIntegerFromUserInput (String str, String pattern) {
        System.out.println("Enter Contact's " + str + ":");
        while (!scanner.hasNext(pattern)) {
            System.out.println("You have entered an invalid " + str);
            Integer.parseInt(scanner.nextLine());
        }
        return Integer.parseInt(scanner.nextLine());
    }

    public static String validateStringFromUserInput(String str, String pattern) {
        System.out.println("Enter Contact's " + str + ":");
        while (!scanner.hasNext(pattern)) {
            System.out.println("You have entered an invalid " + str);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }
}

