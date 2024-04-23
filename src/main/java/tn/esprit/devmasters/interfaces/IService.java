package tn.esprit.devmasters.interfaces;

import java.util.List;
import java.sql.SQLException;

public interface IService<T> {
    public void add(T t) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(T t) throws SQLException;
    public T findById(int id) throws SQLException;
    public List<T> findAll() throws SQLException;
}