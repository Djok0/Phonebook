package project.contacts.contact;

import project.contacts.account.Account;
import project.contacts.utils.ValidationUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

        Birthday birthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);

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
        }
        Address address = new Address(country, city, streetName, streetNumber);
        Contact contact = new Contact(firstName, lastName, birthday, address, personalNumber, workNumber);
        contacts.add(contact);
        addContactToFile(contact);
        System.out.println("Contact added successfully!");
    }

    public static void addContactToFile(Contact contact) {
        String path = "src/resources/project/contacts/contact";
        String fileName = "phonebook.txt";
        String pathFileName = path + "/" + fileName;

        try {
            File file = new File(path, fileName);
            if (!file.createNewFile()) {
                try {
                    FileWriter fileWriter = new FileWriter(pathFileName, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(contact.toString());
                    bufferedWriter.newLine();
                    bufferedWriter.close();
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

    public static void printAllContactsFromFile() {
        String path = "src/resources/project/contacts/contact";
        String fileName = "phonebook.txt";
        String pathFileName = path + "/" + fileName;
        int counter = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFileName));
            ArrayList<String> contacts = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                contacts.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            for (String contact : contacts) {
                counter++;
                System.out.println(counter + ". " + contact);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countRowsFromFile(String file) {
        int counter = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            ArrayList<String> contacts = new ArrayList<>();
            String line = bufferedReader.readLine();

            while (line != null) {
                contacts.add(line);
                line = bufferedReader.readLine();
                counter++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public static void searchRecordByName() {
        String firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
        String path = "src/resources/project/contacts/contact";
        String fileName = "phonebook.txt";
        String pathFileName = path + "/" + fileName;
        boolean found = false;
        try {
            Scanner scanner = new Scanner(new File(pathFileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(firstName)) {
                    found = true;
                    System.out.println("A contact with name " + firstName + " exists. Here are the details:");
                    System.out.println(line);
                }
            }
            if (!found) {
                System.out.println("There is no record with name " + firstName + " in the phonebook!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void searchRecordByPhoneNumber() {
        String phoneNumber = ValidationUtil.validateStringFromUserInput("Phone Number", PHONE_NUMBER_PATTERN);
        String path = "src/resources/project/contacts/contact";
        String fileName = "phonebook.txt";
        String pathFileName = path + "/" + fileName;
        boolean found = false;
        try {
            Scanner scanner = new Scanner(new File(pathFileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(phoneNumber)) {
                    found = true;
                    System.out.println(line);
                }
            }
            if (!found) {
                System.out.println("There is no record with number " + phoneNumber + " in the phonebook!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void editRecord() {
        String path = "src/resources/project/contacts/contact";
        String fileName = "phonebook.txt";
        String pathFileName = path + "/" + fileName;
        int rowsFromFile = countRowsFromFile(pathFileName);
        printAllContactsFromFile();
        System.out.println("Please select which row you want to edit: ");
        while (!scanner.hasNext("[0-9]*")) {
            System.out.println("You have entered an invalid row. ");
            scanner.nextLine();
        }
        int row = Integer.parseInt(scanner.nextLine());
        String record = row + ".";
        if (rowsFromFile < row) {
            System.out.println("You have selected a non existing record");
        } else {
            try {
                Scanner scanner = new Scanner(new File(pathFileName));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains(record)) {
                        System.out.println("Do you want to edit Contact's First Name? [Y/N]");
                        String choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Last Name? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Personal Number? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Work Name? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Country? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's City? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Street Name? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Street Number? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Day Of Birth? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Month Of Birth? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                        System.out.println("Do you want to edit Contact's Year Of Birth? [Y/N]");
                        choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("Y")) {

                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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

