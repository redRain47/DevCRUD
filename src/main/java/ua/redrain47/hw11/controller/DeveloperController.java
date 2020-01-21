package ua.redrain47.hw11.controller;

import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.repository.DeveloperRepository;
import ua.redrain47.hw11.repository.io.IODeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {
    private DeveloperRepository devRepo;

    public DeveloperController() {
        this.devRepo = new IODeveloperRepositoryImpl();
    }

    public Developer getDataById(Long id) {
        return devRepo.getById(id);
    }

    public List<Developer> getAllData() {
        return devRepo.getAll();
    }

    public boolean addData(Developer addedDeveloper) {
        if (addedDeveloper != null) {
            devRepo.save(addedDeveloper);
            return true;
        }

        return false;
    }

    public boolean updateDataById(Developer updatedDeveloper) {
        return devRepo.update(updatedDeveloper);
    }

    public boolean deleteDataById(Long id) {
        return devRepo.deleteById(id);
    }

    public boolean isEmptyData() {
        return devRepo.isEmpty();
    }

    public Long getLastId() {
        return devRepo.getAutoIncrId();
    }
}
