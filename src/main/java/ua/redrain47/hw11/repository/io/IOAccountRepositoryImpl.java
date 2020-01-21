package ua.redrain47.hw11.repository.io;

import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.model.AccountStatus;
import ua.redrain47.hw11.repository.AccountRepository;
import ua.redrain47.hw11.util.IOUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IOAccountRepositoryImpl implements AccountRepository {
    private IOUtil fileRepo = new IOUtil("src\\main\\resources\\files\\accounts.txt");
    private static final String SPLITTER = " ";
    private Long autoIncrId = 0L;

    public IOAccountRepositoryImpl() {
        ArrayList<Account> accounts = (ArrayList<Account>) this.getAll();

        if (accounts != null) {
            for (Account account : accounts) {
                autoIncrId = Math.max(account.getId(), autoIncrId);
            }
        }
    }

    @Override
    public boolean save(Account newAccount) {
        if (newAccount != null) {
            newAccount.setId(++autoIncrId);
            fileRepo.save(newAccount.toString());
            return true;
        }

        return false;
    }

    @Override
    public Account getById(Long searchId) {
        if (searchId != null) {
            ArrayList<Account> accounts = (ArrayList<Account>) this.getAll();

            if (accounts != null) {
                for (Account account : accounts) {
                    if (account.getId().equals(searchId)) {
                        return account;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public List<Account> getAll() {
        ArrayList<Account> accounts = null;
        ArrayList<String> records = (ArrayList<String>) fileRepo.readAll();

        if (records != null) {
            String[] splitData;
            Long curId;
            String curEmail;
            String curAccStatus;

            accounts = new ArrayList<>(20);

            for (String record : records) {
                if (!Objects.equals(record, "")) {
                    splitData = record.split(SPLITTER);
                    curId = Long.parseLong(splitData[0]);
                    curEmail = splitData[1];
                    curAccStatus = splitData[2];
                    accounts.add(new Account(curId, curEmail, AccountStatus.valueOf(curAccStatus)));
                }
            }
        }

        return accounts;
    }

    @Override
    public boolean update(Account updatedAccount) {
        if (updatedAccount != null) {
            Long searchId = updatedAccount.getId();
            ArrayList<Account> accounts = (ArrayList<Account>) this.getAll();

            if (accounts != null) {
                fileRepo.clear();
                for (Account account : accounts) {
                    if (account.getId().equals(searchId)) {
                        account.setEmail(updatedAccount.getEmail());
                        account.setAccountStatus(updatedAccount.getAccountStatus());
                    }
                    fileRepo.save(account.toString());
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(Long deletedId) {
        if (deletedId != null) {
            ArrayList<Account> accounts = (ArrayList<Account>) this.getAll();

            if (accounts != null) {
                fileRepo.clear();
                for (Account account : accounts) {
                    if (!account.getId().equals(deletedId)) {
                        fileRepo.save(account.toString());
                    }
                }

                if (fileRepo.isEmpty()) {
                    autoIncrId = 0L;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return fileRepo.isEmpty();
    }

    @Override
    public Long getAutoIncrId() {
        return autoIncrId;
    }
}
