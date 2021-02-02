package project.contacts.utils;

import project.contacts.contact.Birthday;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Scanner scanner = new Scanner(System.in);
    private static final int maxYear = 4000;
    static int day, month, year;

    public static final String PASSWORD_VALIDATION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&!+=])(?=\\S+$).{8,}$";

    public static Birthday validateBirthdayFromUserInput() {
        boolean validDate = false;
        boolean leapYear = false;
        while (!validDate && !leapYear) {
            try {
                System.out.println("Enter Contact's Day of Birth:");
                day = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter Contact's Month of Birth:");
                month = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter Contact's Year of Birth:");
                year = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("You have entered an invalid birthday! Please try again: ");
                ValidationUtil.validateBirthdayFromUserInput();
            }

            leapYear = isLeapYear(year);
            validDate = isValidDate(month, day, year);
            if (!leapYear && !validDate) {
                System.out.println("You have entered an invalid birthday! Please try again: ");
            }
        }
        return new Birthday(day, month, year);
    }

    public static boolean isLeapYear(int year) {
        return (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0));
    }

    public static boolean isValidDate(int month, int day, int year) {
        return (month >= 1 && month <= 12) && (day >= 1 &&
                day <= numDaysInMonth(month, isLeapYear(year))) && (year >= 0 && year <= maxYear);
    }

    public static int numDaysInMonth(int month, boolean isLeapYear) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 0;
        }
    }

    public static String validateStringFromUserInput(String str, String pattern) {
        System.out.println("Enter valid " + str + ":");
        while (!scanner.hasNext(pattern)) {
            System.out.println("You have entered an invalid " + str + "! Please try again: ");
            scanner.nextLine();
        }
        return scanner.nextLine().trim();
    }

    public static boolean validatePassword(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_VALIDATION);
        if (!password.matches(String.valueOf(passwordPattern))) {
            System.out.println("Your password's strength doesn't meet the requirements.");
            System.out.println("Your password must be at least 8 symbols and must contains");
            System.out.println("at least one lowerCase, one upperCase, one number and one special character!");
            return false;
        } else {
            return true;
        }
    }
}
