package ua.redrain47.devcrud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.redrain47.devcrud.config.RepositoryConfig;
import ua.redrain47.devcrud.exceptions.DbConnectionIssueException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.repository.AccountRepository;
import ua.redrain47.devcrud.model.Account;

import java.util.List;

@Service
@Import(RepositoryConfig.class)
@Slf4j
public class AccountService {
    private AccountRepository accountRepo;

//    public AccountService() throws DbConnectionIssueException {
//        try {
//            accountRepo = new JdbcAccountRepositoryImpl();
//            log.debug("Instance created");
//        } catch (DbConnectionIssueException e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    @Autowired
    public AccountService(@Qualifier("accountRepository") AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @ExceptionHandler(DbConnectionIssueException.class)
    public void handleException(DbConnectionIssueException e)
            throws DbConnectionIssueException {
        log.error(e.getMessage());
        throw e;
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

    public void addData(Account addedAccount)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        try {
            accountRepo.save(addedAccount);
            log.debug("Added data");
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void updateDataById(Account updatedAccount)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        try {
            accountRepo.update(updatedAccount);
            log.debug("Updated data by id");
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        try {
            accountRepo.deleteById(id);
            log.debug("Deleted data by id");
        } catch (DeletingReferencedRecordException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
