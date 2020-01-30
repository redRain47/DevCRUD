package ua.redrain47.hw11.view;

import ua.redrain47.hw11.controller.AccountController;
import ua.redrain47.hw11.controller.DeveloperController;
import ua.redrain47.hw11.controller.SkillController;
import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.util.IntegerAnswer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static ua.redrain47.hw11.messages.AccountMessages.NO_SUCH_ACCOUNT_TEXT;
import static ua.redrain47.hw11.messages.CommonMessages.ENTER_MENU_ITEM_CHOICE_TEXT;
import static ua.redrain47.hw11.messages.CommonMessages.NO_DATA_TEXT;
import static ua.redrain47.hw11.messages.DeveloperMessages.*;
import static ua.redrain47.hw11.messages.SkillMessages.NO_SUCH_SKILL_TEXT;

public class DeveloperView extends BaseView {
    private SkillController skillController;
    private AccountController accountController;
    private DeveloperController developerController;
    private IntegerAnswer integerAnswer = new IntegerAnswer();

    public DeveloperView() {
        try {
            this.accountController = new AccountController();
            this.skillController = new SkillController();
            this.developerController = new DeveloperController();
        } catch (ConnectionIssueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        int answer;

        do {
            System.out.println(CHOOSE_MENU_ITEM_TEXT);
            System.out.println(FIRST_MENU_ITEM_TEXT);
            System.out.println(SECOND_MENU_ITEM_TEXT);
            System.out.println(THIRD_MENU_ITEM_TEXT);
            System.out.println(FOURTH_MENU_ITEM_TEXT);
            System.out.println(FIFTH_MENU_ITEM_TEXT);
            System.out.println(SIXTH_MENU_ITEM_TEXT);

            answer = integerAnswer.enterAnswer(ENTER_MENU_ITEM_CHOICE_TEXT,1, 6);

            switch (answer) {
                case 1 : addData(); break;
                case 2 : showData(); break;
                case 3 : showAllData(); break;
                case 4 : updateData(); break;
                case 5 : deleteData(); break;
            }

            System.out.println();
        } while (answer != 6);
    }

    @Override
    public void addData() {
        String firstName = enterName(ENTER_DEVELOPER_FIRST_NAME_TEXT);
        String lastName = enterName(ENTER_DEVELOPER_LAST_NAME_TEXT);
        Set<Skill> skillSet = enterSkills();
        Account account = enterAccount();
        Developer newDeveloper = new Developer(0L, firstName,
                lastName, skillSet, account);

        try {
            developerController.addData(newDeveloper);
            System.out.println(ADDED_DEVELOPER_TEXT);
        } catch (SuchEntityAlreadyExistsException
                | ConnectionIssueException e) {
            System.out.println(e.getMessage());
        }
    }

    private String enterName(String invitingMessage) {
        System.out.println(invitingMessage);
        return super.scanner.nextLine();
    }

    private Set<Skill> enterSkills() {
        Set<Long> skillIds = new HashSet<>();
        Set<Skill> skillSet = new HashSet<>();
        int skillNumber = integerAnswer.enterAnswer(ENTER_SKILL_NUMBER_TEXT,
                1, Integer.MAX_VALUE);
        Long tempId;
        Skill tempSkill;

        for (int i = 0; i < skillNumber; i++) {
            tempId = enterId();

            if (!skillIds.contains(tempId)) {
                try {
                    tempSkill = skillController.getDataById(tempId);

                    if (tempSkill != null) {
                        skillIds.add(tempId);
                        skillSet.add(tempSkill);
                        continue;
                    } else {
                        System.out.println(NO_SUCH_SKILL_TEXT);
                    }
                } catch (ConnectionIssueException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println(SUCH_ID_HAS_BEEN_ALREADY_ENTERED_TEXT);
            }

            --i;
        }

        return skillSet;
    }

    private Account enterAccount() {
        Long tempId;
        Account tempAccount;
        int answer;

        System.out.println();
        answer = integerAnswer.enterAnswer(ENTER_ACCOUNT_DECISION, 0, 1);

        if (answer == 0) {
            return null;
        }

        while (true) {
            tempId = enterId();
            try {
                tempAccount = accountController.getDataById(tempId);

                if (tempAccount == null) {
                    System.out.println(NO_SUCH_ACCOUNT_TEXT);
                } else {
                    return tempAccount;
                }
            } catch (ConnectionIssueException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void showData() {
        Long searchId = super.enterId();
        Developer requestedDeveloper;

        try {
            requestedDeveloper = developerController.getDataById(searchId);

            if (requestedDeveloper != null) {
                System.out.println(requestedDeveloper);
            } else {
                System.out.println(NO_SUCH_DEVELOPER_TEXT);
            }
        } catch (ConnectionIssueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showAllData() {
        ArrayList<Developer> allDevelopers;

        try {
            allDevelopers = (ArrayList<Developer>) developerController.getAllData();

            if (allDevelopers != null) {
                for (Developer developer : allDevelopers) {
                    System.out.println(developer);
                }
            } else {
                System.out.println(NO_DATA_TEXT);
            }
        } catch (ConnectionIssueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateData() {
        Long searchId = super.enterId();
        Developer requestedDeveloper;

        try {
            requestedDeveloper = developerController
                    .getDataById(searchId);

            if (requestedDeveloper != null) {
                String firstName = enterName(ENTER_NEW_DEVELOPER_FIRST_NAME_TEXT);
                String lastName = enterName(ENTER_NEW_DEVELOPER_LAST_NAME_TEXT);
                Set<Skill> skillSet = enterSkills();
                Account account = enterAccount();
                Developer newDeveloper = new Developer(requestedDeveloper.getId(),
                        firstName, lastName, skillSet, account);

                developerController.updateDataById(newDeveloper);
                System.out.println(UPDATED_DEVELOPER_TEXT);
            } else {
                System.out.println(NO_SUCH_DEVELOPER_TEXT);
            }
        } catch (ConnectionIssueException e) {
            System.out.println(e.getMessage());
        } catch (SuchEntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteData() {
        Long searchId = super.enterId();

        try {
            if (developerController.getDataById(searchId) != null) {
                developerController.deleteDataById(searchId);
                System.out.println(DELETED_DEVELOPER_TEXT);
            } else {
                System.out.println(NO_SUCH_DEVELOPER_TEXT);
            }
        } catch (ConnectionIssueException
                | DeletingReferencedRecordException e) {
            System.out.println(e.getMessage());
        }
    }
}
