package ua.redrain47.hw11.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.model.AccountStatus;
import ua.redrain47.hw11.repository.io.IOAccountRepositoryImpl;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IOAccountRepositoryImplTest {
    private IOAccountRepositoryImpl accountRepo = new IOAccountRepositoryImpl();
    private Long lastId;

    @Before
    public void getLastId() {
        lastId = accountRepo.getAutoIncrId();
    }

    @After
    public void deleteChanges() {
        Long curId = lastId;

        do {
            ++curId;
        } while (accountRepo.deleteById(curId));
    }

    @Test
    public void shouldSaveAndGetById() {
        Account searchAccount = new Account(lastId + 1, "asda@adds.com", AccountStatus.ACTIVE);

        accountRepo.save(searchAccount);
        Account resultAccount = accountRepo.getById(searchAccount.getId());

        assertEquals(searchAccount, resultAccount);
    }

    @Test
    public void shouldNotGetByNullId() {
        Account resultAccount = accountRepo.getById(null);
        assertNull(resultAccount);
    }

    @Test
    public void shouldNotSaveNull() {
        assertFalse(accountRepo.save(null));
    }

    @Test
    public void shouldUpdate() {
        Account account1 = new Account(lastId + 1, "asda@adds.com",
                AccountStatus.ACTIVE);
        Account account2 = new Account(lastId + 2, "xyz@xyz.ua",
                AccountStatus.BANNED);
        Account updatedAccount2 = new Account(lastId + 2, "qwerty@xyz.ua",
                AccountStatus.DELETED);

        accountRepo.save(account1);
        accountRepo.save(account2);
        accountRepo.update(updatedAccount2);

        assertEquals(updatedAccount2, accountRepo.getById(lastId + 2));
    }

    @Test
    public void shouldNotUpdateByNull() {
        assertFalse( accountRepo.update(null));
    }

    @Test
    public void shouldGetAll() {
        Account account1 = new Account(lastId + 1, "asda@adds.com",
                AccountStatus.ACTIVE);
        Account account2 = new Account(lastId + 2, "xyz@xyz.ua",
                AccountStatus.BANNED);
        ArrayList<Account> expectedAccounts = new ArrayList<>();

        expectedAccounts.add(account1);
        expectedAccounts.add(account2);
        accountRepo.save(account1);
        accountRepo.save(account2);

        ArrayList<Account> resultAccounts = (ArrayList<Account>) accountRepo.getAll();

        // deleting untestable data from arraylist
        for (int i = 0; expectedAccounts.size() != resultAccounts.size(); i++) {
            resultAccounts.remove(i);
        }

        assertArrayEquals(expectedAccounts.toArray(), resultAccounts.toArray());
    }

    @Test
    public void shouldDeleteById() {
        Account account1 = new Account(lastId + 1, "asda@adds.com",
                AccountStatus.ACTIVE);
        Account account2 = new Account(lastId + 2, "xyz@xyz.ua",
                AccountStatus.BANNED);

        accountRepo.save(account1);
        accountRepo.save(account2);
        accountRepo.deleteById(lastId + 1);

        assertNull(accountRepo.getById(lastId + 1));
    }

    @Test
    public void shouldNotDeleteById() {
        Account account1 = new Account(lastId + 1, "asda@adds.com",
                AccountStatus.ACTIVE);

        accountRepo.save(account1);
        accountRepo.deleteById(lastId + 3);

        assertEquals(account1, accountRepo.getById(lastId + 1));
    }
}