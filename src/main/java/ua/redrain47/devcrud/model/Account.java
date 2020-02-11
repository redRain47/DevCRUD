package ua.redrain47.devcrud.model;

import java.util.Objects;

public class Account {
    private Long id;
    private String email;
    private AccountStatus accountStatus;

    public Account(Long id, String email, AccountStatus accountStatus) {
        this.id = id;
        this.email = email;
        this.accountStatus = accountStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return id + " " + email + " " + accountStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(email, account.email) &&
                accountStatus == account.accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, accountStatus);
    }
}
