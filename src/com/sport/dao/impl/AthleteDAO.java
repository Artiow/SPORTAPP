package com.sport.dao.impl;

import com.sport.config.Hibernate;
import com.sport.dao.ForeignKeyException;
import com.sport.dao.generic.GenericDAOImpl;
import com.sport.model.*;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AthleteDAO extends GenericDAOImpl<Athlete, Integer> {
    public AthleteDAO() {
        super(Athlete.class);
    }

    public int saveAthlete(String fullName, Date birthDate, String category) {
        Athlete athlete = new Athlete();
        athlete.setFullName(fullName);
        athlete.setCategory(category);
        athlete.setBirth(birthDate);
        return save(athlete);
    }

    public void updateAthlete(Athlete athlete, String newFullName, Date newBirthDate, String newCategory) {
        athlete.setFullName(newFullName);
        athlete.setCategory(newCategory);
        athlete.setBirth(newBirthDate);
        update(athlete);
    }

    public void deleteAthlete(Athlete athlete) throws AthleteInUseException {
        if ((!athlete.getParticipates().isEmpty()) || (!athlete.getTeamMemberships().isEmpty())) throw new AthleteInUseException();
        delete(athlete);
    }

    public List<Athlete> getAllAthletes() {
        return list();
    }

    public List<Athlete> getSuperAthletes(Sport sport) {
        Collection<WorldRecord> worldRecordCollection = sport.getWorldRecords();
        if ((worldRecordCollection == null) || (worldRecordCollection.isEmpty())) return new ArrayList<>();
        WorldRecord maxRecord = Collections.max(worldRecordCollection, new WorldRecord.ComparatorByValue());

        Session session = Hibernate.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Athlete> query = builder.createQuery(Athlete.class);

        Root<Sport> sportRoot = query.from(Sport.class);
        Join<Sport, Competition> competitionJoin = sportRoot.join("competitions", JoinType.INNER);
        Join<Competition, Participate> participateJoin = competitionJoin.join("participates", JoinType.INNER);
        Join<Participate, Athlete> athleteJoin = participateJoin.join("athlete", JoinType.INNER);

        Predicate compare;
        Boolean reverse = sport.getReverse();
        if (reverse)
            compare = builder.lessThan(participateJoin.get("result"), maxRecord.getValue());
        else
            compare = builder.greaterThan(participateJoin.get("result"), maxRecord.getValue());

        athleteJoin.on(builder.and(builder.equal(sportRoot, sport), compare));

        query.select(athleteJoin).distinct(true);
        return session.createQuery(query).getResultList();
    }

    public class AthleteInUseException extends ForeignKeyException {
        private AthleteInUseException() {
            super();
        }

        private AthleteInUseException(String msg) {
            super(msg);
        }
    }
}