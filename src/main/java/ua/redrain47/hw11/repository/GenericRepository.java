package ua.redrain47.hw11.repository;

import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;

import java.util.List;

public interface GenericRepository<T, ID> {
    boolean save(T entity) throws SuchEntityAlreadyExistsException,
            ConnectionIssueException; // TODO: change return type, implement verification on null in the controller layer

    T getById(ID id) throws ConnectionIssueException;

    List<T> getAll() throws ConnectionIssueException;

    boolean update(T entity) throws SuchEntityAlreadyExistsException,
            ConnectionIssueException;

    boolean deleteById(ID id) throws DeletingReferencedRecordException,
            ConnectionIssueException;
}
