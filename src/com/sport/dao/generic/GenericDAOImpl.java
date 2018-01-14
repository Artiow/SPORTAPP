package com.sport.dao.generic;

import com.sport.config.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.io.Serializable;
import java.util.List;

public class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T, PK> {
    private Class<T> aClass;

    public GenericDAOImpl(Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public PK save(T entity) {
        Session session = Hibernate.getSession();
        PK id = (PK) session.save(entity);
        session.close();
        return id;
    }

    @Override
    public void update(T entity) {
        Session session = Hibernate.getSession();
        Transaction tx = session.beginTransaction();
        session.update(entity);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(T entity) {
        Session session = Hibernate.getSession();
        Transaction tx = session.beginTransaction();
        session.delete(entity);
        tx.commit();
        session.close();
    }

    @Override
    public T get(PK id) {
        Session session = Hibernate.getSession();
        T object = session.get(aClass, id);
        session.close();
        return object;
    }

    @Override
    public List<T> list() {
        Session session = Hibernate.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(aClass);

        Root<T> root = criteriaQuery.from(aClass);
        criteriaQuery.select(root);

        List<T> objects = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return objects;
    }
}