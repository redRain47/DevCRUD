package ua.redrain47.devcrud.repository.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.redrain47.devcrud.exceptions.DbStorageException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.model.AccountStatus;
import ua.redrain47.devcrud.model.Developer;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.repository.DeveloperRepository;
import ua.redrain47.devcrud.test_util.H2ConnectionUtil;
import ua.redrain47.devcrud.test_util.TempDbManager;

import java.sql.Connection;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JdbcDeveloperRepositoryImplTest {
    private DeveloperRepository developerRepo;
    private TempDbManager tempDbManager;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = H2ConnectionUtil.getConnection();
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
    public void shouldSaveCorrectly() throws DbStorageException, SuchEntityAlreadyExistsException {
        Account account = new Account(1L, "asd@sda.com", AccountStatus.ACTIVE);
        Skill javaSkill = new Skill(1L, "Java");
        Developer expectedNewDeveloper = new Developer(4L, "John",
                "Cena", Collections.singleton(javaSkill), account);
        developerRepo.save(expectedNewDeveloper);

        Developer savedNewDeveloper = developerRepo.getById(4L);

        assertEquals(expectedNewDeveloper, savedNewDeveloper);
    }

    @Test(expected = SuchEntityAlreadyExistsException.class)
    public void shouldNotSaveDuplicate() throws SuchEntityAlreadyExistsException, DbStorageException {
        Developer duplicatedSkill = developerRepo.getById(1L);
        developerRepo.save(duplicatedSkill);
    }

    @Test
    public void shouldGetDataById() throws DbStorageException {
        Account account = new Account(1L, "asd@sda.com", AccountStatus.ACTIVE);

        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(new Skill(1L, "Java"));
        skillSet.add(new Skill(2L, "C++"));

        Developer expectedDeveloper = new Developer(1L, "Rob",
                "Walker", skillSet, account);

        assertEquals(expectedDeveloper, developerRepo.getById(1L));
    }

    @Test
    public void shouldNotGetDataByNonExistedId() throws DbStorageException {
        assertNull(developerRepo.getById(5L));
    }

    @Test
    public void shouldGetAll() throws DbStorageException {
        List<Developer> expectedDeveloperList = new ArrayList<>();
        Skill javaSkill = new Skill(1L, "Java");
        Skill cppSkill = new Skill(2L, "C++");
        Skill pythonSkill = new Skill(3L, "Python");

        Set<Skill> firstDevSkillSet = new HashSet<>();
        firstDevSkillSet.add(javaSkill);
        firstDevSkillSet.add(cppSkill);

        Set<Skill> secondDevSkillSet = new HashSet<>();
        secondDevSkillSet.add(pythonSkill);
        secondDevSkillSet.add(cppSkill);

        expectedDeveloperList.add(new Developer(1L, "Rob",
                "Walker", firstDevSkillSet, new Account(1L, "asd@sda.com",
                AccountStatus.ACTIVE)));
        expectedDeveloperList.add(new Developer(2L, "Mike",
                "Vazovsky", secondDevSkillSet, new Account(2L,
                "qqqeqe@gmail.com", AccountStatus.BANNED)));
        expectedDeveloperList.add(new Developer(3L, "Dexter",
                "Morgan", Collections.singleton(javaSkill),
                new Account(3L, "zeref13@yandex.ru", AccountStatus.DELETED)));

        assertEquals(expectedDeveloperList, developerRepo.getAll());
    }

    @Test
    public void shouldUpdate() throws DbStorageException, SuchEntityAlreadyExistsException {
        Developer updatedDeveloper = developerRepo.getById(1L);

        updatedDeveloper.setLastName("Sins");
        developerRepo.update(updatedDeveloper);

        assertEquals(updatedDeveloper, developerRepo.getById(1L));
    }

    @Test(expected = SuchEntityAlreadyExistsException.class)
    public void shouldNotUpdateDueToDuplicate() throws DbStorageException, SuchEntityAlreadyExistsException {
        Developer developer = developerRepo.getById(1L);
        developer.setId(2L);
        developerRepo.update(developer);
    }

    @Test
    public void shouldDeleteCorrectly() throws DbStorageException, DeletingReferencedRecordException {
        List<Developer> allDevelopersButOne = developerRepo.getAll();

        allDevelopersButOne.remove(0);
        developerRepo.deleteById(1L);

        assertEquals(allDevelopersButOne, developerRepo.getAll());
    }
}