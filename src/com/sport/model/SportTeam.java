package com.sport.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
public class SportTeam {
    private int id;
    private String name;
    private String city;
    private Date foundation;

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
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Foundation")
    public Date getFoundation() {
        return foundation;
    }

    public void setFoundation(Date foundation) {
        this.foundation = foundation;
    }

    @Basic
    @Column(name = "City")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SportTeam sportTeam = (SportTeam) o;

        if (id != sportTeam.id) return false;
        if (name != null ? !name.equals(sportTeam.name) : sportTeam.name != null) return false;
        if (city != null ? !city.equals(sportTeam.city) : sportTeam.city != null) return false;
        if (foundation != null ? !foundation.equals(sportTeam.foundation) : sportTeam.foundation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (foundation != null ? foundation.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "sport")
    public Collection<TeamMembership> getTeamMemberships() {
        return teamMemberships;
    }

    public void setTeamMemberships(Collection<TeamMembership> teamMembershipsById) {
        this.teamMemberships = teamMembershipsById;
    }
}
