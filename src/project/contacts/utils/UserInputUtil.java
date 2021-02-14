package project.contacts.utils;

import project.contacts.contact.Address;
import project.contacts.contact.Birthday;
import project.contacts.contact.Contact;

import java.util.Scanner;

public class UserInputUtil {
    private static final String FIRST_NAME_PATTERN = "[a-zA-Z]{1,}";
    private static final String LAST_NAME_PATTERN = "[a-zA-Z]{1,}-?[A-Z]?[a-zA-Z]*";
    private static final String PERSONAL_PHONE_NUMBER_PATTERN = "(([+]?359)|0)+[8]{1}[7,8,9]{1}\\d{7}";
    private static final String WORK_PHONE_NUMBER_PATTERN =
            "((([+]?359)|0)+[8]{1}[7,8,9]{1}\\d{7}|((([+]?(3592))|(02))+[1-9]{1}\\d{5,6}))";
    private static final String COUNTRY_NAME_PATTERN =
            "[a-zA-Z]{1,}[ ]?[a-zA-Z]*[ ]?[a-zA-Z]*";
    private static final String CITY_NAME_PATTERN =
            "[a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*";
    private static final String STREET_NAME_PATTERN =
            "([a-zA-Z]{1,}[ ]?[A-Z]?[a-zA-Z]*[ ]?[A-Z]?[a-zA-Z]*|[1-9]{1}[0-9]{0,4})";
    private static final String STREET_NUMBER_PATTERN = "[1-9]{1}[0-9]{0,3}|[1-9]{1}[0-9]{1,3}[A-Z]{1}";

    private UserInputUtil(){}

    public static Contact createContactViaUserInput() {
        String workPhoneNumber = "null";
        String country = "null";
        String city = "null";
        String streetName = "null";
        String streetNumber = "null";
        Birthday birthday = new Birthday(0,0,0);

        String firstName = ValidationUtil.validateStringFromUserInput
                ("First Name", FIRST_NAME_PATTERN);
        String lastName = ValidationUtil.validateStringFromUserInput
                ("Last Name", LAST_NAME_PATTERN);
        String personalPhoneNumber = ValidationUtil.validateStringFromUserInput
                ("Personal Phone Number", PERSONAL_PHONE_NUMBER_PATTERN);

        Logger.printInfoMessage("Do you want to enter Contact's Work Phone Number? [Y/N]: ");
        String workPhoneNumberChoice = ValidationUtil.validateYesNoFromUserInput().toUpperCase();
        if (workPhoneNumberChoice.equals("Y") || workPhoneNumberChoice.equals("YES")) {
            workPhoneNumber = ValidationUtil.validateStringFromUserInput
                    ("Work Phone Number", WORK_PHONE_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's Address? [Y/N]: ");
        String addressChoice = ValidationUtil.validateYesNoFromUserInput().toUpperCase();
        if (addressChoice.equals("Y") || addressChoice.equals("YES")) {
            country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
            city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
            streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
            streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
        }
        Logger.printInfoMessage("Do you want to enter Contact's Birthday? [Y/N]: ");
        String birthdayChoice = ValidationUtil.validateYesNoFromUserInput().toUpperCase();
        if (birthdayChoice.equals("Y") || birthdayChoice.equals("YES")) {
            birthday = createBirthdayViaUserInput();
        }

        Address address = new Address(country, city, streetName, streetNumber);
        return new Contact(ValidationUtil.capitalize(firstName), ValidationUtil.capitalize(lastName),
                personalPhoneNumber, workPhoneNumber, address, birthday);
    }

    public static Birthday createBirthdayViaUserInput() {
        Scanner scanner = new Scanner(System.in);
        boolean validDate = false;
        int day = 0, month = 0, year = 0;
        while (!validDate) {
            try {
                Logger.printInfoMessage("Enter Contact's Day of Birth: ");
                day = Integer.parseInt(scanner.nextLine().trim());
                Logger.printInfoMessage("Enter Contact's Month of Birth: ");
                month = Integer.parseInt(scanner.nextLine().trim());
                Logger.printInfoMessage("Enter Contact's Year of Birth: ");
                year = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                Logger.printErrorMessage("You have entered an invalid birthday! Please try again: \n");
                continue;
            }
            validDate = ValidationUtil.isValidDate(month, day, year);
            if (!validDate) {
                Logger.printErrorMessage("You have entered an invalid birthday! Please try again: \n");
            }
        }
        return new Birthday(day, month, year);
    }

    public static void editPropertyViaUserInput(Contact contact, int choice) {
        if (choice == 1) {
            contact.setFirstName(ValidationUtil.capitalize
                    (ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN)));
        }
        if (choice == 2) {
            contact.setLastName(ValidationUtil.capitalize
                    (ValidationUtil.validateStringFromUserInput("Last Name", LAST_NAME_PATTERN)));
        }
        if (choice == 3) {
            contact.setPersonalPhoneNumber(ValidationUtil.validateStringFromUserInput("Personal Phone Number", PERSONAL_PHONE_NUMBER_PATTERN));
        }
        if (choice == 4) {
            contact.setWorkPhoneNumber(ValidationUtil.validateStringFromUserInput("Work Phone Number", WORK_PHONE_NUMBER_PATTERN));
        }
        if (choice == 5) {
            String country = ValidationUtil.validateStringFromUserInput("Country", COUNTRY_NAME_PATTERN);
            String city = ValidationUtil.validateStringFromUserInput("City", CITY_NAME_PATTERN);
            String streetName = ValidationUtil.validateStringFromUserInput("Street Name", STREET_NAME_PATTERN);
            String streetNumber = ValidationUtil.validateStringFromUserInput("Street Number", STREET_NUMBER_PATTERN);
            Address address = new Address(country, city, streetName, streetNumber);
            contact.setAddress(address);
        }
        if (choice == 6) {
            contact.setBirthday(createBirthdayViaUserInput());
        }
    }
}
