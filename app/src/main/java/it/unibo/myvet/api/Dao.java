package it.unibo.myvet.api;

import java.util.List;

public interface Dao<T, ID> {
    T findById(ID id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(ID id);
}
