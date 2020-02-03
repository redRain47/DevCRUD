package ua.redrain47.hw11.controller;

import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.service.AccountService;

import java.util.List;

public class AccountController {
    private AccountService accountService = new AccountService();

    public AccountController() throws DbConnectionIssueException {
    }

    public Account getDataById(Long id) throws DbConnectionIssueException {
        return accountService.getDataById(id);
    }

    public List<Account> getAllData() throws DbConnectionIssueException {
        return accountService.getAllData();
    }

    public boolean addData(Account addedAccount)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        return accountService.addData(addedAccount);
    }

    public boolean updateDataById(Account updatedAccount)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        return accountService.updateDataById(updatedAccount);
    }

    public boolean deleteDataById(Long id)  throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        return accountService.deleteDataById(id);
    }
}
