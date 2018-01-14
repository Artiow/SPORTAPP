package com.sport.model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.beans.Transient;
import java.util.Collection;

@Entity
public class Sport {
    private int id;
    private String name;

    private Unit unit;
    private Boolean reverse;

    private Collection<Competition> competitions;
    private Collection<WorldRecord> worldRecords;

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

        Sport sport = (Sport) o;

        if (id != sport.id) return false;
        if (name != null ? !name.equals(sport.name) : sport.name != null) return false;
        if (unit != null ? !unit.equals(sport.unit) : sport.unit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(name)
                .append(" (")
                .append(unit.toString())
                .append(")")
                .toString();
    }

    @ManyToOne
    @JoinColumn(name = "Unit_ID", referencedColumnName = "ID", nullable = false)
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unitByUnitId) {
        this.unit = unitByUnitId;
    }

    @Basic
    @Column(name = "Unit_Reverse")
    public Boolean getReverse() {
        return reverse;
    }

    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }

    @OneToMany(mappedBy = "sport")
    public Collection<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(Collection<Competition> competitionsById) {
        this.competitions = competitionsById;
    }

    @OneToMany(mappedBy = "sport")
    public Collection<WorldRecord> getWorldRecords() {
        return worldRecords;
    }

    public void setWorldRecords(Collection<WorldRecord> worldRecordsById) {
        this.worldRecords = worldRecordsById;
    }

    @Transient
    public SimpleStringProperty unitProperty() {
        return new SimpleStringProperty(this.unit.getName());
    }
}
