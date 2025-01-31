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
    public void modifyName(User user, String newName){
        em.getTransaction().begin();
        user.setName(newName);
        em.merge(user);
        em.getTransaction().commit();
    }
    public void modifySurname(User user, String newSurname){
        em.getTransaction().begin();
        user.setSurname(newSurname);
        em.merge(user);
        em.getTransaction().commit();
    }
    public void modifyPassword(User user, String newPassword){
        em.getTransaction().begin();
        user.setPassword(newPassword);
        em.merge(user);
        em.getTransaction().commit();
    }
    public void modifyCardNumber(User user, String newCardNumber){
        em.getTransaction().begin();
        user.setCardNumber(newCardNumber);
        em.merge(user);
        em.getTransaction().commit();
    }
    public void modifyRole(User user, boolean newRole){
        em.getTransaction().begin();
        user.setAdmin(newRole);
        em.merge(user);
        em.getTransaction().commit();
    }
}
