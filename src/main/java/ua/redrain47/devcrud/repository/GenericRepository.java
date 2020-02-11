package ua.redrain47.devcrud.repository;

import ua.redrain47.devcrud.exceptions.DbConnectionIssueException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;

import java.util.List;

public interface GenericRepository<T, ID> {
    void save(T entity) throws SuchEntityAlreadyExistsException,
            DbConnectionIssueException;

    T getById(ID id) throws DbConnectionIssueException;

    List<T> getAll() throws DbConnectionIssueException;

    void update(T entity) throws SuchEntityAlreadyExistsException,
            DbConnectionIssueException;

    void deleteById(ID id) throws DeletingReferencedRecordException,
            DbConnectionIssueException;
}
