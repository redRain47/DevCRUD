package ua.redrain47.devcrud.service;

import lombok.SneakyThrows;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.repository.SkillRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SkillServiceTest {
    @Mock
    private SkillRepository skillRepo;
    @InjectMocks
    private SkillService skillService;
    private Skill testSkill = new Skill(null, null);

    public SkillServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeSave() {
        skillService.addData(testSkill);
        verify(skillRepo, times(1)).save(testSkill);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeGetById() {
        skillService.getDataById(1L);
        verify(skillRepo, times(1)).getById(1L);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeGetAll() {
        skillService.getAllData();
        verify(skillRepo, times(1)).getAll();
    }

    @SneakyThrows
    @Test
    public void shouldInvokeUpdate() {
        skillService.updateDataById(testSkill);
        verify(skillRepo, times(1)).update(testSkill);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeDelete() {
        skillService.deleteDataById(1L);
        verify(skillRepo, times(1)).deleteById(1L);
    }
}