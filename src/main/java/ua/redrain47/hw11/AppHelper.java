package ua.redrain47.hw11;

import ua.redrain47.hw11.util.IntegerAnswer;
import ua.redrain47.hw11.view.AccountView;
import ua.redrain47.hw11.view.BaseView;
import ua.redrain47.hw11.view.DeveloperView;
import ua.redrain47.hw11.view.SkillView;

import static ua.redrain47.hw11.messages.MainMenuMessages.*;

public class AppHelper {
    public void start() {
        int answer;
        IntegerAnswer integerAnswer = new IntegerAnswer();
        BaseView chosenView = null;

        while (true) {
            System.out.println(CHOOSE_MENU_ITEM_TEXT);
            System.out.println(FIRST_MENU_ITEM_TEXT);
            System.out.println(SECOND_MENU_ITEM_TEXT);
            System.out.println(THIRD_MENU_ITEM_TEXT);

            answer = integerAnswer.enterAnswer(ENTER_YOUR_CHOICE, 0, 3);

            switch (answer) {
                case 0 : return;
                case 1 : chosenView = new DeveloperView(); break;
                case 2 : chosenView = new SkillView(); break;
                case 3 : chosenView = new AccountView(); break;
            }

            chosenView.run();
            System.out.println();
        }
    }
}
