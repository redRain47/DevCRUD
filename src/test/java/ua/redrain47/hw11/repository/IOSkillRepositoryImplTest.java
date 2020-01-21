package ua.redrain47.hw11.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.repository.io.IOSkillRepositoryImpl;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IOSkillRepositoryImplTest {
    private IOSkillRepositoryImpl skillRepo = new IOSkillRepositoryImpl();
    private Long lastId;

    @Before
    public void getLastId() {
        lastId = skillRepo.getAutoIncrId();
    }

    @After
    public void deleteChanges() {
        Long curId = lastId;

        do {
            ++curId;
        } while (skillRepo.deleteById(curId));
    }

    @Test
    public void shouldSaveAndGetById() {
        Skill searchSkill = new Skill(lastId + 1, "Java");

        skillRepo.save(searchSkill);
        Skill resultSkill = skillRepo.getById(searchSkill.getId());

        assertEquals(searchSkill, resultSkill);
    }

    @Test
    public void shouldNotGetByNullId() {
        Skill resultSkill = skillRepo.getById(null);
        assertNull(resultSkill);
    }

    @Test
    public void shouldNotSaveNull() {
        assertFalse(skillRepo.save(null));
    }

    @Test
    public void shouldUpdate() {
        Skill skill1 = new Skill(lastId + 1, "Java");
        Skill skill2 = new Skill(lastId + 2, "C++");
        Skill updatedSkill2 = new Skill(lastId + 2, "Python");

        skillRepo.save(skill1);
        skillRepo.save(skill2);
        skillRepo.update(updatedSkill2);

        assertEquals(updatedSkill2, skillRepo.getById(lastId + 2));
    }

    @Test
    public void shouldNotUpdateByNull() {
        assertFalse( skillRepo.update(null));
    }

    @Test
    public void shouldGetAll() {
        Skill skill1 = new Skill(lastId + 1, "Java");
        Skill skill2 = new Skill(lastId + 2, "C++");
        ArrayList<Skill> expectedSkills = new ArrayList<>();

        expectedSkills.add(skill1);
        expectedSkills.add(skill2);
        skillRepo.save(skill1);
        skillRepo.save(skill2);

        ArrayList<Skill> resultSkills = (ArrayList<Skill>) skillRepo.getAll();

        // deleting untestable data from arraylist
        for (int i = 0; expectedSkills.size() != resultSkills.size(); i++) {
            resultSkills.remove(i);
        }

        assertArrayEquals(expectedSkills.toArray(), resultSkills.toArray());
    }

    @Test
    public void shouldDeleteById() {
        Skill skill1 = new Skill(lastId + 1, "Java");
        Skill skill2 = new Skill(lastId + 2, "C++");

        skillRepo.save(skill1);
        skillRepo.save(skill2);
        skillRepo.deleteById(lastId + 1);

        assertNull(skillRepo.getById(lastId + 1));
    }

    @Test
    public void shouldNotDeleteById() {
        Skill skill1 = new Skill(lastId + 1, "Java");

        skillRepo.save(skill1);
        skillRepo.deleteById(lastId + 3);

        assertEquals(skill1, skillRepo.getById(lastId + 1));
    }
}