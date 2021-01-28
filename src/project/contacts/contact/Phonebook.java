package project.contacts.contact;

import project.contacts.account.Account;
import project.contacts.utils.ValidationUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Phonebook {

    public static final String FIRST_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}";
    public static final String LAST_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}-?[A-Z]?[a-zA-Z]*";
    public static final String PHONE_NUMBER_PATTERN = "[0][8]{1}[7,8,9]{1}\\d{7}";
    public static final String COUNTRY_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*";
    public static final String CITY_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*";
    public static final String STREET_NAME_PATTERN = "([A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*|[1-9]{1}[0-9]{0,4})";
    public static final String STREET_NUMBER_PATTERN = "[1-9]{1}[0-9]{0,3}|[1-9]{1}[0-9]{0,3}[A-Z]{1}";

    private static List<Contact> contacts = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static Birthday birthday;

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

        int dayOfBirth = 0;
        int monthOfBirth = 0;
        int yearOfBirth = 0;

        String firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
        String lastName = ValidationUtil.validateStringFromUserInput("Last Name", LAST_NAME_PATTERN);

        System.out.println("Do you want to enter Contact's personal phone number? [Y/N]");
        String personalPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (personalPhoneNumberChoice.equals("Y")) {
            personalNumber = ValidationUtil.validateStringFromUserInput("Personal Number", PHONE_NUMBER_PATTERN);
        }
        System.out.println("Do you want to enter Contact's work phone number? [Y/N]");
        String workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            workNumber = ValidationUtil.validateStringFromUserInput("Work Number", PHONE_NUMBER_PATTERN);
        }
        System.out.println("Do you want to enter Contact's address? [Y/N]");
        String addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
            city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
            streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
            streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
        }
        System.out.println("Do you want to enter Contact's birthday? [Y/N]");
        String birthdayChoice = scanner.nextLine().toUpperCase();
        if (birthdayChoice.equals("Y")) {
            birthday = ValidationUtil.validateBirthdayFromUserInput();

            dayOfBirth = birthday.getDayOfBirth();
            monthOfBirth = birthday.getMonthOfBirth();
            yearOfBirth = birthday.getYearOfBirth();
        }
        Address address = new Address(country, city, streetName, streetNumber);
        Contact contact = new Contact(firstName, lastName, birthday, address, personalNumber, workNumber);
        contacts.add(contact);
        System.out.println("Contact added successfully!");

        String path = "src/resources/project/contacts/contact";
        String fileName = "phonebook.txt";
        String pathFileName = path + "/" + fileName;

        try {
            File phonebook = new File(path, fileName);
            if (!phonebook.createNewFile()) {
                try {
                    FileWriter storeCredentials = new FileWriter(pathFileName);
                    storeCredentials.write(firstName);
                    storeCredentials.write(",");
                    storeCredentials.write(lastName);
                    storeCredentials.write(",");
                    if (personalNumber != null) {
                        storeCredentials.write(personalNumber);
                        storeCredentials.write(",");
                    }
                    if (workNumber != null) {
                        storeCredentials.write(workNumber);
                        storeCredentials.write(",");
                    }
                    if (country != null) {
                        storeCredentials.write(country);
                        storeCredentials.write(",");
                    }
                    if (city != null) {
                        storeCredentials.write(city);
                        storeCredentials.write(",");
                    }
                    if (streetName != null) {
                        storeCredentials.write(streetName);
                        storeCredentials.write(",");
                    }
                    if (streetNumber != null) {
                        storeCredentials.write(streetNumber);
                        storeCredentials.write(",");
                    }
                    if (streetNumber != null) {
                        storeCredentials.write(streetNumber);
                        storeCredentials.write(",");
                    }
                    storeCredentials.write(dayOfBirth);         // Null check
                    storeCredentials.write(",");
                    storeCredentials.write(monthOfBirth);       // Null check
                    storeCredentials.write(",");
                    storeCredentials.write(yearOfBirth);        // Null check
                    storeCredentials.write("\r\n");
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

