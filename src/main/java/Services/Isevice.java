package Services;

import Entities.Article;

import java.sql.SQLException;
import java.util.List;

public interface Isevice<T> {
    int Add(T t) ;
    int Edit(T t) ;
    int Delete(int id) ;
    List<T> getAll();
//  T getOne(int id) ;

}
