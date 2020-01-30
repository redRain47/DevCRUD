package ua.redrain47.hw11.service;

import lombok.extern.slf4j.Slf4j;
import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.repository.DeveloperRepository;
import ua.redrain47.hw11.repository.jdbc.JdbcDeveloperRepositoryImpl;
import java.util.List;

@Slf4j
public class DeveloperService {
    private DeveloperRepository developerRepo;

    public DeveloperService() throws ConnectionIssueException {
        try {
            this.developerRepo = new JdbcDeveloperRepositoryImpl();
            log.debug("DeveloperService -> Instance created");
        } catch (ConnectionIssueException e) {
            log.error("DeveloperService -> " + e);
            throw e;
        }
    }

    public Developer getDataById(Long id) throws ConnectionIssueException {
        try {
            Developer developer = developerRepo.getById(id);
            log.debug("DeveloperService -> Got data by id");
            return developer;
        } catch (ConnectionIssueException e) {
            log.error("DeveloperService -> " + e);
            throw e;
        }
    }

    public List<Developer> getAllData() throws ConnectionIssueException {
        try {
            List<Developer> developerList = developerRepo.getAll();
            log.debug("DeveloperService -> Got all data");
            return developerList;
        } catch (ConnectionIssueException e) {
            log.error("DeveloperService -> " + e);
            throw e;
        }
    }

    public boolean addData(Developer addedDeveloper)
            throws SuchEntityAlreadyExistsException, ConnectionIssueException {
        try {
            boolean result = developerRepo.save(addedDeveloper);
            log.debug("DeveloperService -> Added data");
            return result;
        } catch (SuchEntityAlreadyExistsException
                | ConnectionIssueException e) {
            log.error("DeveloperService -> " + e);
            throw e;
        }
    }

    public boolean updateDataById(Developer updatedDeveloper)
            throws SuchEntityAlreadyExistsException, ConnectionIssueException {
        try {
            boolean result = developerRepo.update(updatedDeveloper);
            log.debug("DeveloperService -> Updated data by id");
            return result;
        } catch (SuchEntityAlreadyExistsException
                | ConnectionIssueException e) {
            log.error("DeveloperService -> " + e);
            throw e;
        }
    }

    public boolean deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            ConnectionIssueException {
        try {
            developerRepo.deleteById(id);
            log.debug("DeveloperService -> Deleted data by id");
            return true;
        } catch (DeletingReferencedRecordException
                | ConnectionIssueException e) {
            log.error("DeveloperService -> " + e);
            throw e;
        }
    }
}
