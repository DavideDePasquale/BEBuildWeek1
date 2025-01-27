package org.example.DAO;

import org.example.entities.User;
import org.example.entities.Vehicle;

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
}
