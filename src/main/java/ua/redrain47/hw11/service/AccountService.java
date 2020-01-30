package ua.redrain47.hw11.service;

import lombok.extern.slf4j.Slf4j;
import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.repository.AccountRepository;
import ua.redrain47.hw11.repository.jdbc.JdbcAccountRepositoryImpl;

import java.util.List;

@Slf4j
public class AccountService {
    private AccountRepository accountRepo;

    public AccountService() throws ConnectionIssueException{
        try {
            accountRepo = new JdbcAccountRepositoryImpl();
            log.debug("AccountService -> Instance created");
        } catch (ConnectionIssueException e) {
            log.error("AccountService -> " + e);
            throw e;
        }
    }

    public Account getDataById(Long id) throws ConnectionIssueException {
        try {
            Account account = accountRepo.getById(id);
            log.debug("AccountService -> Got data by id");
            return account;
        } catch (ConnectionIssueException e) {
            log.error("AccountService -> " + e);
            throw e;
        }
    }

    public List<Account> getAllData() throws ConnectionIssueException {
        try {
            List<Account> accountList = accountRepo.getAll();
            log.debug("AccountService -> Got all data");
            return accountList;
        } catch (ConnectionIssueException e) {
            log.error("AccountService -> " + e);
            throw e;
        }
    }

    public boolean addData(Account addedAccount)
            throws ConnectionIssueException, SuchEntityAlreadyExistsException {
        try {
            accountRepo.save(addedAccount);
            log.debug("AccountService -> Added data");
            return true;
        } catch (SuchEntityAlreadyExistsException
                | ConnectionIssueException e) {
            log.error("AccountService -> " + e);
            throw e;
        }
    }

    public boolean updateDataById(Account updatedAccount)
            throws ConnectionIssueException, SuchEntityAlreadyExistsException {
        try {
            accountRepo.update(updatedAccount);
            log.debug("AccountService -> Updated data by id");
            return true;
        } catch (SuchEntityAlreadyExistsException
                | ConnectionIssueException e) {
            log.error("AccountService -> " + e);
            throw e;
        }
    }

    public boolean deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            ConnectionIssueException {
        try {
            accountRepo.deleteById(id);
            log.debug("AccountService -> Deleted data by id");
            return true;
        } catch (DeletingReferencedRecordException
                | ConnectionIssueException e) {
            log.error("AccountService -> " + e);
            throw e;
        }
    }
}
