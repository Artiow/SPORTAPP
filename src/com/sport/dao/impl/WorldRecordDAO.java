package com.sport.dao.impl;

import com.sport.dao.generic.GenericDAOImpl;
import com.sport.model.Sport;
import com.sport.model.WorldRecord;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorldRecordDAO extends GenericDAOImpl<WorldRecord, Integer> {
    public WorldRecordDAO() {
        super(WorldRecord.class);
    }

    public int saveWorldRecord(Sport sport, Integer value, Date date) {
        WorldRecord record = new WorldRecord();
        record.setSport(sport);
        record.setValue(value);
        record.setDate(date);
        return save(record);
    }

    public void updateWorldRecord(WorldRecord record, Integer newValue, Date newDate) {
        record.setValue(newValue);
        record.setDate(newDate);
        update(record);
    }

    public void deleteWorldRecord(WorldRecord record) {
        delete(record);
    }

    public List<WorldRecord> getAllCompetitions() {
        return list();
    }

    public List<WorldRecord> getRecordFor(Sport sport) {
        Collection<WorldRecord> collection = sport.getWorldRecords();
        List<WorldRecord> list;

        if (collection instanceof List) list = (List<WorldRecord>) collection;
        else list = new ArrayList(collection);

        return list;
    }
}