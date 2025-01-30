package org.example.services;

import org.example.DAO.*;
import org.example.entities.*;
import org.example.enumeration.VehicleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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
    public static void searchTicketByTripId(Trip trip, EntityManager em){
        try {
            List<Ticket> tickets = em.createQuery("SELECT t FROM Ticket t WHERE t.trip = :trip", Ticket.class).setParameter("trip",trip).getResultList();
            int numberOfSoledTickets = tickets.size();
            System.out.println("TOTAL NUMBER OF SOLED TICKETS : " + numberOfSoledTickets);
            int availableTickets = trip.getVehicle().getCapacity() - numberOfSoledTickets;
            System.out.println("AVAILABLE TICKETS : " + availableTickets);
            tickets.forEach(System.out::println);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void searchTicketByDate(String dateString, EntityManager em) {
        try {
            List<Ticket> tickets = em.createQuery(
                            "SELECT t FROM Ticket t WHERE TO_CHAR(t.issueDate, 'YYYY-MM') LIKE :issueDate", Ticket.class)
                    .setParameter("issueDate", dateString + "%")
                    .getResultList();
          int numberOfTickets =tickets.size();
            System.out.println("TOTAL NUMBER OF TICKETS : " + numberOfTickets);
            tickets.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void searchTicketByDistributor(Distributor distributor,EntityManager em){
        try {
            List<Ticket> tickets = em.createQuery("SELECT t FROM Ticket t WHERE t.distributor = :distributor", Ticket.class).setParameter("distributor",distributor).getResultList();
            int numberOfSoledTickets = tickets.size();
            System.out.println("TOTAL NUMBER OF SOLED TICKETS : " + numberOfSoledTickets);
            tickets.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
   public static void vehiclesInMaintenance(){
        try {
            List<Vehicle> vehiclesInMaintenance = em.createQuery("SELECT v FROM Vehicle v WHERE v.status = :MAINTENANCE", Vehicle.class).setParameter("MAINTENANCE", VehicleStatus.MAINTENANCE).getResultList();
            vehiclesInMaintenance.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

   }
}
