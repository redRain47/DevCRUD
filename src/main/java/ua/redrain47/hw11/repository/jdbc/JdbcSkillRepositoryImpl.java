package ua.redrain47.hw11.repository.jdbc;

import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.queries.SkillQueries;
import ua.redrain47.hw11.repository.SkillRepository;
import ua.redrain47.hw11.util.ConnectionUtil;
import ua.redrain47.hw11.util.ObjectMapper;

import java.sql.*;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {
    private Connection connection;

    public JdbcSkillRepositoryImpl() throws ConnectionIssueException {
        try {
            connection = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new ConnectionIssueException(e);
        }
    }

    @Override
    public boolean save(Skill newSkill)
            throws SuchEntityAlreadyExistsException, ConnectionIssueException {
        if (newSkill != null) {
            try ( PreparedStatement preparedStatement = connection
                    .prepareStatement(SkillQueries.INSERT_QUERY)) {
                preparedStatement.setString(1, newSkill.getName());

                preparedStatement.execute();
                preparedStatement.close();

                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new SuchEntityAlreadyExistsException(e);
            } catch (SQLException e) {
                throw new ConnectionIssueException(e);
            }
        }

        return false;
    }

    @Override
    public Skill getById(Long searchId) throws ConnectionIssueException {
        if (searchId == null) {
            return null;
        }

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SkillQueries.SELECT_BY_ID_QUERY)) {
            preparedStatement.setInt(1, searchId.intValue());

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Skill> skillList = ObjectMapper.mapToSkillList(resultSet);
            Skill foundSkill = null;

            if (skillList != null && skillList.size() != 0) {
                foundSkill = skillList.get(0);
            }

            return foundSkill;
        } catch (SQLException e) {
            throw new ConnectionIssueException(e);
        }
    }

    @Override
    public List<Skill> getAll() throws ConnectionIssueException {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SkillQueries.SELECT_ALL_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Skill> skillList = ObjectMapper.mapToSkillList(resultSet);

            return (skillList.size() != 0) ? skillList : null;
        } catch (SQLException e) {
            throw new ConnectionIssueException(e);
        }
    }

    @Override
    public boolean update(Skill updatedSkill)
            throws SuchEntityAlreadyExistsException, ConnectionIssueException {
        if (updatedSkill != null) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(SkillQueries.UPDATE_BY_ID_QUERY)) {
                preparedStatement.setString(1, updatedSkill.getName());
                preparedStatement.setInt(2, updatedSkill.getId().intValue());

                preparedStatement.execute();

                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new SuchEntityAlreadyExistsException(e);
            } catch (SQLException e) {
                throw new ConnectionIssueException(e);
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(Long deletedId)
            throws DeletingReferencedRecordException,
            ConnectionIssueException {
        if (deletedId != null) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(SkillQueries.DELETE_BY_ID_QUERY)) {
                preparedStatement.setInt(1, deletedId.intValue());

                preparedStatement.execute();
                preparedStatement.close();

                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new DeletingReferencedRecordException(e);
            } catch (SQLException e) {
                throw new ConnectionIssueException(e);
            }
        }

        return false;
    }
}
