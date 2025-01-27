package org.example.entities;

import org.example.enumeration.DistributorType;

import javax.persistence.*;

@Entity
@Table(name = "distributors")
public class Distributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private DistributorType type;
    private String name;
    private String location;
    private boolean isActive;

    public Distributor() {
    }

    public Distributor(DistributorType type, String name, String location, boolean isActive) {
        this.type = type;
        this.name = name;
        this.location = location;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DistributorType getType() {
        return type;
    }

    public void setType(DistributorType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
