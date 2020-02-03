package ua.redrain47.hw11.controller;

import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.service.DeveloperService;

import java.util.List;

public class DeveloperController {
    private DeveloperService devRepo = new DeveloperService();

    public DeveloperController() throws DbConnectionIssueException {
    }

    public Developer getDataById(Long id) throws DbConnectionIssueException {
        return devRepo.getDataById(id);
    }

    public List<Developer> getAllData() throws DbConnectionIssueException {
        return devRepo.getAllData();
    }

    public boolean addData(Developer addedDeveloper)
            throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        return devRepo.addData(addedDeveloper);
    }

    public boolean updateDataById(Developer updatedDeveloper)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        return devRepo.updateDataById(updatedDeveloper);
    }

    public boolean deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        return devRepo.deleteDataById(id);
    }
}
