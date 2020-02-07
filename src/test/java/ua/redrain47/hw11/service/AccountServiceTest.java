package ua.redrain47.hw11.service;

import lombok.SneakyThrows;
import org.junit.Test;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.repository.AccountRepository;

import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private static AccountRepository accountRepo = mock(AccountRepository.class);
    private AccountService accountService = new AccountService(accountRepo);
    private Account testAccount = new Account(null, null, null);

    @SneakyThrows
    @Test
    public void shouldInvokeSave() {
        accountService.addData(testAccount);
        verify(accountRepo, times(1)).save(testAccount);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeGetById() {
        accountService.getDataById(1L);
        verify(accountRepo, times(1)).getById(1L);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeGetAll() {
        accountService.getAllData();
        verify(accountRepo, times(1)).getAll();
    }

    @SneakyThrows
    @Test
    public void shouldInvokeUpdate() {
        accountService.updateDataById(testAccount);
        verify(accountRepo, times(1)).update(testAccount);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeDelete() {
        accountService.deleteDataById(1L);
        verify(accountRepo, times(1)).deleteById(1L);
    }
}