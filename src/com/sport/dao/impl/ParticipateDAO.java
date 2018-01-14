package com.sport.dao.impl;

import com.sport.config.Hibernate;
import com.sport.dao.generic.GenericDAOImpl;
import com.sport.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ParticipateDAO extends GenericDAOImpl<Participate, ParticipatePK> {
    public ParticipateDAO() {
        super(Participate.class);
    }

    @Transactional
    public ParticipatePK saveParticipate(Competition competition, Athlete athlete, int result) {
        ParticipatePK pk = new ParticipatePK(competition, athlete);
        Participate participate = Optional.ofNullable(this.get(pk)).orElse(new Participate());

        participate.setCompetition(competition);
        participate.setAthlete(athlete);
        participate.setResult(result);

        Transaction tx = null;
        try (Session session = Hibernate.getSession()) {
            tx = session.beginTransaction();
            pk = (ParticipatePK) session.save(participate);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }

        return pk;
    }

    public void updateParticipate(Participate participate, int result) {
        participate.setResult(result);
        update(participate);
    }

    public void deleteParticipate(Participate participate) {
        delete(participate);
    }

    public List<Participate> getAllParticipatesFor(Athlete athlete, Sport sport) {
        Session session = Hibernate.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Participate> query = builder.createQuery(Participate.class);

        Root<Sport> sportRoot = query.from(Sport.class);
        Join<Sport, Competition> competitionJoin = sportRoot.join("competitions", JoinType.INNER);
        Join<Competition, Participate> participateJoin = competitionJoin.join("participates", JoinType.INNER);
        participateJoin.on(builder.and(builder.equal(sportRoot, sport), builder.equal(participateJoin.get("athlete"), athlete)));

        query.select(participateJoin).distinct(true);
        return session.createQuery(query).getResultList();
    }

    public List<Participate> getParticipateFor(Competition competition) {
        Collection<Participate> collection = competition.getParticipates();
        List<Participate> list;

        if (collection instanceof List) list = (List<Participate>) collection;
        else list = new ArrayList(collection);

        return list;
    }

    public List<Participate> getParticipateFor(Athlete athlete) {
        Collection<Participate> collection = athlete.getParticipates();
        List<Participate> list;

        if (collection instanceof List) list = (List<Participate>) collection;
        else list = new ArrayList(collection);

        return list;
    }
}
