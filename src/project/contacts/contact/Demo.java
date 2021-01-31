package project.contacts.contact;

import project.contacts.utils.AccountManagementUtil;

public class Demo {
    public static void main(String[] args) {
//        Birthday georgiBirthday = new Birthday(30, 5, 1996);
//        Address georgiAddress = new Address("BG", "Pz", "Vasil Levski", "61");
//        Contact georgi = new Contact("Georgi", "Minkov", georgiBirthday,
//                georgiAddress, "0893513421", null);
//
//        List<Contact> contacts = new ArrayList<>();
//        contacts.add(georgi);
//
//        for (Contact contact : contacts) {
//            System.out.println(contact);
//        }



//        Account newAccount = new Account("testAdmin", "TestAdminPassword");
//        try {
//            String path = "src/resources/project/contacts/account/credentials";
//            String fileName = newAccount.getName() + "_credentials.txt";
//            String pathFileName = path + "/" + fileName;
//            File accountCredentials = new File(path, fileName);
//            if (!accountCredentials.exists()) {
//                System.out.println(accountCredentials.getAbsolutePath());
//                FileWriter storeCredentials = new FileWriter(pathFileName);
//                storeCredentials.write(newAccount.getName());
//                storeCredentials.write(",");
//                storeCredentials.write(newAccount.getPassword());
//                storeCredentials.write("\r\n");
//                storeCredentials.close();
//                System.out.println("File created: " + accountCredentials.getName());
//            } else {
//                System.out.println("File already exists " + accountCredentials.getName());
//                Writer writer = new BufferedWriter(new FileWriter(accountCredentials, true));
//                writer.write(newAccount.getName() + "," + newAccount.getPassword());
//                writer.write("\r\n");
//                writer.close();
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }

        // Test AccountManagementUtil2.logIn
//        AccountManagementUtil.logIn();

        // Test Phonebook.addContact()
//        Phonebook.addContact();

        // Test Phonebook.printAllContactsFromFile()
//        Phonebook.printAllContactsFromFile();

        // Test Phonebook.searchRecordByName()
//        Phonebook.searchRecordByName();

        // Test Phonebook.searchRecordByPhoneNumber();
//        Phonebook.searchRecordByPhoneNumber();

        // Test Phonebook.editRecord()
//        Phonebook.addContact();
//        Phonebook.printAllContactsFromFile();
//        Phonebook.editRecord();

    }
}
