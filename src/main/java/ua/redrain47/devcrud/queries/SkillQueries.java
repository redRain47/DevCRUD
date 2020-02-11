package ua.redrain47.devcrud.queries;

public interface SkillQueries {
    String INSERT_QUERY = "INSERT INTO skills (name) VALUES (?)";
    String SELECT_ALL_QUERY = "SELECT * FROM skills ORDER BY id";
    String SELECT_BY_ID_QUERY = "SELECT * FROM skills WHERE id = ?";
    String UPDATE_BY_ID_QUERY = "UPDATE skills SET name = ? WHERE id = ?";
    String DELETE_BY_ID_QUERY = "DELETE FROM skills WHERE id = ?";
}
