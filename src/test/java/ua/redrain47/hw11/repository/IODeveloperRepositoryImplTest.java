package ua.redrain47.hw11.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.model.AccountStatus;
import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.repository.io.IOAccountRepositoryImpl;
import ua.redrain47.hw11.repository.io.IODeveloperRepositoryImpl;
import ua.redrain47.hw11.repository.io.IOSkillRepositoryImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class IODeveloperRepositoryImplTest {
    private IODeveloperRepositoryImpl devRepo = new IODeveloperRepositoryImpl();
    private IOAccountRepositoryImpl accountRepo = new IOAccountRepositoryImpl();
    private IOSkillRepositoryImpl skillRepo = new IOSkillRepositoryImpl();
    private Long lastDeveloperId;
    private Long lastSkillId;
    private Long lastAccountId;
    private Account account1;
    private Account account2;
    private Set<Skill> skillSet;

    @Before
    public void setUp() {
        lastDeveloperId = devRepo.getAutoIncrId();
        lastSkillId = skillRepo.getAutoIncrId();
        lastAccountId = accountRepo.getAutoIncrId();

        Skill skill1 = new Skill(lastSkillId + 1L, "Java");
        Skill skill2 = new Skill(lastSkillId + 2L, "C++");
        account1 = new Account(lastAccountId + 1L, "cena@wwe.com", AccountStatus.ACTIVE);
        account2 = new Account(lastAccountId + 2L, "bigshow@wwe.com", AccountStatus.BANNED);

        skillSet = new HashSet<>();
        skillSet.add(skill1);
        skillSet.add(skill2);

        skillRepo.save(skill1);
        skillRepo.save(skill2);

        accountRepo.save(account1);
        accountRepo.save(account2);
    }

    @After
    public void deleteChanges() {
        deleteRepoChanges(devRepo, lastDeveloperId);
        deleteRepoChanges(skillRepo, lastSkillId);
        deleteRepoChanges(accountRepo, lastAccountId);
    }

    private void deleteRepoChanges(GenericRepository repository, Long lastId) {
        Long curId = lastId;

        do {
            ++curId;
        } while (repository.deleteById(curId));
    }

    @Test
    public void shouldSaveAndGetById() {
        Developer searchDeveloper = new Developer(lastDeveloperId + 1,
                "John", "Cena", skillSet, account1);

        devRepo.save(searchDeveloper);
        Developer resultDeveloper = devRepo.getById(searchDeveloper.getId());

        assertEquals(searchDeveloper, resultDeveloper);
    }

    @Test
    public void shouldNotGetByNullId() {
        Developer resultDeveloper = devRepo.getById(null);
        assertNull(resultDeveloper);
    }

    @Test
    public void shouldNotSaveNull() {
        assertFalse(devRepo.save(null));
    }

    @Test
    public void shouldUpdate() {
        Developer dev1 = new Developer(lastDeveloperId + 1,
                "John", "Cena", skillSet, account1);
        Developer dev2 = new Developer(lastDeveloperId + 2,
                "Big", "Show", skillSet, account2);
        Developer updatedDev2 = new Developer(lastDeveloperId + 2,
                "Little", "Oopsie", skillSet, account2);

        devRepo.save(dev1);
        devRepo.save(dev2);
        devRepo.update(updatedDev2);

        assertEquals(updatedDev2, devRepo.getById(lastDeveloperId + 2));
    }

    @Test
    public void shouldNotUpdateByNull() {
        assertFalse( devRepo.update(null));
    }

    @Test
    public void shouldGetAll() {
        Developer dev1 = new Developer(lastDeveloperId + 1,
                "John", "Cena", skillSet, account1);
        Developer dev2 = new Developer(lastDeveloperId + 2,
                "Big", "Show", skillSet, account2);
        ArrayList<Developer> expectedDevelopers = new ArrayList<>();

        expectedDevelopers.add(dev1);
        expectedDevelopers.add(dev2);
        devRepo.save(dev1);
        devRepo.save(dev2);

        ArrayList<Developer> resultAccounts = (ArrayList<Developer>) devRepo.getAll();

        // deleting untestable data from arraylist
        for (int i = 0; expectedDevelopers.size() != resultAccounts.size(); i++) {
            resultAccounts.remove(i);
        }

        assertArrayEquals(expectedDevelopers.toArray(), resultAccounts.toArray());
    }

    @Test
    public void shouldDeleteById() {
        Developer dev1 = new Developer(lastDeveloperId + 1,
                "John", "Cena", skillSet, account1);
        Developer dev2 = new Developer(lastDeveloperId + 2,
                "Big", "Show", skillSet, account2);

        devRepo.save(dev1);
        devRepo.save(dev2);
        devRepo.deleteById(lastDeveloperId + 1);

        assertNull(devRepo.getById(lastDeveloperId + 1));
    }

    @Test
    public void shouldNotDeleteById() {
        Developer dev1 = new Developer(lastDeveloperId + 1,
                "John", "Cena", skillSet, account1);

        devRepo.save(dev1);
        devRepo.deleteById(lastDeveloperId + 3);

        assertEquals(dev1, devRepo.getById(lastDeveloperId + 1));
    }
}