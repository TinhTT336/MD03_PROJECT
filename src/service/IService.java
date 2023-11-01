package service;

import java.util.List;

public interface IService<T, ID> {
    List<T> findAll();

    Long getNewId();

    boolean save(T t);

    T findById(ID id);

    boolean deleteById(ID id);

}
