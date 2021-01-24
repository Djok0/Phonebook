package project.contacts.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contact {

    List<Contact> contacts = new ArrayList<>();

    private String firstName;
    private String lastName;

    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;

    private String county;
    private String city;
    private String streetName;
    private String streetNumber;

    private String personalNumber;
    private String workNumber;

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Contact(String firstName, String lastName, int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        this(firstName, lastName);
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
    }

    public Contact(String firstName, String lastName, String county, String city, String street, String streetNumber) {
        this(firstName, lastName);
        this.county = county;
        this.city = city;
        this.streetName = street;
        this.streetNumber = streetNumber;
    }

    public Contact(String firstName, String lastName, String personalNumber) {
        this(firstName, lastName);
        this.personalNumber = personalNumber;
    }

    public Contact(String firstName, String lastName, String personalNumber, String workNumber) {
        this(firstName, lastName, personalNumber);
        this.workNumber = workNumber;
    }

    public Contact(String firstName, String lastName, int dayOfBirth,
                   int monthOfBirth, int yearOfBirth, String county, String city, String streetName,
                   String streetNumber, String personalNumber, String workNumber) {
        this(firstName, lastName);
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
        this.county = county;
        this.city = city;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.personalNumber = personalNumber;
        this.workNumber = workNumber;
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
    }

    public void printAllContacts() {
        System.out.println(contacts);
    }

    public boolean hasContact(Contact contact) {
        return this.contacts.contains(contact);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dayOfBirth=" + dayOfBirth +
                ", monthOfBirth=" + monthOfBirth +
                ", yearOfBirth=" + yearOfBirth +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", workNumber='" + workNumber + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }
}
