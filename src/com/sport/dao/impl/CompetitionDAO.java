package com.sport.dao.impl;

import com.sport.dao.ForeignKeyException;
import com.sport.dao.generic.GenericDAOImpl;
import com.sport.model.*;

import java.sql.Date;
import java.util.List;

public class CompetitionDAO extends GenericDAOImpl<Competition, Integer> {
    public CompetitionDAO() {
        super(Competition.class);
    }

    public int saveCompetition(Sport sport, String name, String venue, Date date) {
        Competition competition = new Competition();
        competition.setSport(sport);
        competition.setName(name);
        competition.setVenue(venue);
        competition.setDate(date);
        return save(competition);
    }

    public void updateCompetition(Competition competition, Sport newSport, String newName, String newVenue, Date newDate) {
        competition.setSport(newSport);
        competition.setName(newName);
        competition.setVenue(newVenue);
        competition.setDate(newDate);
        update(competition);
    }

    public void deleteCompetition(Competition competition) throws CompetitionInUseException {
        if (!competition.getParticipates().isEmpty()) throw new CompetitionInUseException();
        delete(competition);
    }

    public List<Competition> getAllCompetitions() {
        return list();
    }

    public class CompetitionInUseException extends ForeignKeyException {
        private CompetitionInUseException() {
            super();
        }

        private CompetitionInUseException(String msg) {
            super(msg);
        }
    }
}
