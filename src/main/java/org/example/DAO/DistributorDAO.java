package org.example.DAO;

import org.example.entities.Distributor;

import javax.persistence.EntityManager;

public class DistributorDAO {
    private EntityManager em;

    public DistributorDAO(EntityManager em) {
        this.em = em;
    }
    public void save(Distributor distributor){
        em.getTransaction().begin();
        em.persist(distributor);
        em.getTransaction().commit();
    }
    public Distributor getFinById(long id){
        return em.find(Distributor.class,id);
    }
    public void delete(Distributor distributor){
        em.getTransaction().begin();
        em.remove(distributor);
        em.getTransaction().commit();
    }
}
