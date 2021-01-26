package project.contacts.utils;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class ValidationUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static int validateIntegerFromUserInput(String str, String pattern) {
        System.out.println("Enter Contact's " + str + ":");
        while (!scanner.hasNext(pattern)) {
            System.out.println("You have entered an invalid " + str);
            scanner.nextLine();
        }
        return Integer.parseInt(scanner.nextLine());
    }

    public static String validateStringFromUserInput(String str, String pattern) {
        System.out.println("Enter Contact's " + str + ":");
        while (!scanner.hasNext(pattern)) {
            System.out.println("You have entered an invalid " + str);
            scanner.nextLine();
        }
        return scanner.nextLine();
    }
}
