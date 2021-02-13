package project.contacts.utils;

import project.contacts.account.Account;
import project.contacts.contact.Address;
import project.contacts.contact.Birthday;
import project.contacts.contact.Contact;
import project.contacts.contact.Phonebook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManagementUtil {

    public static boolean verifyLoginCredentials(Account account, String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(account.getPassword().getBytes());
        boolean credentials = false;
        String str;
        String tempUserName = "";
        String tempPassword = "";
        String[] values;

        try {
            FileInputStream fstream = new FileInputStream(account.getCredentialsPath());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fstream));
            while ((str = bufferedReader.readLine()) != null) {
                values = str.split(",");
                tempUserName = values[0];
                tempPassword = values[1];
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred \n");
        }
        if (tempUserName.trim().equals(account.getUsername().trim()) &&
                tempPassword.trim().equals(encodedPassword.trim())) {
            credentials = true;
        }
        return credentials;
    }

    public static boolean accountExist(Account account) {
        File user = new File(account.getCredentialsPath());
        return user.exists() && !user.isDirectory();
    }

    public static Phonebook readPhonebookFromFile(Account account) {
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

        List<Contact> contacts = new ArrayList<>();
        String line;

        try {
            Scanner scanner = new Scanner(new File(account.getPhonebookPath()));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                firstName = extractPropertyFromFile(line, "firstName");
                lastName = extractPropertyFromFile(line, "lastName");
                personalPhoneNumber = extractPropertyFromFile(line, "personalPhoneNumber");
                workPhoneNumber = extractPropertyFromFile(line, "workPhoneNumber");
                country = extractPropertyFromFile(line, "country");
                city = extractPropertyFromFile(line, "city");
                streetName = extractPropertyFromFile(line, "streetName");
                streetNumber = extractPropertyFromFile(line, "streetNumber");
                dayOfBirth = Integer.parseInt(extractPropertyFromFile(line, "dayOfBirth"));
                monthOfBirth = Integer.parseInt(extractPropertyFromFile(line, "monthOfBirth"));
                yearOfBirth = Integer.parseInt(extractPropertyFromFile(line, "yearOfBirth"));

                Birthday birthday = new Birthday(dayOfBirth, monthOfBirth, yearOfBirth);
                Address address = new Address(country, city, streetName, streetNumber);
                Contact contact = new Contact(firstName, lastName, personalPhoneNumber,
                        workPhoneNumber, address, birthday);

                contacts.add(contact);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Phonebook(contacts);
    }

    public static void savePhonebookInFile(Account account) {
        Phonebook phonebook = account.getPhonebook();
        phonebook.sortContacts();
        List<Contact> contacts = phonebook.getContacts();
        int row = 1;

        try {
            FileWriter fileWriter = new FileWriter(account.getPhonebookPath());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Contact contact : contacts) {
                bufferedWriter.write(row + ". ");
                bufferedWriter.write(contact.toString());
                bufferedWriter.newLine();
                row++;
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred \n");
        }
    }

    private static String extractPropertyFromFile(String line, String property) {
        String propertyPattern = property + "='.*?'";
        Pattern pattern = Pattern.compile(propertyPattern);
        Matcher m = pattern.matcher(line);
        String value = "";
        int startOfProperty = propertyPattern.length() - 4;   // firstName='.*?' - 4 = firstName='

        while (m.find()) {
            value = (line.substring(m.start() + startOfProperty, m.end() - 1)); // .*?' - ' = .*?
        }
        if (value.equals("")) {
            value = "null";
        }
        return value;
    }

    public static void addAccountToFile(Account account) {
        String encodedPassword = Base64.getEncoder().encodeToString(account.getPassword().getBytes());
        try {
            FileWriter fileWriter = new FileWriter(account.getCredentialsPath());
            fileWriter.write(account.getUsername());
            fileWriter.write(",");
            fileWriter.write(encodedPassword);
            fileWriter.close();
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred \n");
        }
    }

    public static boolean createPhonebookFileForAccount(Account account) {
        try {
            File file = new File(account.getPhonebookPath());
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.close();
                return true;
            }
        } catch (IOException e) {
            Logger.printErrorMessage("Error reading from / writing to file has occurred \n");
        }
        return false;
    }
}
