package project.contacts.contact;

import project.contacts.account.Account;
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

    public static final String FIRST_NAME_PATTERN = "[a-zA-Z]{1,}";
    public static final String LAST_NAME_PATTERN = "[a-zA-Z]{1,}-?[A-Z]?[a-zA-Z]*";
    public static final String PERSONAL_PHONE_NUMBER_PATTERN = "(([+]?359)|0)?[8]{1}[7,8,9]{1}\\d{7}";
    public static final String WORK_PHONE_NUMBER_PATTERN = "((([+]?359)|0)?[8]{1}[7,8,9]{1}\\d{7}|((([+]?(3592))|(02))?[1-9]{1}\\d{6}))";
    public static final String COUNTRY_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*";
    public static final String CITY_NAME_PATTERN = "[A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*";
    public static final String STREET_NAME_PATTERN = "([A-Z]{1}[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*|[1-9]{1}[0-9]{0,4})";
    public static final String STREET_NUMBER_PATTERN = "[1-9]{1}[0-9]{0,3}|[1-9]{1}[0-9]{0,3}[A-Z]{1}";
    public static final String PATH_TO_THE_FILE_WITH_ALL_CONTACTS = "src/resources/project/contacts/contact";
    public static final String CHOOSE_RECORD_ROW_NUMBER = "[0-9]{1,6}";

    public static void addContact(Account account) {

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
        String personalPhoneNumber = ValidationUtil.validateStringFromUserInput("Personal Phone Number", PERSONAL_PHONE_NUMBER_PATTERN);

        Logger.printInfoMessage("Do you want to enter Contact's Work Phone Number? [Y/N]: ");
        workPhoneNumberChoice = ValidationUtil.validateYesNoFromUserInput().toUpperCase();
        if (workPhoneNumberChoice.equals("Y") || workPhoneNumberChoice.equals("YES")) {
            workPhoneNumber = ValidationUtil.validateStringFromUserInput("Work Phone Number", WORK_PHONE_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's Address? [Y/N]: ");
        addressChoice = ValidationUtil.validateYesNoFromUserInput().toUpperCase();
        if (addressChoice.equals("Y") || addressChoice.equals("YES")) {
            country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
            city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
            streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
            streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's Birthday? [Y/N]: ");
        birthdayChoice = ValidationUtil.validateYesNoFromUserInput().toUpperCase();
        if (birthdayChoice.equals("Y") || birthdayChoice.equals("YES")) {
            birthday = Birthday.createBirthday();
        }

        Address address = new Address(country, city, streetName, streetNumber);
        Contact contact = new Contact(ValidationUtil.capitalize(firstName), ValidationUtil.capitalize(lastName), personalPhoneNumber, workPhoneNumber, address, birthday);
//        contacts.add(contact);

        addContactToFile(account, contact);
        Logger.printSuccessMessage("Contact added successfully!");
    }

    public static void addContactToFile(Account account, Contact contact) {
        String path = PATH_TO_THE_FILE_WITH_ALL_CONTACTS;
        String fileName = account.getName() + "_phonebook.txt";
        String pathFileName = path + "/" + fileName;
        File file;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        int rowsFromFile;

        try {
            file = new File(path, fileName);
            if (!file.createNewFile()) {
                try {
                    rowsFromFile = countRowsFromFile(account, pathFileName);
                    fileWriter = new FileWriter(pathFileName, true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(rowsFromFile + 1 + ". ");
                    bufferedWriter.write(contact.toString());
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    fileWriter.close();
                } catch (IOException e) {
                    Logger.printErrorMessage("Error reading from / writing to file has occurred \n");
                }
            } else {
                rowsFromFile = countRowsFromFile(account, pathFileName);
                fileWriter = new FileWriter(pathFileName);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(rowsFromFile + 1 + ". ");
                bufferedWriter.write(contact.toString());
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred \n");
        }
    }

    public static void printAllContactsFromFile(Account account) {
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

        String fileName = account.getName() + "_phonebook.txt";
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
            br.close();
            sortContactsInFile(account, contacts);
            printContactsWithoutNulls(contacts);

        } catch (IOException e) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
        }

    }

    private static void sortContactsInFile(Account account, List<Contact> contacts) {
        Collections.sort(contacts);

        String path = PATH_TO_THE_FILE_WITH_ALL_CONTACTS;
        String fileName = account.getName() + "_phonebook.txt";
        String pathFileName = path + "/" + fileName;
        File file;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        int row = 0;

        try {
            file = new File(path, fileName);
            if (!file.createNewFile()) {
                try {
                    fileWriter = new FileWriter(pathFileName);      // Clean the file
                    for (Contact contact : contacts) {
                        fileWriter = new FileWriter(pathFileName, true);    // Write sorted values
                        bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(row + 1 + ". ");
                        bufferedWriter.write(contact.toString());
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                        fileWriter.close();
                        row++;
                    }
                    fileWriter.close();
                } catch (IOException e) {
                    Logger.printErrorMessage("Error reading from / writing to file has occurred \n");
                }
            } else {
                Logger.printInfoMessage("File already exists. \n");
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred \n");
        }
    }

    private static void printContactsWithoutNulls(List<Contact> contacts) {

        Collections.sort(contacts);
        System.out.println("___________________________________________________" +
                "______________________________________________________________" +
                "______________________________________________________________");
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
            System.out.println("___________________________________________________" +
                    "______________________________________________________________" +
                    "______________________________________________________________");
        }
    }

    public static int countRowsFromFile(Account account, String file) {
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
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
        }
        return counter;
    }

    public static void searchRecordByName(Account account) {
        String searchFirstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
        String fileName = account.getName() + "_phonebook.txt";
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
            scanner.close();
            if (!found) {
                Logger.printErrorMessage("There is no record with name " + searchFirstName + " in the phonebook! \n");
            } else {
                Logger.printSuccessMessage("Contact/s with First Name " + searchFirstName + " exists. Here are the details:");
                printContactsWithoutNulls(contacts);
            }
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
        }
    }

    public static void searchRecordByPhoneNumber(Account account) {
        String searchPhoneNumber = ValidationUtil.validateStringFromUserInput("Phone Number", PERSONAL_PHONE_NUMBER_PATTERN);
        String fileName = account.getName() + "_phonebook.txt";
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
            scanner.close();
            if (!found) {
                Logger.printErrorMessage("There is no record with number " + searchPhoneNumber + " in the phonebook! \n");
            } else {
                Logger.printSuccessMessage("Contact/s with phone number " + searchPhoneNumber + " exists. Here are the details:");
                printContactsWithoutNulls(contacts);
            }
        } catch (FileNotFoundException e) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
        }
    }

    public static void editRecord(Account account) {
        Scanner scanner = new Scanner(System.in);
        String firstName;
        String lastName;

        String fileName = account.getName() + "_phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;

        File f = new File(pathFileName);
        if (!f.exists()) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
            return;
        }

        int rowsFromFile = countRowsFromFile(account, pathFileName);
        int row = -1;
        String record;
        boolean found = false;
        boolean correctInput = false;

        printAllContactsFromFile(account);

        Logger.printInfoMessage("Please select which row you want to edit or press 0 to return to the Main Menu: ");
        while (!correctInput) {
            while (!scanner.hasNext(CHOOSE_RECORD_ROW_NUMBER)) {
                Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
                scanner.nextLine();
            }
            row = Integer.parseInt(scanner.nextLine().trim());
            if (row == 0) {
                return;
            }
            if (row <= rowsFromFile) {
                correctInput = true;
            } else {
                Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
            }
        }

        record = row + ".";
        try {
            scanner = new Scanner(new File(pathFileName));
            while (!found && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(record)) {
                    firstName = extractPropertyValueFromFile(line, "firstName");
                    lastName = extractPropertyValueFromFile(line, "lastName");

                    Logger.printInfoMessage("You are now editing contact: " + firstName + " " + lastName + "\n");
                    ProgramManagementUtil.openEditMenu(account, row);
                    found = true;
                }
            }
            scanner.close();
        } catch (IOException e) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
        }
    }

    public static void deleteRecord(Account account) {
        Scanner scanner = new Scanner(System.in);
        String firstName;
        String lastName;
        String deleteContactChoice;

        String fileName = account.getName() + "_phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;

        File f = new File(pathFileName);
        if (!f.exists()) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
            return;
        }

        int rowsFromFile = countRowsFromFile(account, pathFileName);
        int row = -1;
        String record;
        boolean found = false;
        boolean correctInput = false;

        printAllContactsFromFile(account);

        Logger.printInfoMessage("Please select which row you want to delete or press 0 to return to the Main Menu: ");
        while (!correctInput) {
            while (!scanner.hasNext(CHOOSE_RECORD_ROW_NUMBER)) {
                Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
                scanner.nextLine();
            }
            row = Integer.parseInt(scanner.nextLine().trim());
            if (row == 0) {
                return;
            }
            if (row <= rowsFromFile) {
                correctInput = true;
            } else {
                Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
            }
        }

        record = row + ".";
        try {
            scanner = new Scanner(new File(pathFileName));
            while (!found && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(record)) {
                    firstName = extractPropertyValueFromFile(line, "firstName");
                    lastName = extractPropertyValueFromFile(line, "lastName");

                    Logger.printInfoMessage("Do you really want to delete contact " + firstName + " " + lastName + "? [Y/N]: ");
                    deleteContactChoice = ValidationUtil.validateYesNoFromUserInput().toUpperCase();
                    if (deleteContactChoice.equals("Y") || deleteContactChoice.equals("YES")) {
                        deleteLineFromFile(pathFileName, record);
                        Logger.printSuccessMessage("You have successfully deleted contact: " + firstName + " " + lastName);
                    } else {
                        Logger.printInfoMessage("Contact " + firstName + " " + lastName + " was not deleted! \n");
                    }
                    found = true;
                }
            }
            scanner.close();
        } catch (IOException e) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
        }
    }

    public static void deleteLineFromFile(String file, String lineToBeDeleted) {
        try {
            File inputFile = new File(file);
            if (!inputFile.isFile()) {
                System.out.println("File does not exist");
                return;
            }
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;

            // Read from the original file and write to the temp file
            // unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.trim().contains(lineToBeDeleted)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            // Write to the original file, data from the temp file
            BufferedReader br1 = new BufferedReader(new FileReader(tempFile));
            PrintWriter pw1 = new PrintWriter(new FileWriter(file));
            String line1 = null;
            while ((line1 = br1.readLine()) != null) {
                pw1.println(line1);
            }
            pw1.close();
            br1.close();

            // Delete the temp file
            if (!tempFile.delete()) {
                Logger.printErrorMessage("Could not delete file! \n");
                return;
            }
        } catch (IOException ex) {
            Logger.printErrorMessage("Error reading from / writing to file! \n");
        }
    }

    public static void modifyContactInFile(Account account, String filePath, String oldString, String newString) {
        Path path = Paths.get(filePath);
        Charset charset = StandardCharsets.UTF_8;
        String content;

        try {
            content = new String(Files.readAllBytes(path), charset);
            content = content.replace(oldString, newString);
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
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

    public static void editPropertyInContact(Account account, int choice, int row) {
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

        String fileName = account.getName() + "_phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        String record;
        String oldString;
        String newString;

        record = row + ".";
        boolean found = false;

        try {
            Scanner scanner = new Scanner(new File(pathFileName));
            while (!found && scanner.hasNextLine()) {
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
                        personalPhoneNumber = ValidationUtil.validateStringFromUserInput("Personal Phone Number", PERSONAL_PHONE_NUMBER_PATTERN);
                    }
                    if (choice == 4) {
                        workPhoneNumber = ValidationUtil.validateStringFromUserInput("Work Phone Number", WORK_PHONE_NUMBER_PATTERN);
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
                    newContact = new Contact(ValidationUtil.capitalize(firstName), ValidationUtil.capitalize(lastName), personalPhoneNumber, workPhoneNumber, newAddress, newBirthday);
                    newString = record + " " + newContact.toString();

                    modifyContactInFile(account, pathFileName, oldString, newString);
                    Logger.printSuccessMessage("Changes are successfully applied!");
                    found = true;
                }
            }
            scanner.close();
        } catch (IOException e) {
            Logger.printErrorMessage("No records found in Phonebook for account " + account.getName() + "\n");
        }
    }
}