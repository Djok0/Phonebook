package project.contacts.contact;

import project.contacts.utils.Logger;
import project.contacts.utils.ProgramManagementUtil;
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
    private static final Scanner scanner = new Scanner(System.in);

    public static void addContact() {

        String workPhoneNumber = null;

        String country = null;
        String city = null;
        String streetName = null;
        String streetNumber = null;

        int dayOfBirth = 0;
        int monthOfBirth = 0;
        int yearOfBirth = 0;

        Birthday birthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);

        String workPhoneNumberChoice;
        String addressChoice;
        String birthdayChoice;

        String firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
        String lastName = ValidationUtil.validateStringFromUserInput("Last Name", LAST_NAME_PATTERN);
        String personalPhoneNumber = ValidationUtil.validateStringFromUserInput("Personal Phone Number", PHONE_NUMBER_PATTERN);

        Logger.printInfoMessage("Do you want to enter Contact's Work Phone Number? [Y/N]");
        workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            workPhoneNumber = ValidationUtil.validateStringFromUserInput("Work Phone Number", PHONE_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's Address? [Y/N]");
        addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
            city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
            streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
            streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's Birthday? [Y/N]");
        birthdayChoice = scanner.nextLine().toUpperCase();
        if (birthdayChoice.equals("Y")) {
            birthday = Birthday.createBirthday();
        }

        Address address = new Address(country, city, streetName, streetNumber);
        Contact contact = new Contact(firstName, lastName, personalPhoneNumber, workPhoneNumber, address, birthday);
//        contacts.add(contact);

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
        String firstName;
        String lastName;

        String personalPhoneNumber;
        String workPhoneNumber;

        String country;
        String city;
        String streetName;
        String streetNumber;

        int dayOfBirth;
        int monthOfBirth;
        int yearOfBirth;

        Address address;
        Birthday birthday;
        Contact contact;

        List<Contact> contacts = new ArrayList<>();

        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(pathFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                firstName = extractPropertyValueFromFile(line, "firstName");
                lastName = extractPropertyValueFromFile(line, "lastName");
                personalPhoneNumber = extractPropertyValueFromFile(line, "personalPhoneNumber");
                workPhoneNumber = extractPropertyValueFromFile(line, "workPhoneNumber");
                country = extractPropertyValueFromFile(line, "country");
                city = extractPropertyValueFromFile(line, "city");
                streetName = extractPropertyValueFromFile(line, "streetName");
                streetNumber = extractPropertyValueFromFile(line, "streetNumber");
                dayOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "dayOfBirth"));
                monthOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "monthOfBirth"));
                yearOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "yearOfBirth"));

                birthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
                address = new Address(country, city, streetName, streetNumber);
                contact = new Contact(firstName, lastName, personalPhoneNumber, workPhoneNumber, address, birthday);
                contacts.add(contact);
            }
            printContactsWithoutNulls(contacts);
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred");
        }

    }

    private static void printContactsWithoutNulls(List<Contact> contacts) {
        for (Contact c : contacts) {
            System.out.println(1 + contacts.indexOf(c) + "." +
                    " Name: " + c.getFirstName() + " " + c.getLastName() +
                    "; Personal Phone: " + c.getPersonalPhoneNumber() +
                    "; Work Phone: " + ((!c.getWorkPhoneNumber().equals("null"))
                    ? c.getWorkPhoneNumber() : "No Data") +
                    "; Address: " + ((!c.getAddress().getCountry().equals("null"))
                    ? c.getAddress().getStreetNumber() + " " +
                    c.getAddress().getStreetName() + " Street, " +
                    c.getAddress().getCity() + ", " +
                    c.getAddress().getCountry() : "No Data") +
                    "; Birthday: " + ((c.getBirthday().getDayOfBirth() != 0)
                    ? c.getBirthday().getDayOfBirth() + "." +
                    c.getBirthday().getMonthOfBirth() + "." +
                    c.getBirthday().getYearOfBirth() : "No Data"));
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
        String searchFirstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        boolean found = false;
        Scanner scanner;
        String line;

        String firstName;
        String lastName;

        String personalPhoneNumber;
        String workPhoneNumber;

        String country;
        String city;
        String streetName;
        String streetNumber;

        int dayOfBirth;
        int monthOfBirth;
        int yearOfBirth;

        Address address;
        Birthday birthday;
        Contact contact;

        List<Contact> contacts = new ArrayList<>();

        try {
            scanner = new Scanner(new File(pathFileName));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(searchFirstName)) {
                    firstName = extractPropertyValueFromFile(line, "firstName");
                    lastName = extractPropertyValueFromFile(line, "lastName");
                    personalPhoneNumber = extractPropertyValueFromFile(line, "personalPhoneNumber");
                    workPhoneNumber = extractPropertyValueFromFile(line, "workPhoneNumber");
                    country = extractPropertyValueFromFile(line, "country");
                    city = extractPropertyValueFromFile(line, "city");
                    streetName = extractPropertyValueFromFile(line, "streetName");
                    streetNumber = extractPropertyValueFromFile(line, "streetNumber");
                    dayOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "dayOfBirth"));
                    monthOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "monthOfBirth"));
                    yearOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "yearOfBirth"));

                    birthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
                    address = new Address(country, city, streetName, streetNumber);
                    contact = new Contact(firstName, lastName, personalPhoneNumber, workPhoneNumber, address, birthday);

                    if (searchFirstName.equals(firstName)) {
                        found = true;
                        contacts.add(contact);
                    }
                }
            }
            if (!found) {
                Logger.printErrorMessage("There is no record with name " + searchFirstName + " in the phonebook!");
            } else {
                Logger.printSuccessMessage("Contact/s with First Name " + searchFirstName + " exists. Here are the details:");
                printContactsWithoutNulls(contacts);
            }
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("File was not found!");
        }
    }

    public static void searchRecordByPhoneNumber() {
        String searchPhoneNumber = ValidationUtil.validateStringFromUserInput("Phone Number", PHONE_NUMBER_PATTERN);
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        boolean found = false;
        Scanner scanner;
        String line;

        String firstName;
        String lastName;

        String personalPhoneNumber;
        String workPhoneNumber;

        String country;
        String city;
        String streetName;
        String streetNumber;

        int dayOfBirth;
        int monthOfBirth;
        int yearOfBirth;

        Address address;
        Birthday birthday;
        Contact contact;

        List<Contact> contacts = new ArrayList<>();

        try {
            scanner = new Scanner(new File(pathFileName));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(searchPhoneNumber)) {
                    firstName = extractPropertyValueFromFile(line, "firstName");
                    lastName = extractPropertyValueFromFile(line, "lastName");
                    personalPhoneNumber = extractPropertyValueFromFile(line, "personalPhoneNumber");
                    workPhoneNumber = extractPropertyValueFromFile(line, "workPhoneNumber");
                    country = extractPropertyValueFromFile(line, "country");
                    city = extractPropertyValueFromFile(line, "city");
                    streetName = extractPropertyValueFromFile(line, "streetName");
                    streetNumber = extractPropertyValueFromFile(line, "streetNumber");
                    dayOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "dayOfBirth"));
                    monthOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "monthOfBirth"));
                    yearOfBirth = Integer.parseInt(extractPropertyValueFromFile(line, "yearOfBirth"));

                    birthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
                    address = new Address(country, city, streetName, streetNumber);
                    contact = new Contact(firstName, lastName, personalPhoneNumber, workPhoneNumber, address, birthday);

                    if (searchPhoneNumber.equals(personalPhoneNumber) || searchPhoneNumber.equals(workPhoneNumber)) {
                        found = true;
                        contacts.add(contact);
                    }
                }
            }
            if (!found) {
                Logger.printErrorMessage("There is no record with number " + searchPhoneNumber + " in the phonebook!");
            } else {
                Logger.printSuccessMessage("Contact/s with phone number " + searchPhoneNumber + " exists. Here are the details:");
                printContactsWithoutNulls(contacts);
            }
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("File was not found!");
        }
    }

    public static void editRecordOld() {

        String firstName;
        String lastName;

        String personalPhoneNumber;
        String workPhoneNumber;

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
            Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
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
                        personalPhoneNumber = extractPropertyValueFromFile(line, "personalPhoneNumber");
                        workPhoneNumber = extractPropertyValueFromFile(line, "workPhoneNumber");
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
                        oldContact = new Contact(firstName, lastName, personalPhoneNumber, workPhoneNumber, oldAddress, oldBirthday);
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
                        Logger.printInfoMessage("Do you want to edit Contact's Personal Phone Number? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            personalPhoneNumber = ValidationUtil.validateStringFromUserInput("Personal Phone Number", PHONE_NUMBER_PATTERN);
                        }
                        Logger.printInfoMessage("Do you want to edit Contact's Work Phone Number? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            workPhoneNumber = ValidationUtil.validateStringFromUserInput("Work Phone Number", PHONE_NUMBER_PATTERN);
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
                            newBirthday = Birthday.createBirthday();
                        }

                        newAddress = new Address(country, city, streetName, streetNumber);
                        newContact = new Contact(firstName, lastName, personalPhoneNumber, workPhoneNumber, newAddress, newBirthday);
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

    public static void editRecord() {

        String firstName;
        String lastName;

        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        int rowsFromFile = countRowsFromFile(pathFileName);
        int row;
        String record;

        printAllContactsFromFile();

        Logger.printInfoMessage("Please select which row you want to edit: ");
        while (!scanner.hasNext("[0-9]*")) {
            Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
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

                        ProgramManagementUtil.openEditMenu(firstName, lastName, row);
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

    public static void editPropertyInContact(int choice, int row) {
        String firstName;
        String lastName;

        String personalPhoneNumber;
        String workPhoneNumber;

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

        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        String record;
        String oldString;
        String newString;

        record = row + ".";

        try {
            Scanner scanner = new Scanner(new File(pathFileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(record)) {
                    firstName = extractPropertyValueFromFile(line, "firstName");
                    lastName = extractPropertyValueFromFile(line, "lastName");
                    personalPhoneNumber = extractPropertyValueFromFile(line, "personalPhoneNumber");
                    workPhoneNumber = extractPropertyValueFromFile(line, "workPhoneNumber");
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
                    oldContact = new Contact(firstName, lastName, personalPhoneNumber, workPhoneNumber, oldAddress, oldBirthday);
                    oldString = record + " " + oldContact.toString();

                    if (choice == 1) {
                        firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
                    }
                    if (choice == 2) {
                        lastName = ValidationUtil.validateStringFromUserInput("Last Name", LAST_NAME_PATTERN);
                    }
                    if (choice == 3) {
                        personalPhoneNumber = ValidationUtil.validateStringFromUserInput("Personal Phone Number", PHONE_NUMBER_PATTERN);
                    }
                    if (choice == 4) {
                        workPhoneNumber = ValidationUtil.validateStringFromUserInput("Work Phone Number", PHONE_NUMBER_PATTERN);
                    }
                    if (choice == 5) {
                        country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
                        city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
                        streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
                        streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
                    }
                    if (choice == 6) {
                        newBirthday = Birthday.createBirthday();
                    }

                    newAddress = new Address(country, city, streetName, streetNumber);
                    newContact = new Contact(firstName, lastName, personalPhoneNumber, workPhoneNumber, newAddress, newBirthday);
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

