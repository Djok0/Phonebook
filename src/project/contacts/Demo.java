package project.contacts;

import project.contacts.utils.AccountManagementUtil;
import project.contacts.utils.ProgramManagementUtil;

public class Demo {
    public static void main(String[] args) {
        ProgramManagementUtil.cleanConsoleWithoutMessage();
        AccountManagementUtil.logIn();
    }
}
