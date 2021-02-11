package project.contacts;

import project.contacts.utils.AccountManagementUtil;

public class Demo {
    public static void main(String[] args) {
        AccountManagementUtil.logIn();
    }
}

// Tasks to be done after feedback v2:
// 1. doesn`t wait after the wrong number message so i can`t read it - add \n at the end of the message - DONE
// 1. enter row: java.land.NumberFormatException: For input string: "44444444444444444" - to make it to get only 6 digits - DONE
// 2. The edit is not recorded when I go back to the main menu. - Either to print once the user edited or to update in the menu - DONE
// 3. One Account = One Phonebook - DONE
// 4. Add validation of y/N answer
// 5. Add option to delete contact - priority low - because out of requirements
// 6. console to be cleared every time

//Feedback v2:
// Main menu:
// - console could be cleared every time
// - there could be a message which invited you to enter your choice - DONE
// Add record:
// - When entering choice or data it goes on the next row - use print instead of println - DONE
// - PHONE_NUMBER_PATTERN should allow +359 as well - DONE
// - no validation of y/N answer
// Edit record:
// - doesn`t wait after the wrong number message so i can`t read it
// - enter row: java.land.NumberFormatException: For input string: "44444444444444444"
// - work number should be 02460556 - and this is real number but is not accepted - DONE
// - You have entered an invalid birthday! Please try again: which is wrong? how to know it?
// - The edit is not recorded when I go back to the main menu.
// New account:
// - why I see the records which are part of another user phonebook?
// Delete contact:
// - Add this option
// Don`t see inheritance, abstraction and polymorphism


// To Do list: After the first consultation with Radi and Kalata
// 1. personal number = mandatory field - DONE
// 2. no nulls to show to the end user - DONE
// 2. Print to be more beautifully - DONE
// 3. Add message: Please try again: - DONE
// 4. use trim() when trying to enter space + input (example space + phone number) - DONE
// 5. where there are errors, use print err - DONE
// 6. Personal Number -> Personal Phone Number - DONE
// 7. keep password not in plain text (use hash for example) - DONE
// 8. Welcome, (add the comma) - DONE
// 9. validateBirthdayFromUserInput - move to -> createBirthday in Birthday - DONE
// 9. when moving validateBirthdayFromUserInput, check that the validation is working, currently it isn't - DONE
// 10. Create a new menu for Edit option - DONE
// 11. use toUpper to first letters of firstName and lastName - READY
