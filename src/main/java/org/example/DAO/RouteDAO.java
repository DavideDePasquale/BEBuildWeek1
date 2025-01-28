package org.example.DAO;

import org.example.entities.Route;

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
}
