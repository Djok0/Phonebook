package project.contacts;

import project.contacts.account.Account;
import project.contacts.utils.AccountManagementUtil;
import project.contacts.utils.Logger;
import project.contacts.utils.ProgramManagementUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        // Creating the initial account (admin/admin)
        Account account = new Account("admin", "admin");

        AccountManagementUtil.logIn(account);
        ProgramManagementUtil.startProgram(account);
    }
}

/*  1. We cannot validate the date in this format - The birthday should be a string and to validate leap year or not etc.
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;

    2. Do we use the setters - maybe is better idea to make validations in setters
    3. Enter == No in our menu
    4. Password and account name stored in file
    5. Maybe we can discuss the changes with Radi tomorrow

*/

