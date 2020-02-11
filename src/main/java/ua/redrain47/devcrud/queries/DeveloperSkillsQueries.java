package ua.redrain47.devcrud.queries;

public interface DeveloperSkillsQueries {
    String INSERT_DEVELOPER_SKILLS_QUERY = "INSERT INTO developer_skills (developer_id, skill_id) VALUES (?, ?)";
    String SELECT_SKILLS_BY_DEVELOPER_ID_QUERY = "SELECT s.id, s.name FROM developer_skills ds " +
            "INNER JOIN skills s ON ds.skill_id = s.id WHERE developer_id = ?";
    String DELETE_DEVELOPER_SKILLS_QUERY = "DELETE FROM developer_skills " +
            "WHERE developer_id = ?";
}
