package ua.redrain47.devcrud.repository.jdbc;

import org.springframework.stereotype.Component;
import ua.redrain47.devcrud.exceptions.DbStorageException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.queries.AccountQueries;
import ua.redrain47.devcrud.repository.AccountRepository;
import ua.redrain47.devcrud.util.ConnectionUtil;
import ua.redrain47.devcrud.util.ObjectMapper;

import java.sql.*;
import java.util.List;

@Component("accountRepository")
public class JdbcAccountRepositoryImpl implements AccountRepository {
    private Connection connection;

    public JdbcAccountRepositoryImpl() {
        try {
            this.connection = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }

    public JdbcAccountRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Account newAccount) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(AccountQueries.INSERT_QUERY)) {

            preparedStatement.setString(1, newAccount.getEmail());
            preparedStatement.setString(2,
                    newAccount.getAccountStatus().toString());

            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SuchEntityAlreadyExistsException(e);
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }

    @Override
    public Account getById(Long searchId) {
        if (searchId == null) {
            return null;
        }

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(AccountQueries.SELECT_BY_ID_QUERY)) {

            preparedStatement.setInt(1, searchId.intValue());

            ResultSet resultSet = preparedStatement.executeQuery();
            Account foundAccount = null;
            List<Account> accountList = ObjectMapper.mapToAccountList(resultSet);

            if (accountList != null && accountList.size() != 0) {
                foundAccount = accountList.get(0);
            }

            return foundAccount;
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }

    @Override
    public List<Account> getAll() {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(AccountQueries.SELECT_ALL_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Account> accountList = ObjectMapper.mapToAccountList(resultSet);

            return (accountList.size() != 0) ? accountList : null;
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }

    @Override
    public void update(Account updatedAccount) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(AccountQueries.UPDATE_BY_ID_QUERY)) {
            preparedStatement.setString(1, updatedAccount.getEmail());
            preparedStatement.setString(2,
                    updatedAccount.getAccountStatus().toString());
            preparedStatement.setInt(3, updatedAccount.getId().intValue());

            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SuchEntityAlreadyExistsException(e);
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }

    @Override
    public void deleteById(Long deletedId) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(AccountQueries.DELETE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, deletedId.intValue());
            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DeletingReferencedRecordException(e);
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }
}
