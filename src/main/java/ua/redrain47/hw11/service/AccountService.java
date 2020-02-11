package ua.redrain47.hw11.service;

import lombok.extern.slf4j.Slf4j;
import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.repository.AccountRepository;
import ua.redrain47.hw11.repository.JdbcAccountRepositoryImpl;

import java.util.List;

@Slf4j
public class AccountService {
    private AccountRepository accountRepo;

    public AccountService() throws DbConnectionIssueException {
        try {
            accountRepo = new JdbcAccountRepositoryImpl();
            log.debug("Instance created");
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account getDataById(Long id) throws DbConnectionIssueException {
        try {
            Account account = accountRepo.getById(id);
            log.debug("Got data by id");
            return account;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<Account> getAllData() throws DbConnectionIssueException {
        try {
            List<Account> accountList = accountRepo.getAll();
            log.debug("Got all data");
            return accountList;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean addData(Account addedAccount)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        try {
            accountRepo.save(addedAccount);
            log.debug("Added data");
            return true;
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean updateDataById(Account updatedAccount)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        try {
            accountRepo.update(updatedAccount);
            log.debug("Updated data by id");
            return true;
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        try {
            accountRepo.deleteById(id);
            log.debug("Deleted data by id");
            return true;
        } catch (DeletingReferencedRecordException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
