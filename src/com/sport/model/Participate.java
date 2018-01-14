package com.sport.model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.beans.Transient;
import java.util.Comparator;

@Entity
public class Participate {
    @EmbeddedId
    private ParticipatePK pk;
    private Integer result;

    @ManyToOne
    @MapsId("athleteId")
    private Athlete athlete;
    @ManyToOne
    @MapsId("competitionId")
    private Competition competition;

    public Participate(){
        this.pk = new ParticipatePK();
    }

    public Participate(ParticipatePK pk) {
        this.pk = pk;
    }

    public ParticipatePK getPK() {
        return pk;
    }

    public void setPK(ParticipatePK pk) {
        this.pk = pk;
    }

    @Basic
    @Column(name = "Result")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participate that = (Participate) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;
        if (result != that.result) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = pk.hashCode();
        result1 = 31 * result1 + result;
        return result1;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(result.toString())
                .append(" (")
                .append(competition.getSport().getUnit().toString())
                .append(")")
                .toString();
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
    @JoinColumn(name = "Competition_ID", referencedColumnName = "ID", nullable = false)
    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competitionByCompetitionId) {
        this.competition = competitionByCompetitionId;
    }

    @Transient
    public SimpleStringProperty athleteProperty() {
        return new SimpleStringProperty(this.athlete.getFullName());
    }

    public static class ComparatorByResult implements Comparator<Participate> {
        boolean reverse;

        public ComparatorByResult() {
            this.reverse = false;
        }

        public ComparatorByResult(boolean reverse) {
            this.reverse = reverse;
        }

        @Override
        public int compare(Participate o1, Participate o2) {
            Boolean reverse = o1.getCompetition().getSport().getReverse();
            if (reverse != o2.getCompetition().getSport().getReverse()) return 0;

            return (reverse == this.reverse) ? o1.result - o2.result : o2.result - o1.result;
        }
    }
}
