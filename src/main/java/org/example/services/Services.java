package org.example.services;

import org.example.DAO.*;
import org.example.Main;
import org.example.entities.*;
import org.example.enumeration.Subscription;
import org.example.enumeration.VehicleStatus;
import org.example.enumeration.VehicleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Services {

    private static Logger log = LoggerFactory.getLogger(Services.class);

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

    public void addVehicle(Vehicle vehicle) {
        try {
            vehicleDao.save(vehicle);
            log.info("Hai aggiunto un veicolo!");
        } catch (Exception e) {
            log.error("Non hai aggiunto il veicolo!", e);
        }
    }

    public void addDistributor(Distributor distributor) {
        try {
            distributorDao.save(distributor);
            log.info("Hai aggiunto un nuovo distrubutore");
        } catch (Exception e) {
            log.error("Non è stato possibile aggiungere il distributore. Controlla l'errore -> ", e);
        }
    }

    public void addUser(User user) {
        try {
            userDao.save(user);
            log.info("Hai aggiunto un nuovo user");
        } catch (Exception e) {
            log.error("Non è stato possibile aggiungere l'user . Controlla l'errore -> ", e);
        }
    }

    public void addRoute(Route route) {
        try {
            routeDao.save(route);
            log.info("Hai aggiunto una nuova rotta");
        } catch (Exception e) {
            log.error("Non è stato possibile aggiungere la rotta. Controlla l'errore -> ", e);
        }
    }

    public void addTicket(Ticket ticket) {
        try {
            ticketDao.save(ticket);
            log.info("Hai aggiunto un nuovo ticket");
        } catch (Exception e) {
            log.error("Non è stato possibile aggiungere il ticket. Controlla l'errore -> ", e);
        }
    }

    public void displayVehicles() {
        try {
            List<Vehicle> vehicles = em.createQuery("FROM Vehicle", Vehicle.class).getResultList();
            vehicles.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare i veicoli", e);
        }
    }

    public void displayDistributors() {
        try {
            List<Distributor> distributors = em.createQuery("FROM Distributor", Distributor.class).getResultList();
            distributors.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare i distributori", e);
        }
    }

    public void displayActiveDistributors(boolean isActive) {
        try {
            Query query = em.createQuery("SELECT d FROM Distributor d WHERE d.isActive = :isActive", Distributor.class);
            List<Distributor> listActive = query.setParameter("isActive", isActive).getResultList();
            listActive.forEach(System.out::println);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void displayUsers() {
        try {
            List<User> users = em.createQuery("FROM User", User.class).getResultList();
            users.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare gli users", e);
        }
    }

    public void displayRoutes(EntityManager em) {
        try {
            List<Route> routes = em.createQuery("FROM Route", Route.class).getResultList();
            routes.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Non è possibile visualizzare le tratte", e);
        }
    }

    public List<Route> displayRoutesActiveVehicles(VehicleStatus status, VehicleType vehicle) {
        List<Route> listRouteActive = null;
        try {
            if (vehicle == VehicleType.BUS) {
                Query query = em.createQuery("SELECT r FROM Route r JOIN r.vehicle v WHERE v.status = :status AND v.type = :type", Route.class).setParameter("status", status).setParameter("type", VehicleType.BUS);
                listRouteActive = query.getResultList();
            } else if (vehicle == VehicleType.TRAM) {
                Query query = em.createQuery("SELECT r FROM Route r JOIN r.vehicle v WHERE v.status = :status AND v.type = :type", Route.class).setParameter("status", status).setParameter("type", VehicleType.TRAM);
                listRouteActive = query.getResultList();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listRouteActive;
    }

    public void displayActiveVehicles(VehicleStatus status){
        try {
            List<Vehicle> vehiclesActive = em.createQuery("SELECT v FROM Vehicle v WHERE v.status = :status", Vehicle.class).setParameter("status",status).getResultList();
            vehiclesActive.forEach(System.out::println);

        } catch (Exception e) {
            throw new RuntimeException(e);
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
    public void buyTicket(long userid, Subscription subscription, long distributor_id, Scanner sc){
        String code = codeGenerator(em);
        LocalDateTime issueDate = LocalDateTime.now();
        LocalDateTime expireDate;
        if(subscription == Subscription.DAILY){
            expireDate = issueDate.plusDays(1);
        } else if(subscription == Subscription.MONTHLY){
            expireDate = issueDate.plusMonths(1);
        } else {
            expireDate = issueDate.plusYears(1);
        }
         try {
             em.getTransaction().begin();
             User user = em.find(User.class, userid);
             Distributor distributor = em.find(Distributor.class, distributor_id);
             if (user == null) {
                 log.error("ID not found");
                 em.getTransaction().rollback();
                 return;
             }
             if (distributor == null) {
                 log.error("ID not found");
                 em.getTransaction().rollback();
                 return;
             }
             System.out.print("What do you want? BUS or TRAM?");
             String resp = sc.nextLine();
             List<Route> listRoute = null;
             if(resp.equals("BUS")){

                 listRoute = displayRoutesActiveVehicles(VehicleStatus.ACTIVE,VehicleType.BUS);
                 listRoute.forEach(System.out::println);

             } else if (resp.equals("TRAM")) {
                 listRoute = displayRoutesActiveVehicles(VehicleStatus.ACTIVE,VehicleType.TRAM);
                 listRoute.forEach(System.out::println);

             }
             if (listRoute != null) {
                 System.out.println("Please, enter Route id : ");
                 int option = Integer.parseInt(sc.nextLine());
                 Route route = routeDao.getFinById(option);
                 if(route != null){
                     Ticket ticket = new Ticket(code, issueDate, expireDate, subscription, user, route);
                     ticket.setDistributor(distributor);
                     em.persist(ticket);
                     log.info("CONGRATULATIONS, YOU BOUGHT TICKET!! ❤️");
                     em.getTransaction().commit();
                 } else {
                     log.error("ROUTE NOT FOUND!❌");
                 }
             } else {
                 log.info("THERE IS NO ROUTE!");
             }
         }   catch(Exception e){
                 throw new RuntimeException(e);
             }
         }
    public static boolean isCodeExist(EntityManager em, String code){
            Query q = em.createQuery("SELECT t FROM Ticket t WHERE t.code = :code", Ticket.class);
            q.setParameter("code",code);
            return !q.getResultList().isEmpty();
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
    public static void deleteUser(EntityManager em, long id){
        User user = new User();
        em.getTransaction().begin();
        user = em.find(User.class,id);
        em.remove(user);
        em.getTransaction().commit();
    }
    public static void deleteRoute(EntityManager em, long id){
        Route route = new Route();
        em.getTransaction().begin();
        route = em.find(Route.class,id);
        em.remove(route);
        em.getTransaction().commit();
    }
    public static void deleteTicket(EntityManager em, long id){
        Ticket ticket = new Ticket();
        em.getTransaction().begin();
        ticket = em.find(Ticket.class,id);
        em.remove(ticket);
        em.getTransaction().commit();
    }
    public static void deleteVehicle(EntityManager em, long id){
        Vehicle vehicle = new Vehicle();
        em.getTransaction().begin();
        vehicle = em.find(Vehicle.class,id);
        em.remove(vehicle);
        em.getTransaction().commit();
    }
    public static void deleteDistributor(EntityManager em, long id){
        Distributor distributor = new Distributor();
        em.getTransaction().begin();
        distributor = em.find(Distributor.class,id);
        em.remove(distributor);
        em.getTransaction().commit();
    }
}