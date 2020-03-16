package ua.redrain47.devcrud.service;

import lombok.SneakyThrows;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.redrain47.devcrud.model.Developer;
import ua.redrain47.devcrud.repository.DeveloperRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DeveloperServiceTest {
    @Mock
    private DeveloperRepository developerRepo;
    @InjectMocks
    private DeveloperService developerService;
    private Developer testDeveloper = new Developer(null, null,
            null, null, null);

    public DeveloperServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

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