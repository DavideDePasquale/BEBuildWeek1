package org.example.services;

import org.example.DAO.*;
import org.example.entities.Ticket;
import org.example.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class Reports {

    private static Logger log = LoggerFactory.getLogger(Services.class);

    private static EntityManager em;
    private static VehicleDAO vehicleDao;
    private static DistributorDAO distributorDao;
    private static RouteDAO routeDao;
    private static TicketDAO ticketDao;
    private static UserDAO userDao;
    private static TripDAO tripDao;

    public Reports() {
        vehicleDao = new VehicleDAO(em);
        distributorDao = new DistributorDAO(em);
        routeDao = new RouteDAO(em);
        ticketDao = new TicketDAO(em);
        userDao = new UserDAO(em);
        tripDao = new TripDAO(em);
    }
    public static void searchTicketsById(User user, EntityManager em){
    try {
        List<Ticket> tickets = em.createQuery("SELECT t FROM Ticket t WHERE t.user = :user", Ticket.class).setParameter("user",user).getResultList();
        tickets.forEach(System.out::println);
    } catch (Exception e) {
        throw new RuntimeException(e);
     }
    }

}
