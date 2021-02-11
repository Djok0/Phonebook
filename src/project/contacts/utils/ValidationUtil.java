package project.contacts.utils;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Scanner scanner = new Scanner(System.in);
    private static final int maxYear = 4000;

    public static final String PASSWORD_VALIDATION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&!+=])(?=\\S+$).{8,}$";
    private static final String USERNAME_VALIDATION = "^[a-zA-Z0-9]{3,20}$";

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
        Logger.printInfoMessage("Enter valid " + str + ": ");
        while (!scanner.hasNext(pattern)) {
            Logger.printErrorMessage("You have entered an invalid " + str + "! Please try again: ");
            scanner.nextLine();
        }
        return scanner.nextLine().trim();
    }

    public static boolean validatePassword(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_VALIDATION);
        if (!password.matches(String.valueOf(passwordPattern))) {
            Logger.printErrorMessage("Your password's strength doesn't meet the requirements. \n");
            Logger.printErrorMessage("Your password must be at least 8 symbols and must contains " +
                    "at least one lower case char, one upper case char, one number and one special character! \n");
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateUsername(String username) {
        Pattern userNamePattern = Pattern.compile(USERNAME_VALIDATION);
        if (!username.matches(String.valueOf(userNamePattern))) {
            Logger.printErrorMessage("Your username doesn't meet the requirements. \n");
            Logger.printErrorMessage("Your username must be between 3 and 20 symbols and can only contain " +
                    "allowed symbols - lower chars, upper chars and numbers! \n");
            return false;
        } else {
            return true;
        }
    }

    public static String capitalize(String str)
    {
        if(str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

//    public static void clearScreen() {
//        try{
//            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
//        } catch (Exception e){
//            System.out.println(e);
//        }
//    }

}
