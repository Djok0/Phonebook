package project.contacts.contact;

import project.contacts.utils.ContactManagementUtil;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Contact person = new Contact("Georgi", "Minkov");
        Contact contactWithAddress = new Contact("Radina", "Kaytazova",
                "Bulgaria", "Sofia", "Vasil Levski", "61");
        Contact contactWithBirthday = new Contact("Radostina",
                "Velevska", 1, 1, 1994);
        Contact contactWithPersonalPhoneNumber = new Contact("Kaloyan",
                "Tsvetkov", "0888737475");
        Contact contactWithWorkPhoneNumber = new Contact("Anton", "Karakanovski",
                "0883848586", "0888776655");
        Contact contactWithFullData = new Contact ("Test", "Test",
                10, 5, 1995, "Test",
                "Test", "Test", "test", "test", "test");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(person);
        contacts.add(contactWithAddress);
        contacts.add(contactWithBirthday);
        contacts.add(contactWithPersonalPhoneNumber);
        contacts.add(contactWithWorkPhoneNumber);
        contacts.add(contactWithFullData);

        for (Contact contact : contacts) {
            System.out.println(contact);
        }


    }
}
