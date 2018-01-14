package com.sport.dao.impl;

import com.sport.config.Hibernate;
import com.sport.dao.ForeignKeyException;
import com.sport.dao.generic.GenericDAOImpl;
import com.sport.model.Unit;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class UnitDAO extends GenericDAOImpl<Unit, Integer> {
    public UnitDAO() {
        super(Unit.class);
    }

    public int saveUnit(String name) {
        Unit unit = new Unit();
        unit.setName(name);
        return save(unit);
    }

    public void updateUnit(Unit unit, String newName) {
        unit.setName(newName);
        update(unit);
    }

    public void deleteUnit(Unit unit) throws UnitInUseException {
        if (!unit.getSports().isEmpty()) throw new UnitInUseException();
        delete(unit);
    }

    public List<Unit> getAllUnits() {
        return list();
    }

    public class UnitInUseException extends ForeignKeyException {
        private UnitInUseException() {
            super();
        }

        private UnitInUseException(String msg) {
            super(msg);
        }
    }
}