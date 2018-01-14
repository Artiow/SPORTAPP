package com.sport.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
public class Athlete {
    private int id;
    private Date birth;
    private String fullName;
    private String category;

    private Collection<Participate> participates;
    private Collection<TeamMembership> teamMemberships;

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
    @Column(name = "FullName")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "Birth")
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Basic
    @Column(name = "Category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Athlete athlete = (Athlete) o;

        if (id != athlete.id) return false;
        if (birth != null ? !birth.equals(athlete.birth) : athlete.birth != null) return false;
        if (fullName != null ? !fullName.equals(athlete.fullName) : athlete.fullName != null) return false;
        if (category != null ? !category.equals(athlete.category) : athlete.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @OneToMany(mappedBy = "athlete")
    public Collection<Participate> getParticipates() {
        return participates;
    }

    public void setParticipates(Collection<Participate> participatesById) {
        this.participates = participatesById;
    }

    @OneToMany(mappedBy = "athlete")
    public Collection<TeamMembership> getTeamMemberships() {
        return teamMemberships;
    }

    public void setTeamMemberships(Collection<TeamMembership> teamMembershipsById) {
        this.teamMemberships = teamMembershipsById;
    }
}