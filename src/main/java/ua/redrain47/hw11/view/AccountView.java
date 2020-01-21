package ua.redrain47.hw11.view;

import ua.redrain47.hw11.controller.AccountController;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.model.AccountStatus;
import ua.redrain47.hw11.util.IntegerAnswer;

import java.util.ArrayList;

import static ua.redrain47.hw11.messages.AccountMessages.*;
import static ua.redrain47.hw11.messages.CommonMessages.ENTER_MENU_ITEM_CHOICE_TEXT;
import static ua.redrain47.hw11.messages.CommonMessages.NO_DATA_TEXT;

public class AccountView extends BaseView {
    private AccountController accountController = new AccountController();
    private IntegerAnswer integerAnswer = new IntegerAnswer();

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
        accountController.addData(new Account(0L, enterEmail(false),
                enterAccountStatus(false)));
        System.out.println(ADDED_ACCOUNT_TEXT);
    }

    private String enterEmail(boolean update) {
        if (!update) {
            System.out.println(ENTER_EMAIL_TEXT);
        } else {
            System.out.println(ENTER_NEW_EMAIL_TEXT);
        }

        return super.scanner.nextLine();
    }

    private AccountStatus enterAccountStatus(boolean update) {
        String invitingMessage = (!update)
                ? ENTER_ACCOUNT_STATUS_TEXT
                : ENTER_NEW_ACCOUNT_STATUS_TEXT;
        int answer = integerAnswer.enterAnswer(invitingMessage,1, 3);
        AccountStatus result = null;

        switch (answer) {
            case 1 : result = AccountStatus.ACTIVE; break;
            case 2 : result = AccountStatus.BANNED; break;
            case 3 : result = AccountStatus.DELETED; break;
        }

        return result;
    }

    @Override
    public void showData() {
        Long searchId = super.enterId();
        Account requestedAccount = accountController.getDataById(searchId);

        if (requestedAccount != null) {
            System.out.println(" " + requestedAccount.getId() + " | "
                    + requestedAccount.getEmail() + " | "
                    + requestedAccount.getAccountStatus());
        } else {
            System.out.println(NO_SUCH_ACCOUNT_TEXT);
        }
    }

    @Override
    public void showAllData() {
        ArrayList<Account> allAccounts = (ArrayList<Account>) accountController.getAllData();

        if (allAccounts != null) {
            for (Account account : allAccounts) {
                System.out.println(" " + account.getId() + " | "
                        + account.getEmail() + " | "
                        + account.getAccountStatus());
            }
        } else {
            System.out.println(NO_DATA_TEXT);
        }
    }

    @Override
    public void updateData() {
        Long searchId = super.enterId();
        Account requestedAccount = accountController.getDataById(searchId);

        if (requestedAccount != null) {
            requestedAccount.setEmail(enterEmail(true));
            requestedAccount.setAccountStatus(enterAccountStatus(true));

            accountController.updateDataById(requestedAccount);

            System.out.println(UPDATED_ACCOUNT_TEXT);
        } else {
            System.out.println(NO_SUCH_ACCOUNT_TEXT);
        }
    }

    @Override
    public void deleteData() {
        Long searchId = super.enterId();

        if (accountController.getDataById(searchId) != null) {
            accountController.deleteDataById(searchId);
            System.out.println(DELETED_ACCOUNT_TEXT);
        } else {
            System.out.println(NO_SUCH_ACCOUNT_TEXT);
        }
    }
}
