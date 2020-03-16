package ua.redrain47.devcrud.repository.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.redrain47.devcrud.exceptions.DbStorageException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.model.AccountStatus;
import ua.redrain47.devcrud.repository.AccountRepository;
import ua.redrain47.devcrud.repository.DeveloperRepository;
import ua.redrain47.devcrud.test_util.H2ConnectionUtil;
import ua.redrain47.devcrud.test_util.TempDbManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JdbcAccountRepositoryImplTest {
    private AccountRepository accountRepo;
    private DeveloperRepository developerRepo; // to delete referenced rows
    private TempDbManager tempDbManager;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = H2ConnectionUtil.getConnection();
        accountRepo = new JdbcAccountRepositoryImpl(connection);
        developerRepo = new JdbcDeveloperRepositoryImpl(connection);
        tempDbManager = new TempDbManager(connection);
        tempDbManager.createDatabase();
        tempDbManager.populateDatabase();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void shouldSaveCorrectly() throws DbStorageException, SuchEntityAlreadyExistsException {
        Account expectedNewAccount = new Account(4L, "qwert123@mail.com",
                AccountStatus.ACTIVE);
        accountRepo.save(expectedNewAccount);

        Account savedNewAccount = accountRepo.getById(4L);

        assertEquals(expectedNewAccount, savedNewAccount);
    }

    @Test(expected = SuchEntityAlreadyExistsException.class)
    public void shouldNotSaveDuplicate() throws SuchEntityAlreadyExistsException, DbStorageException {
        Account duplicatedAccount = new Account(1L, "asd@sda.com",
                AccountStatus.ACTIVE);
        accountRepo.save(duplicatedAccount);
    }

    @Test
    public void shouldGetDataById() throws DbStorageException {
        Account expectedAccount = new Account(1L, "asd@sda.com",
                AccountStatus.ACTIVE);
        assertEquals(expectedAccount, accountRepo.getById(1L));
    }

    @Test
    public void shouldNotGetDataByNonExistedId() throws DbStorageException {
        assertNull(accountRepo.getById(5L));
    }

    @Test
    public void shouldGetAll() throws DbStorageException {
        List<Account> expectedAccountList = new ArrayList<>();

        expectedAccountList.add(new Account(1L, "asd@sda.com",
                AccountStatus.ACTIVE));
        expectedAccountList.add(new Account(2L, "qqqeqe@gmail.com",
                AccountStatus.BANNED));
        expectedAccountList.add(new Account(3L, "zeref13@yandex.ru",
                AccountStatus.DELETED));

        assertEquals(expectedAccountList, accountRepo.getAll());
    }

    @Test
    public void shouldUpdate() throws DbStorageException, SuchEntityAlreadyExistsException {
        Account updatedAccount = accountRepo.getById(1L);

        updatedAccount.setEmail("qwerty@oroal.com");
        accountRepo.update(updatedAccount);

        assertEquals(updatedAccount, accountRepo.getById(1L));
    }

    @Test(expected = SuchEntityAlreadyExistsException.class)
    public void shouldNotUpdateDueToDuplicate() throws DbStorageException, SuchEntityAlreadyExistsException {
        Account account = accountRepo.getById(1L);
        account.setId(2L);
        accountRepo.update(account);
    }

    @Test
    public void shouldDeleteCorrectly() throws DbStorageException, DeletingReferencedRecordException {
        List<Account> allAccountsButOne = accountRepo.getAll();

        allAccountsButOne.remove(0); // id: 1
        developerRepo.deleteById(1L); // deleting developer referencing on this account
        accountRepo.deleteById(1L);

        assertEquals(allAccountsButOne, accountRepo.getAll());
    }

    @Test(expected = DeletingReferencedRecordException.class)
    public void shouldNotDeleteReferencedRow() throws DbStorageException, DeletingReferencedRecordException {
        accountRepo.deleteById(1L);
    }
}