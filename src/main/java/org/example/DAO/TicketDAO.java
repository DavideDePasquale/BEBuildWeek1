package org.example.DAO;

import org.example.entities.Ticket;
import org.example.entities.User;

import javax.persistence.EntityManager;

public class TicketDAO {
    private EntityManager em;

    public TicketDAO(EntityManager em) {
        this.em = em;
    }
    public void save(Ticket ticket){
        em.getTransaction().begin();
        em.persist(ticket);
        em.getTransaction().commit();
    }
    public Ticket getFinById(long id){
        return em.find(Ticket.class,id);
    }
    public void delete(Ticket ticket){
        em.getTransaction().begin();
        em.remove(ticket);
        em.getTransaction().commit();
    }
}
