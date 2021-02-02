# Project for Java Training - Contacts

Topic: Phonebook, address book and birthdays

Requirements:
- OOP principles
- Using files - read, write;
- Using Collections
- Error handling
- Version Control System - github
- User to interact with a menu. The work not to end before the user chooses "Exit".
Example:
  - Option 1
  - Option 2
  - Option 3
  - Exit
- Well formatted code

As a user I expect to have the following options available:
- When I try to log in the phonebook, an authentication is required (name and password)
- To be able to look into all records - by name and sorted alphabetically
- To be able to choose a specific record and to see all of the following information for it:
    - First Name
    - Last Name
    - Address (If there is one entered)
    - Number - Personal, Work (if there is one entered)
    - Birthday (If there is one entered)
- To be able to search by name or by number
- To be able to add new contact
- To be able to delete a contact
- To be able to edit the information of an already existing contact
- To be able to change the password of my user

Design and architecture:
class Account
  String name
  String password
  logIn() - initial login data: name: admin   password: admin
  createAccount(String name, String password)
  changePassword(String password)

- class Contact
  - String firstName
  - String lastName
  - int contactId - unique identifier for each contact - this should have only getter
  - getRecords() - list all contacts alphabetically
  - getRecord(int contactId) - list all of the information for a specific contact
  - getRecord(String firstName, String lastName) - overloading - list all of the information for a specific contact
  - addContact(Contact contact) - contactId++ - this should only be incremented
  - removeContact(Contact contact)
  - editContact(Contact contact)

  - class Phonebook
    - String personalPhoneNumber
    - String workPhoneNumber
    - getRecord(String number) - overriding - list all of the information for a specific contact

  - class AddressBook
    - String address
  - class Birthday
    - int date
    - int month
    - int year

- class Demo
  - Main
    - logIn()
    - Menu
