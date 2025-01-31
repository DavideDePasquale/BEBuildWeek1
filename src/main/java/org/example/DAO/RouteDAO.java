package org.example.DAO;

import org.example.entities.Route;
import org.example.entities.Trip;
import org.example.entities.Vehicle;

import javax.persistence.EntityManager;

public class RouteDAO {
    private EntityManager em;

    public RouteDAO(EntityManager em) {
        this.em = em;
    }
    public void save(Route route){
        em.getTransaction().begin();
        em.persist(route);
        em.getTransaction().commit();
    }
    public Route getFinById(long id){
        try {
            return em.find(Route.class,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void delete(Route route){
        em.getTransaction().begin();
        em.remove(route);
        em.getTransaction().commit();
    }
    public void modifyVehicle(Route route, Vehicle vehicle){
        em.getTransaction().begin();
        route.setVehicle(vehicle);
        em.merge(route);
        em.getTransaction().commit();
    }
    public void modifyStartPoint(Route route, String startPoint){
        em.getTransaction().begin();
        route.setStartPoint(startPoint);
        em.merge(route);
        em.getTransaction().commit();
    }
    public void modifyEndPoint(Route route, String endPoint){
        em.getTransaction().begin();
        route.setEndPoint(endPoint);
        em.merge(route);
        em.getTransaction().commit();
    }
    public void modifyEstimatedDuration(Route route, int estimatedDuration){
        em.getTransaction().begin();
        route.setEstimatedDuration(estimatedDuration);
        em.merge(route);
        em.getTransaction().commit();
    }
}
