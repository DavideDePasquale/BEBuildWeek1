package org.example.services;

import org.example.DAO.*;
import org.example.Main;
import org.example.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

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

}
