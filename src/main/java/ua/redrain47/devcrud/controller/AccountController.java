package ua.redrain47.devcrud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@Slf4j
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Account account) {
        log.debug("Request to create (POST)");

        if (isValidAccount(account)) {
            accountService.addData(account);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        Account account = accountService.getDataById(id);

        return (account != null)
                ? ResponseEntity.ok(account)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accounts = accountService.getAllData();

        return (accounts != null && accounts.size() > 0)
                ? ResponseEntity.ok(accounts)
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Account account) {
        if (isValidAccount(account)) {
            if (accountService.getDataById(account.getId()) == null) {
                return ResponseEntity.notFound().build();
            }

            accountService.updateDataById(account);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (accountService.getDataById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        accountService.deleteDataById(id);

        return ResponseEntity.ok().build();
    }

    private boolean isValidAccount(Account account) {
        return account != null && account.getEmail() != null
                && account.getAccountStatus() != null;
    }
}
