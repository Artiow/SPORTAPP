package com.sport.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    PK save(T entity);

    void update(T entity);

    void delete(T entity);

    T get(PK id);

    List<T> list();
}