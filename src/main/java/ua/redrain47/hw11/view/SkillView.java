package ua.redrain47.hw11.view;

import ua.redrain47.hw11.controller.SkillController;
import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.util.IntegerAnswer;

import java.util.ArrayList;

import static ua.redrain47.hw11.messages.SkillMessages.*;
import static ua.redrain47.hw11.messages.CommonMessages.*;

public class SkillView extends BaseView {
    private SkillController skillController;
    private IntegerAnswer integerAnswer = new IntegerAnswer();

    public SkillView() {
        try {
            this.skillController = new SkillController();
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

            answer = integerAnswer.enterAnswer(ENTER_MENU_ITEM_CHOICE_TEXT,1,6);

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
        System.out.println(ENTER_SKILL_NAME_TEXT);
        try {
            skillController.addData(new Skill(0L, super.scanner.nextLine()));
            System.out.println(ADDED_SKILL_TEXT);
        } catch (ConnectionIssueException
                | SuchEntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showData() {
        Long searchId = super.enterId();
        Skill requestedSkill;

        try {
            requestedSkill = skillController.getDataById(searchId);

            if (requestedSkill != null) {
                System.out.println("\n " + requestedSkill.getId() + " | "
                        + requestedSkill.getName());
            } else {
                System.out.println(NO_SUCH_SKILL_TEXT);
            }
        } catch (ConnectionIssueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showAllData() {
        ArrayList<Skill> allSkills;

        try {
            allSkills = (ArrayList<Skill>) skillController.getAllData();

            if (allSkills != null) {
                for (Skill skill : allSkills) {
                    System.out.println(" " + skill.getId() + " | "
                            + skill.getName());
                }
            } else {
                System.out.println(NO_DATA_TEXT);
                System.out.println();
            }
        } catch (ConnectionIssueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateData() {
        Long searchId = super.enterId();
        Skill requestedSkill;

        try {
            requestedSkill = skillController.getDataById(searchId);

            if (requestedSkill != null) {
                System.out.println(ENTER_NEW_SKILL_NAME_TEXT);

                requestedSkill.setName(scanner.nextLine());
                skillController.updateDataById(requestedSkill);

                System.out.println(UPDATED_SKILL_TEXT);
            } else {
                System.out.println(NO_SUCH_SKILL_TEXT);
            }
        } catch (ConnectionIssueException
                | SuchEntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteData() {
        Long searchId = super.enterId();
        Skill requestedSkill;

        try {
            requestedSkill = skillController.getDataById(searchId);

            if (requestedSkill != null) {
                skillController.deleteDataById(searchId);
                System.out.println(DELETED_SKILL_TEXT);
            } else {
                System.out.println(NO_SUCH_SKILL_TEXT);
            }
        } catch (ConnectionIssueException
                | DeletingReferencedRecordException e) {
            System.out.println(e.getMessage());
        }
    }
}
