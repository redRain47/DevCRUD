package ua.redrain47.devcrud.queries;

public interface DeveloperQueries {
    String INSERT_QUERY = "INSERT INTO developers (first_name, last_name, account_id) VALUES (?, ?, ?)";
    String SELECT_ALL_QUERY = "SELECT * FROM developers ORDER BY id";
    String SELECT_BY_ID_QUERY = "SELECT * FROM developers WHERE id = ?";
    String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() AS id FROM developers";
    String UPDATE_BY_ID_QUERY = "UPDATE developers SET first_name = ?, last_name = ?, account_id = ? WHERE id = ?";
    String DELETE_BY_ID_QUERY = "DELETE FROM developers WHERE id = ?";
}
