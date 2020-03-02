package ua.redrain47.devcrud.repository;

import ua.redrain47.devcrud.exceptions.DbStorageException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;

import java.util.List;

public interface GenericRepository<T, ID> {
    void save(T entity) throws SuchEntityAlreadyExistsException,
            DbStorageException;

    T getById(ID id) throws DbStorageException;

    List<T> getAll() throws DbStorageException;

    void update(T entity) throws SuchEntityAlreadyExistsException,
            DbStorageException;

    void deleteById(ID id) throws DeletingReferencedRecordException,
            DbStorageException;
}
