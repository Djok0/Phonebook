package project.contacts.utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Scanner scanner = new Scanner(System.in);
    private static final int maxYear = 4000;

    public static final String PASSWORD_VALIDATION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&!+=])(?=\\S+$).{8,}$";

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
            Logger.printErrorMessage("Your password's strength doesn't meet the requirements.");
            Logger.printErrorMessage("Your password must be at least 8 symbols and must contains" +
                    "at least one lower case char, one upper case char, one number and one special character!");
            return false;
        } else {
            return true;
        }
    }
}
