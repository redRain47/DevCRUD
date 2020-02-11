package ua.redrain47.devcrud.queries;

public interface AccountQueries {
    String INSERT_QUERY = "INSERT INTO accounts (email, account_status) VALUES (?, ?)";
    String SELECT_ALL_QUERY = "SELECT * FROM accounts ORDER BY id";
    String SELECT_BY_ID_QUERY = "SELECT * FROM accounts WHERE id = ?";
    String UPDATE_BY_ID_QUERY = "UPDATE accounts SET email = ?, account_status = ? WHERE id = ?";
    String DELETE_BY_ID_QUERY = "DELETE FROM accounts WHERE id = ?";
}
