package org.example.DAO;

import org.example.entities.Distributor;
import org.example.entities.User;
import org.example.enumeration.DistributorType;

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
    public void modifyType(Distributor distributor, DistributorType newType){
        em.getTransaction().begin();
        distributor.setType(newType);
        em.merge(distributor);
        em.getTransaction().commit();
    }
    public void modifyName(Distributor distributor, String newName){
        em.getTransaction().begin();
        distributor.setName(newName);
        em.merge(distributor);
        em.getTransaction().commit();
    }
    public void modifyLocation(Distributor distributor, String newLocation){
        em.getTransaction().begin();
        distributor.setLocation(newLocation);
        em.merge(distributor);
        em.getTransaction().commit();
    }
    public void modifyIsActive(Distributor distributor, Boolean newValue){
        em.getTransaction().begin();
        distributor.setActive(newValue);
        em.merge(distributor);
        em.getTransaction().commit();
    }

}
