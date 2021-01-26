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

        firstName = Phonebook.validateFirstName("Enter Contact's first name: ");
        lastName = Phonebook.validateSecondName("Enter Contact's last name: ");

        System.out.println("Do you want to enter Contact's personal phone number? [Y/N]");
        String personalPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (personalPhoneNumberChoice.equals("Y")) {
            personalNumber = Phonebook.validateNumber("Enter Contact's personal number: ");
        }
        System.out.println("Do you want to enter Contact's work phone number? [Y/N]");
        String workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            workNumber = Phonebook.validateNumber("Enter Contact's work number: ");
        }
        System.out.println("Do you want to enter Contact's address? [Y/N]");
        String addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            country = validateCountryName("Enter Contact's county: ");
            city = validateCityName("Enter Contact's city: ");
            streetName = validateStreetName("Enter Contact's Street Name: ");
            streetNumber = validateStreetNumber("Enter Contact's Street Number: ");
        }
        System.out.println("Do you want to enter Contact's birthday? [Y/N]");
        String birthdayChoice = scanner.nextLine().toUpperCase();
        if (birthdayChoice.equals("Y")) {
            dayOfBirth = validateDay("Enter Contact's Day of Birth: ");
            monthOfBirth = validateMonth("Enter Contact's Month of Birth: ");
            yearOfBirth = validateYear("Enter Contact's Year of Birth: ");
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

    public static String validateFirstName(String message) {
        System.out.println(message);
        while (!scanner.hasNext("[A-Z]{1}[a-zA-Z]{1,}")) {
            System.out.println("You have entered an invalid " + firstName);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }

    public static String validateSecondName(String message) {
        System.out.println(message);
        while (!scanner.hasNext("[A-Z]{1}[a-zA-Z]{1,}-?[A-Z]?[a-zA-Z]*")) {
            System.out.println("You have entered an invalid " + lastName);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }

    public static String validateNumber(String message) {
        System.out.println(message);
        while (!scanner.hasNext("[0][8]{1}[7,8,9]{1}\\d{7}")) {
            System.out.println("You have entered an invalid " + telephonNumber);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }

    public static String validateCountryName(String message) {
        System.out.println(message);
        while (!scanner.hasNext("[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*")) {
            System.out.println("You have entered an invalid " + country);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }

    public static String validateCityName(String message) {
        System.out.println(message);
        while (!scanner.hasNext("[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*")) {
            System.out.println("You have entered an invalid " + city);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }

    public static String validateStreetName(String message) {
        System.out.println(message);
        while (!scanner.hasNext("([A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*|[1-9]{1}[0-9]{0,4})")) {
            System.out.println("You have entered an invalid " + streetName);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }

    public static String validateStreetNumber(String message) {
        System.out.println(message);
        while (!scanner.hasNext("[1-9]{1}[0-9]{0,3}[A-Z]{1}")) {
            System.out.println("You have entered an invalid " + streetNumber);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }

    public static int validateDay(String message) {
        System.out.println(message);
        while (!scanner.hasNext("0[1-9]|[12]\\d|3[01]")){
            System.out.println("You have entered an invalid " + dayOfBirth);
            Integer.parseInt(scanner.nextLine());
        }
        return Integer.parseInt(scanner.nextLine());
    }

    public static int validateMonth(String message) {
        System.out.println(message);
        while (!scanner.hasNext("0[1-9]|1[012]")){
            System.out.println("You have entered an invalid " + dayOfBirth);
            Integer.parseInt(scanner.nextLine());
        }
        return Integer.parseInt(scanner.nextLine());
    }

    public static int validateYear(String message) {
        System.out.println(message);
        while (!scanner.hasNext("^(19|20)\\d{2}$\n")){
            System.out.println("You have entered an invalid " + dayOfBirth);
            Integer.parseInt(scanner.nextLine());
        }
        return Integer.parseInt(scanner.nextLine());
    }
}

// for Int use Integer.parseInt(scanner.nextLine());
// (0[1-9]|[12]\d|3[01]) - 01-31
