package ua.redrain47.hw11.messages;

public interface DeveloperMessages {
    // Menu messages
    String CHOOSE_MENU_ITEM_TEXT = "Please choose one of the following actions: ";
    String FIRST_MENU_ITEM_TEXT = "1 - Add Developer";
    String SECOND_MENU_ITEM_TEXT = "2 - Show Developer";
    String THIRD_MENU_ITEM_TEXT = "3 - Show all Developers";
    String FOURTH_MENU_ITEM_TEXT = "4 - Update Developer";
    String FIFTH_MENU_ITEM_TEXT = "5 - Delete Developer";
    String SIXTH_MENU_ITEM_TEXT = "6 - Exit";

    // Inviting messages
    String ENTER_DEVELOPER_FIRST_NAME_TEXT = "Enter first name:";
    String ENTER_DEVELOPER_LAST_NAME_TEXT = "Enter last name:";
    String ENTER_SKILL_NUMBER_TEXT = "Enter number of skills to add (at least 1):";
    String ENTER_ACCOUNT_DECISION = "Do you want to add account? (1 - yes, 0 - no)";
    String ENTER_NEW_DEVELOPER_FIRST_NAME_TEXT = "Enter new first name:";
    String ENTER_NEW_DEVELOPER_LAST_NAME_TEXT = "Enter new last name:";

    // Result messages
    String ADDED_DEVELOPER_TEXT = "Developer has been successfully added!";
    String UPDATED_DEVELOPER_TEXT = "Developer has been successfully updated!";
    String DELETED_DEVELOPER_TEXT = "Developer has been successfully deleted!";
    String NO_SUCH_DEVELOPER_TEXT = "Developer with such id doesn't exist!";

    // Error messages
    String SUCH_ID_HAS_BEEN_ALREADY_ENTERED_TEXT = "Such id has been already entered!";
}
