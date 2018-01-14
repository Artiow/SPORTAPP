package com.sport.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Comparator;

@Entity
public class WorldRecord {
    private int id;
    private int value;
    private Date date;

    private Sport sport;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Value")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Basic
    @Column(name = "Date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorldRecord that = (WorldRecord) o;

        if (id != that.id) return false;
        if (value != that.value) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (sport != null ? !sport.equals(that.sport) : that.sport != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + value;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (sport != null ? sport.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Sport_ID", referencedColumnName = "ID", nullable = false)
    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sportBySportId) {
        this.sport = sportBySportId;
    }

    public static class ComparatorByValue implements Comparator<WorldRecord> {
        boolean reverse;

        public ComparatorByValue() {
            this.reverse = false;
        }

        public ComparatorByValue(boolean reverse) {
            this.reverse = reverse;
        }

        @Override
        public int compare(WorldRecord o1, WorldRecord o2) {
            Boolean reverse = o1.getSport().getReverse();
            if (reverse != o2.getSport().getReverse()) return 0;

            return (reverse == this.reverse) ? o1.value - o2.value : o2.value - o1.value;
        }
    }
}