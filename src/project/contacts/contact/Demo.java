package project.contacts.contact;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Birthday georgiBirthday = new Birthday(30, 5, 1996);
        Address georgiAddress = new Address("BG", "Pz", "Vasil Levski", "61");
        Contact georgi = new Contact("Georgi", "Minkov", georgiBirthday,
                georgiAddress, "0893513421", null);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(georgi);

        for (Contact contact : contacts) {
            System.out.println(contact);
        }


    }
}
