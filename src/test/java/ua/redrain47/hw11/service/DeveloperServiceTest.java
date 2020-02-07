package ua.redrain47.hw11.service;

import lombok.SneakyThrows;
import org.junit.Test;
import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.repository.DeveloperRepository;

import static org.mockito.Mockito.*;

public class DeveloperServiceTest {
    private static DeveloperRepository developerRepo = mock(DeveloperRepository.class);
    private DeveloperService developerService = new DeveloperService(developerRepo);
    private Developer testDeveloper = new Developer(null, null,
            null, null, null);

    @SneakyThrows
    @Test
    public void shouldInvokeSave() {
        developerService.addData(testDeveloper);

        verify(developerRepo, times(1)).save(testDeveloper);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeGetById() {
        developerService.getDataById(1L);
        verify(developerRepo, times(1)).getById(1L);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeGetAll() {
        developerService.getAllData();
        verify(developerRepo, times(1)).getAll();
    }

    @SneakyThrows
    @Test
    public void shouldInvokeUpdate() {
        developerService.updateDataById(testDeveloper);
        verify(developerRepo, times(1)).update(testDeveloper);
    }

    @SneakyThrows
    @Test
    public void shouldInvokeDelete() {
        developerService.deleteDataById(1L);
        verify(developerRepo, times(1)).deleteById(1L);
    }
}