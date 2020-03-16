package ua.redrain47.devcrud.service;

import java.util.List;

public interface Serviceable<T, ID> {
    T getDataById(ID id);

    List<T> getAllData();

    void addData(T addedEntity);

    void updateDataById(T updatedEntity);

    void deleteDataById(ID id);
}
