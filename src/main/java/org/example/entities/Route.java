package org.example.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String startPoint;
    private String endPoint;
    private int estimatedDuration;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @OneToMany(mappedBy = "route",cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Route() {
    }

    public Route(String startPoint, String endPoint, int estimatedDuration) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.estimatedDuration = estimatedDuration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", estimatedDuration=" + estimatedDuration +
                ", vehicle=" + vehicle +
                '}';
    }
}
