package com.sport.dao.impl;

import com.sport.config.Hibernate;
import com.sport.dao.ForeignKeyException;
import com.sport.dao.generic.GenericDAOImpl;
import com.sport.model.*;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import java.util.List;

public class SportDAO extends GenericDAOImpl<Sport, Integer> {
    public SportDAO() {
        super(Sport.class);
    }

    public int saveSport(String name, Unit unit, Boolean reverse) {
        Sport sport = new Sport();
        sport.setName(name);
        sport.setUnit(unit);
        sport.setReverse(reverse);

        return save(sport);
    }

    public void updateSport(Sport sport, String newName, Unit newUnit, Boolean newReverse) {
        sport.setName(newName);
        sport.setUnit(newUnit);
        sport.setReverse(newReverse);

        update(sport);
    }

    public void deleteSport(Sport sport) throws SportInUseException {
        if ((!sport.getCompetitions().isEmpty()) || (!sport.getWorldRecords().isEmpty())) throw new SportInUseException();
        delete(sport);
    }

    public List<Sport> getAllSports() {
        return list();
    }

    public List<Sport> getAllSportsFor(Athlete athlete) {
        Session session = Hibernate.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Sport> query = builder.createQuery(Sport.class);

        Root<Athlete> athleteRoot = query.from(Athlete.class);
        Join<Athlete, Participate> participateJoin = athleteRoot.join("participates", JoinType.INNER);
        Join<Participate, Competition> competitionJoin = participateJoin.join("competition", JoinType.INNER);
        Join<Competition, Sport> sportJoin = competitionJoin.join("sport", JoinType.INNER);
        sportJoin.on(builder.equal(athleteRoot, athlete));

        query.select(sportJoin).distinct(true);
        return session.createQuery(query).getResultList();
    }

    public class SportInUseException extends ForeignKeyException {
        private SportInUseException() {
            super();
        }

        private SportInUseException(String msg) {
            super(msg);
        }
    }
}