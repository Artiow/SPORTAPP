package com.sport.model;

import javax.persistence.*;

@Entity
public class TeamMembership {
    @EmbeddedId
    private TeamMembershipPK pk;

    @ManyToOne
    @MapsId("athleteId")
    private Athlete athlete;
    @ManyToOne
    @MapsId("sportTeamId")
    private SportTeam sport;

    public TeamMembershipPK getPK() {
        return pk;
    }

    public void setPK(TeamMembershipPK pk) {
        this.pk = pk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamMembership that = (TeamMembership) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = athlete.getId();
        result = 31 * result + sport.getId();
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Athlete_ID", referencedColumnName = "ID", nullable = false)
    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athleteByAthleteId) {
        this.athlete = athleteByAthleteId;
    }

    @ManyToOne
    @JoinColumn(name = "SportTeam_ID", referencedColumnName = "ID", nullable = false)
    public SportTeam getSport() {
        return sport;
    }

    public void setSport(SportTeam sportTeamBySportTeamId) {
        this.sport = sportTeamBySportTeamId;
    }
}
