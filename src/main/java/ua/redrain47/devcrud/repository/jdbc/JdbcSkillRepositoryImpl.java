package ua.redrain47.devcrud.repository.jdbc;

import org.springframework.stereotype.Component;
import ua.redrain47.devcrud.exceptions.DbStorageException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.queries.SkillQueries;
import ua.redrain47.devcrud.repository.SkillRepository;
import ua.redrain47.devcrud.util.ConnectionUtil;
import ua.redrain47.devcrud.util.ObjectMapper;

import java.sql.*;
import java.util.List;

@Component("skillRepository")
public class JdbcSkillRepositoryImpl implements SkillRepository {
    private Connection connection;

    public JdbcSkillRepositoryImpl() {
        try {
            connection = ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }

    public JdbcSkillRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Skill newSkill) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SkillQueries.INSERT_QUERY)) {
            preparedStatement.setString(1, newSkill.getName());

            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SuchEntityAlreadyExistsException(e);
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }

    @Override
    public Skill getById(Long searchId) {
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
            throw new DbStorageException(e);
        }
    }

    @Override
    public List<Skill> getAll() {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SkillQueries.SELECT_ALL_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Skill> skillList = ObjectMapper.mapToSkillList(resultSet);

            return (skillList.size() != 0) ? skillList : null;
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }

    @Override
    public void update(Skill updatedSkill) {
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SkillQueries.UPDATE_BY_ID_QUERY)) {
            preparedStatement.setString(1, updatedSkill.getName());
            preparedStatement.setInt(2, updatedSkill.getId().intValue());

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
                .prepareStatement(SkillQueries.DELETE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, deletedId.intValue());

            preparedStatement.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DeletingReferencedRecordException(e);
        } catch (SQLException e) {
            throw new DbStorageException(e);
        }
    }
}
