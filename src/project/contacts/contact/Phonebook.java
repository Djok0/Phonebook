package project.contacts.contact;

import project.contacts.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Phonebook {

    public static final String FIRST_NAME_VALIDATION = "[A-Z]{1}[a-zA-Z]{1,}";
    public static final String LAST_NAME_VALIDATION = "[A-Z]{1}[a-zA-Z]{1,}-?[A-Z]?[a-zA-Z]*";
    public static final String TELEPHONE_NUMBER_VALIDATION = "[0][8]{1}[7,8,9]{1}\\d{7}";
    public static final String COUNTRY_NAME_VALIDATION = "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*";
    public static final String CITY_NAME_VALIDATION = "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*";
    public static final String STREET_NAME_VALIDATION = "([A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*|[1-9]{1}[0-9]{0,4})";
    public static final String STREET_NUMBER_VALIDATION = "[1-9]{1}[0-9]{0,3}|[1-9]{1}[0-9]{0,3}[A-Z]{1}";

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
                ("First Name", FIRST_NAME_VALIDATION);
        lastName = ValidationUtil.validateStringFromUserInput
                ("Last Name", LAST_NAME_VALIDATION);

        System.out.println("Do you want to enter Contact's personal phone number? [Y/N]");
        String personalPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (personalPhoneNumberChoice.equals("Y")) {
            personalNumber = ValidationUtil.validateStringFromUserInput
                    ("Personal Number", TELEPHONE_NUMBER_VALIDATION);
        }
        System.out.println("Do you want to enter Contact's work phone number? [Y/N]");
        String workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            workNumber = ValidationUtil.validateStringFromUserInput
                    ("Work Number", TELEPHONE_NUMBER_VALIDATION);
        }
        System.out.println("Do you want to enter Contact's address? [Y/N]");
        String addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            country = ValidationUtil.validateStringFromUserInput
                    ("Country", COUNTRY_NAME_VALIDATION);
            city = ValidationUtil.validateStringFromUserInput
                    ("City", CITY_NAME_VALIDATION);
            streetName = ValidationUtil.validateStringFromUserInput("Street Name",
                    STREET_NAME_VALIDATION);
            streetNumber = ValidationUtil.validateStringFromUserInput
                    ("Street Number", STREET_NUMBER_VALIDATION);
        }
        System.out.println("Do you want to enter Contact's birthday? [Y/N]");
        String birthdayChoice = scanner.nextLine().toUpperCase();
        if (birthdayChoice.equals("Y")) {
            birthday = ValidationUtil.validateBirthdayFromUserInput();
        }
        address = new Address(country, city, streetName, streetNumber);
        contact = new Contact(firstName, lastName, birthday, address, personalNumber, workNumber);
        contacts.add(contact);
        System.out.println("Contact added successfully!");
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

