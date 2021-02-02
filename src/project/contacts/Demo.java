package project.contacts;

import project.contacts.utils.AccountManagementUtil;

public class Demo {
    public static void main(String[] args) {
        AccountManagementUtil.logIn();
    }
}

// To Do list: After the first consultation with Radi and Kalata
// 1. personal number = mandatory field
// 2. no nulls to show to the end user
// 2. Print to be more beautifully
// 3. Add message: Please try again:
// 4. use trim() when trying to enter space + input (example space + phone number)
// 5. use toUpper to first letters of firstName and lastName
// 6. where there are errors, use print err - DONE
// 7. Personal Number -> Personal Phone Number - DONE
// 8. keep password not in plain text (use hash for example)
// 9. Welcome, (add the comma) - DONE
// 10. validateBirthdayFromUserInput - move to -> createBirthday in Birthday
