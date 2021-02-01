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
    // private static Birthday birthday;

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

        Logger.printInfoMessage("Do you want to enter Contact's personal phone number? [Y/N]");
        String personalPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (personalPhoneNumberChoice.equals("Y")) {
            personalNumber = ValidationUtil.validateStringFromUserInput("Personal Number", PHONE_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's work phone number? [Y/N]");
        String workPhoneNumberChoice = scanner.nextLine().toUpperCase();
        if (workPhoneNumberChoice.equals("Y")) {
            workNumber = ValidationUtil.validateStringFromUserInput("Work Number", PHONE_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's address? [Y/N]");
        String addressChoice = scanner.nextLine().toUpperCase();
        if (addressChoice.equals("Y")) {
            country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
            city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
            streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
            streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's birthday? [Y/N]");
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
        String path = PATH_TO_THE_FILE_WITH_ALL_CONTACTS;
        String fileName = "phonebook.txt";
        String pathFileName = path + "/" + fileName;

        try {
            File file = new File(path, fileName);
            if (!file.createNewFile()) {
                try {
                    int rowsFromFile = countRowsFromFile(pathFileName);
                    FileWriter fileWriter = new FileWriter(pathFileName, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(rowsFromFile + 1 + ". ");
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

//    public void removeContact(Contact contact) {
//        contacts.remove(contact);
//    }

    public static void printAllContactsFromFile() {
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        // int counter = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFileName));
            ArrayList<String> contacts = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                contacts.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
//          printAllContacts();
            for (String contact : contacts) {
//                counter++;
//                System.out.println(counter + ". " + contact);
                System.out.println(contact);
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
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
        boolean found = false;
        try {
            Scanner scanner = new Scanner(new File(pathFileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(firstName)) {
                    found = true;
                    Logger.printInfoMessage("A contact with name " + firstName + " exists. Here are the details:");
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
        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
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

        String firstName = null;
        String lastName = null;

        String personalNumber = null;
        String workNumber = null;

        String country = null;
        String city = null;
        String streetName = null;
        String streetNumber = null;

        int dayOfBirth = 0;
        int monthOfBirth = 0;
        int yearOfBirth = 0;

        String fileName = "phonebook.txt";
        String pathFileName = PATH_TO_THE_FILE_WITH_ALL_CONTACTS + "/" + fileName;
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
                        firstName = extractPropertyFromFile(line, "firstName='.*?'");
                        lastName = extractPropertyFromFile(line, "lastName='.*?'");
                        personalNumber = extractPropertyFromFile(line, "personalNumber='.*?'");
                        workNumber = extractPropertyFromFile(line, "workNumber='.*?'");
                        country = extractPropertyFromFile(line, "country='.*?'");
                        city = extractPropertyFromFile(line, "city='.*?'");
                        streetName = extractPropertyFromFile(line, "streetName='.*?'");
                        streetNumber = extractPropertyFromFile(line, "streetNumber='.*?'");
                        dayOfBirth = Integer.parseInt(extractPropertyFromFile(line, "dayOfBirth='.*?'"));
                        monthOfBirth = Integer.parseInt(extractPropertyFromFile(line, "monthOfBirth='.*?'"));
                        yearOfBirth = Integer.parseInt(extractPropertyFromFile(line, "yearOfBirth='.*?'"));

                        Address oldAddress = new Address(country, city, streetName, streetNumber);
                        Birthday oldBirthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
                        Birthday newBirthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
                        Contact oldContact = new Contact(firstName, lastName, oldBirthday, oldAddress, personalNumber, workNumber);
                        String oldString = record + " " + oldContact.toString();

                        Scanner sc = new Scanner(System.in);
                        System.out.println("Do you want to edit Contact's First Name? [Y/N]");
                        String choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
                        }
                        System.out.println("Do you want to edit Contact's Last Name? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            lastName = ValidationUtil.validateStringFromUserInput("Last Name", LAST_NAME_PATTERN);
                        }
                        System.out.println("Do you want to edit Contact's Personal Number? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            personalNumber = ValidationUtil.validateStringFromUserInput("Personal Number", PHONE_NUMBER_PATTERN);
                        }
                        System.out.println("Do you want to edit Contact's Work Name? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            workNumber = ValidationUtil.validateStringFromUserInput("Personal Number", PHONE_NUMBER_PATTERN);
                        }
                        System.out.println("Do you want to edit Contact's Country? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
                        }
                        System.out.println("Do you want to edit Contact's City? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
                        }
                        System.out.println("Do you want to edit Contact's Street Name? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
                        }
                        System.out.println("Do you want to edit Contact's Street Number? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
                        }
                        System.out.println("Do you want to edit Contact's Birthday? [Y/N]");
                        choice = sc.nextLine().toUpperCase();
                        if (choice.equals("Y")) {
                            newBirthday = ValidationUtil.validateBirthdayFromUserInput();
                        }
                        Address address = new Address(country, city, streetName, streetNumber);
                        Contact newContact = new Contact(firstName, lastName, newBirthday, address, personalNumber, workNumber);
                        String newString = record + " " + newContact.toString();
                        modifyContactInFile(pathFileName, oldString, newString);
                        System.out.println("Changes are successfully applied!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void modifyContactInFile(String filePath, String oldString, String newString) {
        Path path = Paths.get(filePath);
        Charset charset = StandardCharsets.UTF_8;

        String content = null;
        try {
            content = new String(Files.readAllBytes(path), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (content != null) {
            content = content.replace(oldString, newString);
        }
        try {
            if (content != null) {
                Files.write(path, content.getBytes(charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractPropertyFromFile(String line, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
        String value = "";
        int subString = pattern.length() - 4;   // firstName='.*?' - 4 = firstName='
        while (m.find()) {
            value = (line.substring(m.start() + subString, m.end() - 1));
        }
        return value;
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

