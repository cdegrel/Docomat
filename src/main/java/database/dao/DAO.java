package database.dao;

import java.util.List;

public interface DAO<T> {

    void insert(T obj);

    void delete(Object key);

    void update(T obj);

    T read(Object key);

    List<T> readAll();

}
