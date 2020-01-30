package ua.redrain47.hw11.controller;

import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.service.DeveloperService;

import java.util.List;

public class DeveloperController {
    private DeveloperService devRepo = new DeveloperService();

    public DeveloperController() throws ConnectionIssueException {
    }

    public Developer getDataById(Long id) throws ConnectionIssueException {
        return devRepo.getDataById(id);
    }

    public List<Developer> getAllData() throws ConnectionIssueException {
        return devRepo.getAllData();
    }

    public boolean addData(Developer addedDeveloper)
            throws SuchEntityAlreadyExistsException, ConnectionIssueException {
        return devRepo.addData(addedDeveloper);
    }

    public boolean updateDataById(Developer updatedDeveloper)
            throws ConnectionIssueException, SuchEntityAlreadyExistsException {
        return devRepo.updateDataById(updatedDeveloper);
    }

    public boolean deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            ConnectionIssueException {
        return devRepo.deleteDataById(id);
    }
}
