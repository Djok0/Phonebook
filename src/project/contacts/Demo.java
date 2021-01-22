package project.contacts;

import project.contacts.account.Account;
import project.contacts.utils.AccountManagementUtil;
import project.contacts.utils.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        // Creating the initial account (admin/admin)
        Account account = new Account("admin", "admin");

        AccountManagementUtil.logIn(account);
    }
}
