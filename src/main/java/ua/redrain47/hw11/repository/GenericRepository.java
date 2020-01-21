package ua.redrain47.hw11.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    boolean save(T entity);
    T getById(ID id);
    List<T> getAll();
    boolean update(T entity);
    boolean deleteById(ID id);
}
