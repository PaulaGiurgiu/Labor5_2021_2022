package repository;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {
    T findOne(long id) throws SQLException;
    List<T> findAll() throws SQLException;
    T save(T entity) throws SQLException;
    void delete(T entity) throws SQLException;
    T update(T entity) throws SQLException;

}
