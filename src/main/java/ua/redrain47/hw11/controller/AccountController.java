package ua.redrain47.hw11.controller;

import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.repository.AccountRepository;
import ua.redrain47.hw11.repository.io.IOAccountRepositoryImpl;

import java.util.List;

public class AccountController {
    private AccountRepository accountRepo;

    public AccountController() {
        this.accountRepo = new IOAccountRepositoryImpl();
    }

    public Account getDataById(Long id) {
        return accountRepo.getById(id);
    }

    public List<Account> getAllData() {
        return accountRepo.getAll();
    }

    public boolean addData(Account addedAccount) {
        return accountRepo.save(addedAccount);
    }

    public boolean updateDataById(Account updatedAccount) {
        return accountRepo.update(updatedAccount);
    }

    public boolean deleteDataById(Long id) {
        return accountRepo.deleteById(id);
    }

    public boolean isEmptyData() {
        return accountRepo.isEmpty();
    }

    public Long getLastId() {
        return accountRepo.getAutoIncrId();
    }
}
