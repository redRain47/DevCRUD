package ua.redrain47.devcrud.repository.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.redrain47.devcrud.exceptions.DbConnectionIssueException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.repository.DeveloperRepository;
import ua.redrain47.devcrud.repository.SkillRepository;
import ua.redrain47.devcrud.repository.jdbc.JdbcDeveloperRepositoryImpl;
import ua.redrain47.devcrud.repository.jdbc.JdbcSkillRepositoryImpl;
import ua.redrain47.devcrud.test_util.H2ConnectionUtil;
import ua.redrain47.devcrud.test_util.TempDbManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcSkillRepositoryImplTest {
    private SkillRepository skillRepo;
    private DeveloperRepository developerRepo;
    private TempDbManager tempDbManager;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = H2ConnectionUtil.getConnection();
        skillRepo = new JdbcSkillRepositoryImpl(connection);
        developerRepo = new JdbcDeveloperRepositoryImpl(connection);
        tempDbManager = new TempDbManager(connection);
        tempDbManager.createDatabase();
        tempDbManager.populateDatabase();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void shouldSaveCorrectly() throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        Skill expectedNewSkill = new Skill(4L, "C");
        skillRepo.save(expectedNewSkill);

        Skill savedNewSkill = skillRepo.getById(4L);

        assertEquals(expectedNewSkill, savedNewSkill);
    }

    @Test(expected = SuchEntityAlreadyExistsException.class)
    public void shouldNotSaveDuplicate() throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        Skill duplicatedSkill = new Skill(1L, "Java");
        skillRepo.save(duplicatedSkill);
    }

    @Test
    public void shouldGetDataById() throws DbConnectionIssueException {
        Skill expectedSkill = new Skill(1L, "Java");
        assertEquals(expectedSkill, skillRepo.getById(1L));
    }

    @Test
    public void shouldNotGetDataByNonExistedId() throws DbConnectionIssueException {
        assertNull(skillRepo.getById(5L));
    }

    @Test
    public void shouldGetAll() throws DbConnectionIssueException {
        List<Skill> expectedSkillList = new ArrayList<>();

        expectedSkillList.add(new Skill(1L, "Java"));
        expectedSkillList.add(new Skill(2L, "C++"));
        expectedSkillList.add(new Skill(3L, "Python"));

        assertEquals(expectedSkillList, skillRepo.getAll());
    }

    @Test
    public void shouldUpdate() throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        Skill updatedSkill = skillRepo.getById(1L);

        updatedSkill.setName("Rust");
        skillRepo.update(updatedSkill);

        assertEquals(updatedSkill, skillRepo.getById(1L));
    }

    @Test(expected = SuchEntityAlreadyExistsException.class)
    public void shouldNotUpdateDueToDuplicate() throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        Skill skill = skillRepo.getById(1L);
        skill.setId(2L);
        skillRepo.update(skill);
    }

    @Test
    public void shouldDeleteCorrectly() throws DbConnectionIssueException, DeletingReferencedRecordException {
        List<Skill> allSkillsButOne = skillRepo.getAll();

        allSkillsButOne.remove(0);

        // deleting developers referencing on this skill
        developerRepo.deleteById(1L);
        developerRepo.deleteById(3L);

        skillRepo.deleteById(1L);

        assertEquals(allSkillsButOne, skillRepo.getAll());
    }

    @Test(expected = DeletingReferencedRecordException.class)
    public void shouldNotDeleteReferencedRow() throws DbConnectionIssueException, DeletingReferencedRecordException {
        skillRepo.deleteById(1L);
    }
}