package com.sport.model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.beans.Transient;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Competition {
    private int id;
    private String name;
    private String venue;
    private Date date;

    private Sport sport;

    private Collection<Participate> participates;

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
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "Venue")
    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Competition that = (Competition) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (venue != null ? !venue.equals(that.venue) : that.venue != null) return false;
        if (sport != null ? !sport.equals(that.sport) : that.sport != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (venue != null ? venue.hashCode() : 0);
        result = 31 * result + (sport != null ? sport.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @ManyToOne
    @JoinColumn(name = "Sport_ID", referencedColumnName = "ID", nullable = false)
    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sportBySportId) {
        this.sport = sportBySportId;
    }

    @OneToMany(mappedBy = "competition")
    public Collection<Participate> getParticipates() {
        return participates;
    }

    public void setParticipates(Collection<Participate> participatesById) {
        this.participates = participatesById;
    }

    @Transient
    public SimpleStringProperty sportProperty() {
        return new SimpleStringProperty(this.sport.getName());
    }
}