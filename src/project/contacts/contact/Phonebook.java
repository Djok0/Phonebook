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

    public Phonebook(List<Contact> contacts) {
        Phonebook.contacts = contacts;
    }

    public static void addContact() {
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

        System.out.println("Enter Contact's first name: ");
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
        birthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
        address = new Address(country, city, streetName, streetNumber);
        contact = new Contact(firstName, lastName,birthday,address,personalNumber,workNumber);
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public static void printAllContacts() {
        System.out.println(contacts);
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
