package ua.redrain47.hw11.repository;

import ua.redrain47.hw11.model.Developer;

public interface DeveloperRepository extends GenericRepository<Developer, Long> {
    boolean isEmpty();
    default Long getAutoIncrId() {
        return null;
    }
}
