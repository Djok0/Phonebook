package project.contacts.contact;

import project.contacts.utils.Logger;
import project.contacts.utils.ValidationUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phonebook {

    public static final String FIRST_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}";
    public static final String LAST_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}-?[A-Z]?[a-zA-Z]*";
    public static final String PHONE_NUMBER_PATTERN = "[0][8]{1}[7,8,9]{1}\\d{7}";
    public static final String COUNTRY_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*";
    public static final String CITY_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*";
    public static final String STREET_NAME_PATTERN = "([A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*|[1-9]{1}[0-9]{0,4})";
    public static final String STREET_NUMBER_PATTERN = "[1-9]{1}[0-9]{0,3}|[1-9]{1}[0-9]{0,3}[A-Z]{1}";
    public static final String PATH_TO_THE_FILE_WITH_ALL_CONTACTS = "src/resources/project/contacts/contact";

    private static List<Contact> contacts = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

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

        String personalPhoneNumberChoice;
        String workPhoneNumberChoice;
        String addressChoice;
        String birthdayChoice;

        String firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
        String lastName = ValidationUtil.validateStringFromUserInput("Last Name", LAST_NAME_PATTERN);

        Logger.printInfoMessage("Do you want to enter Contact's personal phone number? [Y/N]");
        personalPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (personalPhoneNumberChoice.equals("Y")) {
            personalNumber = ValidationUtil.validateStringFromUserInput("Personal Number", PHONE_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's work phone number? [Y/N]");
        workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            workNumber = ValidationUtil.validateStringFromUserInput("Work Number", PHONE_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's address? [Y/N]");
        addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
            city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
            streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
            streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's birthday? [Y/N]");
        birthdayChoice = scanner.nextLine().toUpperCase();
        if (birthdayChoice.equals("Y")) {
            birthday = ValidationUtil.validateBirthdayFromUserInput();
        }

        Address address = new Address(country, city, streetName, streetNumber);
        Contact contact = new Contact(firstName, lastName, birthday, address, personalNumber, workNumber);
        contacts.add(contact);

        addContactToFile(contact);
        Logger.printSuccessMessage("Contact added successfully!");
    }

    public static void addContactToFile(Contact contact) {
        String path = PATH_TO_THE_FILE_WITH_ALL_CONTACTS;
        String fileName = "phonebook.txt";
        String pathFileName = path + "/" + fileName;
        File file;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        int rowsFromFile;

        try {
            file = new File(path, fileName);
            if (!file.createNewFile()) {
                try {
                    rowsFromFile = countRowsFromFile(pathFileName);
                    fileWriter = new FileWriter(pathFileName, true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(rowsFromFile + 1 + ". ");
                    bufferedWriter.write(contact.toString());
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                } catch (IOException e) {
                    Logger.printErrorMessage("Error reading from / writing to file has occurred");
                }
            } else {
                Logger.printInfoMessage("File already exists.");
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred");
        }
    }

    public static void printAllContactsFromFile() {
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        BufferedReader bufferedReader;
        ArrayList<String> contacts;
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(pathFileName));
            contacts = new ArrayList<>();
            line = bufferedReader.readLine();
            while (line != null) {
                contacts.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            for (String contact : contacts) {
                System.out.println(contact);
            }

        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred");
        }
    }

    public static int countRowsFromFile(String file) {
        int counter = 0;
        BufferedReader bufferedReader;
        ArrayList<String> contacts;
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            contacts = new ArrayList<>();
            line = bufferedReader.readLine();

            while (line != null) {
                contacts.add(line);
                line = bufferedReader.readLine();
                counter++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred");
        }
        return counter;
    }

    public static void searchRecordByName() {
        String firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        boolean found = false;
        Scanner scanner;
        String line;

        try {
            scanner = new Scanner(new File(pathFileName));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(firstName)) {
                    found = true;
                    Logger.printInfoMessage("A contact with name " + firstName + " exists. Here are the details:");
                    System.out.println(line);
                }
            }
            if (!found) {
                Logger.printInfoMessage("There is no record with name " + firstName + " in the phonebook!");
            }
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("File was not found!");
        }
    }

    public static void searchRecordByPhoneNumber() {
        String phoneNumber = ValidationUtil.validateStringFromUserInput("Phone Number", PHONE_NUMBER_PATTERN);
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        boolean found = false;
        Scanner scanner;
        String line;

        try {
            scanner = new Scanner(new File(pathFileName));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(phoneNumber)) {
                    found = true;
                    System.out.println(line);
                }
            }
            if (!found) {
                Logger.printInfoMessage("There is no record with number " + phoneNumber + " in the phonebook!");
            }
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("File was not found!");
        }
    }


    public static void editRecord() {

        String firstName;
        String lastName;

        String personalNumber;
        String workNumber;

        String country;
        String city;
        String streetName;
        String streetNumber;

        int dayOfBirth;
        int monthOfBirth;
        int yearOfBirth;

        Address oldAddress;
        Address newAddress;
        Birthday oldBirthday;
        Birthday newBirthday;
        Contact oldContact;
        Contact newContact;

        Scanner sc;
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        int rowsFromFile = countRowsFromFile(pathFileName);
        int row;
        String record;
        String choice;
        String oldString;
        String newString;

        printAllContactsFromFile();

        Logger.printInfoMessage("Please select which row you want to edit: ");
        while (!scanner.hasNext("[0-9]*")) {
            Logger.printErrorMessage("You have entered an invalid row. ");
            scanner.nextLine();
        }

        row = Integer.parseInt(scanner.nextLine());
        record = row + ".";

        if (rowsFromFile < row) {
            Logger.printInfoMessage("You have selected a non existing record");
        } else {
            try {
                Scanner scanner = new Scanner(new File(pathFileName));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains(record)) {
                        firstName = extractPropertyValueFromFile(line, "firstName");
                        lastName = extractPropertyValueFromFile(line, "lastName");
                        personalNumber = extractPropertyValueFromFile(line, "personalNumber");
                        workNumber = extractPropertyValueFromFile(line, "workNumber");
                        country = extractPropertyValueFromFile(line, "country");
                        city = extractPropertyValueFromFile(line, "city");
                        streetName = extractPropertyValueFromFile(line, "streetName");
                        streetNumber = extractPropertyValueFromFile(line, "streetNumber");
                        dayOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "dayOfBirth"));
                        monthOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "monthOfBirth"));
                        yearOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "yearOfBirth"));

                        oldAddress = new Address(country, city, streetName, streetNumber);
                        oldBirthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
                        newBirthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
                        oldContact = new Contact(firstName, lastName, oldBirthday, oldAddress, personalNumber, workNumber);
                        oldString = record + " " + oldContact.toString();

                        sc = new Scanner(System.in);
                        Logger.printInfoMessage("Do you want to edit Contact's First Name? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's Last Name? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            lastName = ValidationUtil.validateStringFromUserInput("Last Name", LAST_NAME_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's Personal Number? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            personalNumber = ValidationUtil.validateStringFromUserInput("Personal Number", PHONE_NUMBER_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's Work Number? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            workNumber = ValidationUtil.validateStringFromUserInput("Work Number", PHONE_NUMBER_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's Country? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's City? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's Street Name? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's Street Number? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's Birthday? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            newBirthday = ValidationUtil.validateBirthdayFromUserInput();
                        }

                        newAddress = new Address(country, city, streetName, streetNumber);
                        newContact = new Contact(firstName, lastName, newBirthday, newAddress, personalNumber, workNumber);
                        newString = record + " " + newContact.toString();

                        modifyContactInFile(pathFileName, oldString, newString);
                        Logger.printSuccessMessage("Changes are successfully applied!");
                    }
                }
            } catch (IOException e) {
                Logger.printErrorMessage("Error reading from / writing to file has occurred");
            }
        }
    }

    static void modifyContactInFile(String filePath, String oldString, String newString) {
        Path path = Paths.get(filePath);
        Charset charset = StandardCharsets.UTF_8;
        String content;

        try {
            content = new String(Files.readAllBytes(path), charset);
            content = content.replace(oldString, newString);
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred");
        }
    }

    private static String extractPropertyValueFromFile(String line, String property) {
        String propertyPattern = property + "='.*?'";
        Pattern pattern = Pattern.compile(propertyPattern);
        Matcher m = pattern.matcher(line);
        String value = "";
        int startOfProperty = propertyPattern.length() - 4;   // firstName='.*?' - 4 = firstName='

        while (m.find()) {
            value = (line.substring(m.start() + startOfProperty, m.end() - 1)); // .*?' - ' = .*?
        }

        return value;
    }
}

