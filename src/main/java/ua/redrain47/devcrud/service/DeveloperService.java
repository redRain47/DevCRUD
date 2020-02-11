package ua.redrain47.devcrud.service;

import lombok.extern.slf4j.Slf4j;
import ua.redrain47.devcrud.exceptions.DbConnectionIssueException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.repository.DeveloperRepository;
import ua.redrain47.devcrud.repository.JdbcDeveloperRepositoryImpl;
import ua.redrain47.devcrud.model.Developer;

import java.util.List;

@Slf4j
public class DeveloperService {
    private DeveloperRepository developerRepo;

    public DeveloperService() throws DbConnectionIssueException {
        try {
            this.developerRepo = new JdbcDeveloperRepositoryImpl();
            log.debug("Instance created");
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public DeveloperService(DeveloperRepository developerRepo) {
        this.developerRepo = developerRepo;
    }

    public Developer getDataById(Long id) throws DbConnectionIssueException {
        try {
            Developer developer = developerRepo.getById(id);
            log.debug("Got data by id");
            return developer;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<Developer> getAllData() throws DbConnectionIssueException {
        try {
            List<Developer> developerList = developerRepo.getAll();
            log.debug("Got all data");
            return developerList;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void addData(Developer addedDeveloper)
            throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        try {
            developerRepo.save(addedDeveloper);
            log.debug("Added data");
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void updateDataById(Developer updatedDeveloper)
            throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        try {
            developerRepo.update(updatedDeveloper);
            log.debug("Updated data by id");
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        try {
            developerRepo.deleteById(id);
            log.debug("Deleted data by id");
        } catch (DeletingReferencedRecordException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
