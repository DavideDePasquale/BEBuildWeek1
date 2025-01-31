package org.example.DAO;

import org.example.entities.*;
import org.example.enumeration.VehicleType;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class TripDAO {
    private EntityManager em;

    public TripDAO(EntityManager em) {
        this.em = em;
    }
    public void save(Trip trip){
        em.getTransaction().begin();
        em.persist(trip);
        em.getTransaction().commit();
    }
    public Trip getFinById(long id){
        return em.find(Trip.class,id);
    }
    public void delete(Trip trip){
        em.getTransaction().begin();
        em.remove(trip);
        em.getTransaction().commit();
    }
    public void modifyVehicle(Trip trip, Vehicle vehicle){
        em.getTransaction().begin();
        trip.setVehicle(vehicle);
        em.merge(trip);
        em.getTransaction().commit();
    }
    public void modifyRoute(Trip trip, Route route){
        em.getTransaction().begin();
        trip.setRoute(route);
        em.merge(trip);
        em.getTransaction().commit();
    }
    public void modifyStartTime(Trip trip, LocalDateTime startTime){
        em.getTransaction().begin();
        trip.setStartTime(startTime);
        em.merge(trip);
        em.getTransaction().commit();
    }
    public void modifyEndTime(Trip trip, LocalDateTime endTime){
        em.getTransaction().begin();
        trip.setEndTime(endTime);
        em.merge(trip);
        em.getTransaction().commit();
    }

}
