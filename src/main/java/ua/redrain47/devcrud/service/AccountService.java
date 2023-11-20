package ua.redrain47.devcrud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.repository.AccountRepository;

import java.util.List;

@Service
@Slf4j
public class AccountService implements Serviceable<Account, Long> {
    private AccountRepository accountRepo;

    @Autowired
    public AccountService(@Qualifier("accountRepository") AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account getDataById(Long id) {
        Account account = accountRepo.getById(id);
        log.debug("Got data by id");
        return account;
    }

    public List<Account> getAllData() {
        List<Account> accountList = accountRepo.getAll();
        log.debug("Got all data");
        return accountList;
    }

    public void addData(Account addedAccount) {
        accountRepo.save(addedAccount);
        log.debug("Added data");
    }

    public void updateDataById(Account updatedAccount) {
        accountRepo.update(updatedAccount);
        log.debug("Updated data by id");
    }

    public void deleteDataById(Long id) {
        accountRepo.deleteById(id);
        log.debug("Deleted data by id");
    }
}
