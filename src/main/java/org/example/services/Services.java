package org.example.services;

import org.example.DAO.*;
import org.example.Main;
import org.example.entities.*;
import org.example.enumeration.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Services {

    // 1) Definiamo logger
   private static Logger log = LoggerFactory.getLogger(Main.class);
    // 2) Importare classi dentro services
    private EntityManager em;
    private VehicleDAO vehicleDao;
    private DistributorDAO distributorDao;
    private RouteDAO routeDao;
    private TicketDAO ticketDao;
    private UserDAO userDao;

    public Services() {
    }

    public Services(EntityManager em) {
        this.em = em;
        this.vehicleDao = new VehicleDAO(em);
        this.distributorDao = new DistributorDAO(em);
        this.routeDao = new RouteDAO(em);
        this.ticketDao = new TicketDAO(em);
        this.userDao = new UserDAO(em);
    }
    public void addVehicle(Vehicle vehicle){
        try {
            vehicleDao.save(vehicle);
            log.info("Hai aggiunto un veicolo!");
        } catch (Exception e) {
            log.error("Non hai aggiunto il veicolo!", e);
        }
    }
    public void addDistributor(Distributor distributor){
        try {
            distributorDao.save(distributor);
            log.info("Hai aggiunto un nuovo distrubutore");

        } catch (Exception e) {
            log.error("Non è stato possibile aggiungere il distributore. Controlla l'errore -> ", e);
        }
    }
    public void addUser(User user){
        try {
            userDao.save(user);
            log.info("Hai aggiunto un nuovo user");

        } catch (Exception e) {
            log.error("Non è stato possibile aggiungere l'user . Controlla l'errore -> ", e);
        }
    }
    public void addRoute(Route route){
        try {
            routeDao.save(route);
            log.info("Hai aggiunto una nuova rotta");

        } catch (Exception e) {
            log.error("Non è stato possibile aggiungere la rotta. Controlla l'errore -> ", e);
        }
    }
    public void addTicket(Ticket ticket){
        try {
            ticketDao.save(ticket);
            log.info("Hai aggiunto un nuovo ticket");

        } catch (Exception e) {
            log.error("Non è stato possibile aggiungere il ticket. Controlla l'errore -> ", e);
        }
    }
    public void displayVehicles(){
        try {
            List<Vehicle> vehicles = em.createQuery("FROM Vehicle", Vehicle.class).getResultList();
            vehicles.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare i veicoli", e);
        }
    }
    public void displayDistributors(){
        try {
            List<Distributor> distributors = em.createQuery("FROM Distributor", Distributor.class).getResultList();
            distributors.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare i distributori", e);
        }
    }
    public void displayUsers(){
        try {
            List<User> users = em.createQuery("FROM User", User.class).getResultList();
            users.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare gli users", e);
        }
    }
    public void displayRoutes(){
        try {
            List<Route> routes = em.createQuery("FROM Route", Route.class).getResultList();
            routes.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare le tratte", e);
        }
    }
    public void displayTickets(){
        try {
            List<Ticket> tickets = em.createQuery("FROM Ticket", Ticket.class).getResultList();
            tickets.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare i tickets", e);
        }
    }
    public void buyTicket(long userid, Subscription subscription, long distributor_id){
        String code = codeGenerator(em);
        LocalDateTime issueDate = LocalDateTime.now();
        LocalDateTime expireDate;
        System.out.println("CIAOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        if(subscription == Subscription.DAILY){
            expireDate = issueDate.plusDays(1);

        } else if(subscription == Subscription.MONTHLY){
            expireDate = issueDate.plusDays(30);

        } else {
            expireDate = issueDate.plusDays(365);

        }
        System.out.println(code);
        System.out.println(issueDate);
        System.out.println(expireDate);
        System.out.println(subscription);

         try {
             em.getTransaction().begin();
             User user = em.find(User.class,userid);
             System.out.println("SONO QUIIIIIIIIII");
          /*   if(user == null){
                 log.error("ID not found");
                 em.getTransaction().rollback();
                 return;
             }
             Distributor distributor = em.find(Distributor.class, distributor_id);
             if(distributor == null){
                 log.error("ID not found");
                 em.getTransaction().rollback();
                 return;
             } */

             Ticket ticket = new Ticket(code,issueDate,expireDate,subscription,user);
             ticketDao.save(ticket);

         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }
    public static boolean isCodeExist(EntityManager em, String code){
        try {
            Query q = em.createQuery("SELECT t FROM Ticket t WHERE t.code = :code", Ticket.class);
            q.setParameter("code",code);
            q.getResultList();
            return true;
        } catch (NoResultException e) {
            return false;
        }

    }
    public static String codeGenerator(EntityManager em){
        String code;
        boolean isUnique = false;
        while (!isUnique){
            code = UUID.randomUUID().toString().substring(0, 8);
            if(!isCodeExist(em,code)){
                isUnique = true;
                return code;
            }
        }
        return null;
    }
}
