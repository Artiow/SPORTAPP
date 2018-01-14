package com.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Unit {
    private int id;
    private String name;

    private Collection<Sport> sports;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        if (id != unit.id) return false;
        if (name != null ? !name.equals(unit.name) : unit.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @OneToMany(mappedBy = "unit")
    public Collection<Sport> getSports() {
        return sports;
    }

    public void setSports(Collection<Sport> sportsById) {
        this.sports = sportsById;
    }
}
