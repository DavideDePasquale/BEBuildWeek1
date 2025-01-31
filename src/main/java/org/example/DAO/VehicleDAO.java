package org.example.DAO;

import org.example.entities.Route;
import org.example.entities.Vehicle;
import org.example.enumeration.VehicleStatus;
import org.example.enumeration.VehicleType;

import javax.persistence.EntityManager;

public class VehicleDAO {
    private EntityManager em;

    public VehicleDAO(EntityManager em) {
        this.em = em;
    }
    public void save(Vehicle vehicle){
        em.getTransaction().begin();
        em.persist(vehicle);
        em.getTransaction().commit();
    }
    public Vehicle getFinById(long id){
        return em.find(Vehicle.class,id);
    }
    public void delete(Vehicle vehicle){
        em.getTransaction().begin();
        em.remove(vehicle);
        em.getTransaction().commit();
    }
    public void modifyTypesVehicle(Vehicle vehicle, VehicleType type){
        em.getTransaction().begin();
        vehicle.setType(type);
        em.merge(vehicle);
        em.getTransaction().commit();
    }
    public void modifyCapacity(Vehicle vehicle, int capacity){
        em.getTransaction().begin();
        vehicle.setCapacity(capacity);
        em.merge(vehicle);
        em.getTransaction().commit();
    }
    public void modifyStatusVehicle(Vehicle vehicle, VehicleStatus status){
        em.getTransaction().begin();
        vehicle.setStatus(status);
        em.merge(vehicle);
        em.getTransaction().commit();
    }
}
