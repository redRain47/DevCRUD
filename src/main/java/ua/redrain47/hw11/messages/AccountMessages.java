package ua.redrain47.hw11.messages;

public interface AccountMessages {
    // Menu messages
    String CHOOSE_MENU_ITEM_TEXT = "Please choose one of the following actions: ";
    String FIRST_MENU_ITEM_TEXT = "1 - Add Account";
    String SECOND_MENU_ITEM_TEXT = "2 - Show Account";
    String THIRD_MENU_ITEM_TEXT = "3 - Show all Accounts";
    String FOURTH_MENU_ITEM_TEXT = "4 - Update Account";
    String FIFTH_MENU_ITEM_TEXT = "5 - Delete Account";
    String SIXTH_MENU_ITEM_TEXT = "6 - Exit";

    // Inviting messages
    String ENTER_EMAIL_TEXT = "Enter email:";
    String ENTER_ACCOUNT_STATUS_TEXT = "Enter account status (1 - ACTIVE, 2 - BANNED, 3 - DELETED):";
    String ENTER_NEW_EMAIL_TEXT = "Enter new email:";
    String ENTER_NEW_ACCOUNT_STATUS_TEXT = "Enter new account status (1 - ACTIVE, 2 - BANNED, 3 - DELETED):";

    // Result messages
    String ADDED_ACCOUNT_TEXT = "Account has been successfully added!";
    String UPDATED_ACCOUNT_TEXT = "Account has been successfully updated!";
    String DELETED_ACCOUNT_TEXT = "Account has been successfully deleted!";
    String NO_SUCH_ACCOUNT_TEXT = "Account with such id doesn't exist!";
}
