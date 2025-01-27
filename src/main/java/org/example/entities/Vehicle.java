package org.example.entities;

import org.example.enumeration.VehicleStatus;
import org.example.enumeration.VehicleType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Route> routes = new ArrayList<>();

    public Vehicle() {
    }

    public Vehicle(VehicleType type, int capacity, VehicleStatus status) {
        this.type = type;
        this.capacity = capacity;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", type=" + type +
                ", capacity=" + capacity +
                ", status=" + status +
                '}';
    }
}
