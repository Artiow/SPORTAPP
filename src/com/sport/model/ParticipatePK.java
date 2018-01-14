package com.sport.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class ParticipatePK implements Serializable {
    private Integer athleteId;
    private Integer competitionId;

    public ParticipatePK() {
    }

    public ParticipatePK(Competition competition, Athlete athlete) {
        setCompetitionId(competition.getId());
        setAthleteId(athlete.getId());
    }

    @Id
    @Column(name = "Athlete_ID")
    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    @Id
    @Column(name = "Competition_ID")
    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipatePK that = (ParticipatePK) o;

        if (athleteId != that.athleteId) return false;
        if (competitionId != that.competitionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = athleteId;
        result = 31 * result + competitionId;
        return result;
    }
}
