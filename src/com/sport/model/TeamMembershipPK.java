package com.sport.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class TeamMembershipPK implements Serializable {
    private int athleteId;
    private int sportTeamId;

    @Id
    @Column(name = "Athlete_ID")
    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    @Id
    @Column(name = "SportTeam_ID")
    public int getSportTeamId() {
        return sportTeamId;
    }

    public void setSportTeamId(int sportTeamId) {
        this.sportTeamId = sportTeamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamMembershipPK that = (TeamMembershipPK) o;

        if (athleteId != that.athleteId) return false;
        if (sportTeamId != that.sportTeamId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = athleteId;
        result = 31 * result + sportTeamId;
        return result;
    }
}
