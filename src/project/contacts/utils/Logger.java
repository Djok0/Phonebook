package project.contacts.utils;

public class Logger {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    // Disabling the option to create an object from Logger
    private Logger() {};

    public static void printErrorMessage(String message) {
        System.err.println(message);
    }

    public static void printSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public static void printInfoMessage(String message) {
        System.out.println(message);
    }
}
