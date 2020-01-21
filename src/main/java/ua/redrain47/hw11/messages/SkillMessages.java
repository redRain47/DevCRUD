package ua.redrain47.hw11.messages;

public interface SkillMessages {
    // Menu messages
    String CHOOSE_MENU_ITEM_TEXT = "Please choose one of the following actions: ";
    String FIRST_MENU_ITEM_TEXT = "1 - Add Skill";
    String SECOND_MENU_ITEM_TEXT = "2 - Show Skill";
    String THIRD_MENU_ITEM_TEXT = "3 - Show all Skills";
    String FOURTH_MENU_ITEM_TEXT = "4 - Update Skill";
    String FIFTH_MENU_ITEM_TEXT = "5 - Delete Skill";
    String SIXTH_MENU_ITEM_TEXT = "6 - Exit";

    // Inviting messages
    String ENTER_SKILL_NAME_TEXT = "Enter skill name:";
    String ENTER_NEW_SKILL_NAME_TEXT = "Enter new skill name:";

    // Result messages
    String ADDED_SKILL_TEXT = "Skill has been successfully added!";
    String UPDATED_SKILL_TEXT = "Skill has been successfully updated!";
    String DELETED_SKILL_TEXT = "Skill has been successfully deleted!";
    String NO_SUCH_SKILL_TEXT = "Skill with such id doesn't exist!";
}
