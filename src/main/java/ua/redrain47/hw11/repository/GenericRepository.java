package ua.redrain47.hw11.repository;

import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;

import java.util.List;

public interface GenericRepository<T, ID> {
    boolean save(T entity) throws SuchEntityAlreadyExistsException,
            DbConnectionIssueException; // TODO: change return type, implement verification on null in the controller layer

    T getById(ID id) throws DbConnectionIssueException;

    List<T> getAll() throws DbConnectionIssueException;

    boolean update(T entity) throws SuchEntityAlreadyExistsException,
            DbConnectionIssueException;

    boolean deleteById(ID id) throws DeletingReferencedRecordException,
            DbConnectionIssueException;
}
