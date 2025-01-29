package org.example.DAO;

import org.example.entities.Distributor;
import org.example.entities.Trip;

import javax.persistence.EntityManager;

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
}
