package ua.redrain47.hw11.repository;
import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.queries.DeveloperQueries;
import ua.redrain47.hw11.queries.DeveloperSkillsQueries;
import ua.redrain47.hw11.util.ConnectionUtil;
import ua.redrain47.hw11.util.ObjectMapper;

import java.sql.*;
import java.util.List;
import java.util.Set;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private Connection connection;

    public JdbcDeveloperRepositoryImpl() throws DbConnectionIssueException {
        try {
            connection = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new DbConnectionIssueException(e);
        }
    }

    public JdbcDeveloperRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Developer newDeveloper)
            throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(DeveloperQueries.INSERT_QUERY)) {

            preparedStatement.setString(1, newDeveloper.getFirstName());
            preparedStatement.setString(2, newDeveloper.getLastName());

            Account account = newDeveloper.getAccount();

            if (account != null) {
                preparedStatement.setInt(3,
                        account.getId().intValue());
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }

            preparedStatement.execute();
            insertDeveloperSkills(newDeveloper, false);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SuchEntityAlreadyExistsException(e);
        } catch (SQLException e) {
            throw new DbConnectionIssueException(e);
        }
    }

    @Override
    public Developer getById(Long searchId) throws DbConnectionIssueException {
        if (searchId == null) {
            return null;
        }

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(DeveloperQueries.SELECT_BY_ID_QUERY)){

            preparedStatement.setInt(1, searchId.intValue());

            ResultSet developerResultSet = preparedStatement.executeQuery();
            Developer foundDeveloper = null;
            List<Developer> developerList = ObjectMapper.mapToDeveloperList(developerResultSet);

            if (developerList != null && developerList.size() != 0) {
                foundDeveloper = developerList.get(0);
                setDeveloperSkillSet(foundDeveloper);
                setDeveloperAccount(foundDeveloper);
            }

            return foundDeveloper;
        } catch (SQLException e) {
            throw new DbConnectionIssueException(e);
        }
    }

    @Override
    public List<Developer> getAll() throws DbConnectionIssueException {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(DeveloperQueries.SELECT_ALL_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Developer> developerList = ObjectMapper.mapToDeveloperList(resultSet);

            for (Developer developer : developerList) {
                setDeveloperSkillSet(developer);
                setDeveloperAccount(developer);
            }

            return (developerList.size() != 0) ? developerList : null;
        } catch (SQLException e) {
            throw new DbConnectionIssueException(e);
        }
    }

    @Override
    public void update(Developer updatedDeveloper)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(DeveloperQueries.UPDATE_BY_ID_QUERY)) {

            preparedStatement.setString(1, updatedDeveloper.getFirstName());
            preparedStatement.setString(2, updatedDeveloper.getLastName());

            Account developerAccount = updatedDeveloper.getAccount();

            if (developerAccount != null) {
                preparedStatement.setInt(3, developerAccount.getId()
                        .intValue());
            } else {
                preparedStatement.setNull(3, Types.NULL);
            }

            preparedStatement.setInt(4, updatedDeveloper.getId().intValue());
            preparedStatement.execute();

            deleteAllDeveloperSkills(updatedDeveloper.getId());
            insertDeveloperSkills(updatedDeveloper, true);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SuchEntityAlreadyExistsException(e);
        } catch (SQLException e) {
            throw new DbConnectionIssueException(e);
        }
    }

    @Override
    public void deleteById(Long deletedId)
            throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        try (PreparedStatement  preparedStatement = connection
                .prepareStatement(DeveloperQueries.DELETE_BY_ID_QUERY)) {
            deleteAllDeveloperSkills(deletedId);

            preparedStatement.setInt(1, deletedId.intValue());
            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DeletingReferencedRecordException(e);
        } catch (SQLException e) {
            throw new DbConnectionIssueException(e);
        }
    }

    private void insertDeveloperSkills(Developer developer, boolean update)
            throws SQLException {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(DeveloperSkillsQueries
                        .INSERT_DEVELOPER_SKILLS_QUERY)) {

            Set<Skill> devSkillSet = developer.getSkillSet();
            int intDeveloperId = (update)
                    ? developer.getId().intValue()
                    : selectDeveloperId(developer).intValue();

            for (Skill skill : devSkillSet) {
                preparedStatement.setInt(1, intDeveloperId);
                preparedStatement.setInt(2, skill.getId().intValue());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }

    private Long selectDeveloperId(Developer developer) throws SQLException {
        if (developer == null) {
            return null;
        }

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(DeveloperQueries
                        .SELECT_LAST_INSERT_ID)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            return (!resultSet.next()) ? 0L : resultSet.getInt("id");
        }
    }

    private void deleteAllDeveloperSkills(Long developerId)
            throws SQLException {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(DeveloperSkillsQueries
                        .DELETE_DEVELOPER_SKILLS_QUERY)) {

            preparedStatement.setInt(1, developerId.intValue());
            preparedStatement.execute();
        }
    }

    private void setDeveloperSkillSet(Developer developer)
            throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DeveloperSkillsQueries
                .SELECT_SKILLS_BY_DEVELOPER_ID_QUERY)) {

            preparedStatement.setInt(1, developer.getId().intValue());

            ResultSet devSkillsResultSet = preparedStatement.executeQuery();
            Set<Skill> skillSet = ObjectMapper.mapToSkillSet(devSkillsResultSet);

            developer.setSkillSet(skillSet);
        }
    }

    private void setDeveloperAccount(Developer developer)
            throws DbConnectionIssueException {
        Account developerAccount = developer.getAccount();

        if (developerAccount != null) {
            AccountRepository accountRepo =
                    new JdbcAccountRepositoryImpl(connection);
            developer.setAccount((accountRepo
                    .getById(developerAccount.getId())));
        }
    }
}
