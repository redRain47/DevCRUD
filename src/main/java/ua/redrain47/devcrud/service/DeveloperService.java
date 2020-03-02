package ua.redrain47.devcrud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.redrain47.devcrud.config.RepositoryConfig;
import ua.redrain47.devcrud.exceptions.DbStorageException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.repository.DeveloperRepository;
import ua.redrain47.devcrud.model.Developer;

import java.util.List;

@Service
@Import(RepositoryConfig.class)
@Slf4j
public class DeveloperService {
    private DeveloperRepository developerRepo;

    @Autowired
    public DeveloperService(@Qualifier("developerRepository") DeveloperRepository developerRepo) {
        this.developerRepo = developerRepo;
    }

    public Developer getDataById(Long id) {
        Developer developer = developerRepo.getById(id);
        log.debug("Got data by id");
        return developer;
    }

    public List<Developer> getAllData() {
        List<Developer> developerList = developerRepo.getAll();
        log.debug("Got all data");
        return developerList;
    }

    public void addData(Developer addedDeveloper) {
        developerRepo.save(addedDeveloper);
        log.debug("Added data");
    }

    public void updateDataById(Developer updatedDeveloper) {
        developerRepo.update(updatedDeveloper);
        log.debug("Updated data by id");
    }

    public void deleteDataById(Long id) {
        developerRepo.deleteById(id);
        log.debug("Deleted data by id");
    }
}
