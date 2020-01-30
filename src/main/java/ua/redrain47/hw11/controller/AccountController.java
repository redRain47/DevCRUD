package ua.redrain47.hw11.controller;

import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.service.AccountService;

import java.util.List;

public class AccountController {
    private AccountService accountService = new AccountService();

    public AccountController() throws ConnectionIssueException {
    }

    public Account getDataById(Long id) throws ConnectionIssueException {
        return accountService.getDataById(id);
    }

    public List<Account> getAllData() throws ConnectionIssueException {
        return accountService.getAllData();
    }

    public boolean addData(Account addedAccount)
            throws ConnectionIssueException, SuchEntityAlreadyExistsException {
        return accountService.addData(addedAccount);
    }

    public boolean updateDataById(Account updatedAccount)
            throws ConnectionIssueException, SuchEntityAlreadyExistsException {
        return accountService.updateDataById(updatedAccount);
    }

    public boolean deleteDataById(Long id)  throws DeletingReferencedRecordException,
            ConnectionIssueException {
        return accountService.deleteDataById(id);
    }
}
