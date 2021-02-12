package project.contacts.contact;

import project.contacts.utils.Logger;

import java.util.Scanner;

import static project.contacts.utils.ValidationUtil.isValidDate;

public class Birthday {
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;

    public Birthday(int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
    }

    public static Birthday createBirthday() {
        Scanner scanner = new Scanner(System.in);
        boolean validDate = false;
        int day = 0, month = 0, year = 0;
        while (!validDate) {
            try {
                Logger.printInfoMessage("Enter Contact's Day of Birth: ");
                day = Integer.parseInt(scanner.nextLine().trim());
                Logger.printInfoMessage("Enter Contact's Month of Birth: ");
                month = Integer.parseInt(scanner.nextLine().trim());
                Logger.printInfoMessage("Enter Contact's Year of Birth: ");
                year = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                Logger.printErrorMessage("You have entered an invalid birthday! Please try again: \n");
                continue;
            }
            validDate = isValidDate(month, day, year);
            if (!validDate) {
                Logger.printErrorMessage("You have entered an invalid birthday! Please try again: \n");
            }
        }
        return new Birthday(day, month, year);
    }

    @Override
    public String toString() {
        return ", dayOfBirth='" + dayOfBirth + '\'' +
                ", monthOfBirth='" + monthOfBirth + '\'' +
                ", yearOfBirth='" + yearOfBirth + '\'';
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
}
