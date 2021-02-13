package project.contacts.contact;

import project.contacts.account.Account;
import project.contacts.utils.Logger;
import project.contacts.utils.ProgramManagementUtil;
import project.contacts.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Phonebook {
    private static final String FIRST_NAME_PATTERN = "[a-zA-Z]{1,}";
    private static final String WORK_PHONE_NUMBER_PATTERN =
            "((([+]?359)|0)?[8]{1}[7,8,9]{1}\\d{7}|((([+]?(3592))|(02))?[1-9]{1}\\d{6}))";

    private List<Contact> contacts;

    public Phonebook(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        Logger.printSuccessMessage("Contact " + contact.getFirstName() +
                " " + contact.getLastName() + " is added successfully!");
    }

    public void printContacts() {
        printContacts(this.contacts);
    }

    private void printContacts(List<Contact> contacts) {
        sortContacts();
        if (contacts.isEmpty()) {
            Logger.printErrorMessage("No records found! \n");
            return;
        }
        System.out.println("___________________________________________________" +
                "______________________________________________________________" +
                "______________________________________________________________");
        for (Contact c : contacts) {
            System.out.println(1 + contacts.indexOf(c) + "." +
                    " Name: " + c.getFirstName() + " " + c.getLastName() +
                    "; Personal Phone: " + c.getPersonalPhoneNumber() +
                    "; Work Phone: " + ((!c.getWorkPhoneNumber().equals("null"))
                    ? c.getWorkPhoneNumber() : "No Data") +
                    "; Address: " + ((!c.getAddress().getCountry().equals("null"))
                    ? c.getAddress().getStreetNumber() + " " +
                    c.getAddress().getStreetName() + " Street, " +
                    c.getAddress().getCity() + ", " +
                    c.getAddress().getCountry() : "No Data") +
                    "; Birthday: " + ((c.getBirthday().getDayOfBirth() != 0)
                    ? c.getBirthday().getDayOfBirth() + "." +
                    c.getBirthday().getMonthOfBirth() + "." +
                    c.getBirthday().getYearOfBirth() : "No Data"));
            System.out.println("___________________________________________________" +
                    "______________________________________________________________" +
                    "______________________________________________________________");
        }
    }

    public void searchContactByFirstName() {
        String firstName = ValidationUtil.validateStringFromUserInput("First Name", FIRST_NAME_PATTERN);
        List<Contact> contactsFound = new ArrayList<>();
        boolean found = false;
        for (Contact contact : this.contacts) {
            if (contact.getFirstName().equals(firstName)) {
                contactsFound.add(contact);
                found = true;
            }
        }
        if (!found) {
            Logger.printErrorMessage("There is no record with name " + firstName + " in the phonebook! \n");
        } else {
            Logger.printSuccessMessage("Contact/s with First Name " + firstName + " exists. Here are the details:");
            printContacts(contactsFound);
        }
    }

    public void searchContactByPhoneNumber() {
        String searchPhoneNumber = ValidationUtil.validateStringFromUserInput
                ("Phone Number", WORK_PHONE_NUMBER_PATTERN);

        List<Contact> contactsFound = new ArrayList<>();
        boolean found = false;
        for (Contact contact : this.contacts) {
            if (contact.getWorkPhoneNumber().equals(searchPhoneNumber) ||
                    contact.getPersonalPhoneNumber().equals(searchPhoneNumber)) {
                contactsFound.add(contact);
                found = true;
            }
        }
        if (!found) {
            Logger.printErrorMessage("There is no record with number " +
                    searchPhoneNumber + " in the phonebook! \n");
        } else {
            Logger.printSuccessMessage("Contact/s with phone number " +
                    searchPhoneNumber + " exists. Here are the details:");
            printContacts(contactsFound);
        }
    }

    public void editContact(Account account) {
        printContacts(contacts);

        Logger.printInfoMessage("Please select which row you want to edit or press 0 to return to the Main Menu: ");
        boolean correctInput = false;
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        while (!correctInput) {
            try {
                row = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
                continue;
            }
            if (row == 0) {
                return;
            }
            if (row <= contacts.size()) {
                correctInput = true;
            } else {
                Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
            }
        }
        Contact contact = this.contacts.get(row - 1);
        Logger.printInfoMessage("You are now editing contact: " +
                contact.getFirstName() + " " + contact.getLastName() + "\n");
        ProgramManagementUtil.openEditMenu(account, contact);
        Logger.printSuccessMessage("Changes are successfully applied!");
    }

    public void deleteContact() {
        printContacts(contacts);

        Logger.printInfoMessage("Please select which row you want to delete or press 0 to return to the Main Menu: ");
        boolean correctInput = false;
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        while (!correctInput) {
            try {
                row = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
                continue;
            }
            if (row == 0) {
                return;
            }
            if (row <= contacts.size()) {
                correctInput = true;
            } else {
                Logger.printErrorMessage("You have entered an invalid row. Please try again: ");
            }
        }
        Contact contact = this.contacts.get(row - 1);

        Logger.printInfoMessage("Are you sure you want to delete contact "
                + contact.getFirstName() + " " + contact.getLastName() + " ? [Y/N]: ");
        String confirm = ValidationUtil.validateYesNoFromUserInput();
        if (confirm.toUpperCase().equals("YES") || confirm.toUpperCase().equals("Y")) {
            contacts.remove(contact);
            Logger.printSuccessMessage("You have successfully deleted contact: "
                    + contact.getFirstName() + " " + contact.getLastName());
        } else {
            Logger.printInfoMessage("Contact " + contact.getFirstName() +
                    " " + contact.getLastName() + " was NOT deleted! \n");
        }
    }

    public void sortContacts() {
        Collections.sort(this.contacts);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
