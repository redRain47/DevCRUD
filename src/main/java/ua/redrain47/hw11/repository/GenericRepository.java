package ua.redrain47.hw11.repository;

import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;

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
