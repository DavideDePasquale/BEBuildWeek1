package org.example.DAO;

import org.example.entities.User;

import javax.persistence.EntityManager;

public class UserDAO {

    private EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }
    public void save(User user){
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }
    public User getFinById(long id){
        return em.find(User.class,id);
    }
    public void delete(User user){
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }
}
