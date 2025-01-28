package org.example;


import antlr.CodeGenerator;
import org.example.DAO.RouteDAO;
import org.example.entities.*;
import org.example.enumeration.DistributorType;
import org.example.enumeration.Subscription;
import org.example.enumeration.VehicleStatus;
import org.example.enumeration.VehicleType;
import org.example.services.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Scanner;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BEBuildWeek1");
        EntityManager em = emf.createEntityManager();
        User loggedinUser = login(em);
        Scanner sc = new Scanner(System.in);
        Services services = new Services(em);


        if (loggedinUser != null) {
            System.out.print("Login Successfull!!!");

            if (loggedinUser.isAdmin()) {

                System.out.println("Welcome Admin " + loggedinUser.getName());
                while (true) {
                    System.out.println("""
                            1 - Add items
                            2 - Delete items
                            3 - Display items
                            """);
                    int resp = Integer.parseInt(sc.nextLine());
                    switch (resp) {
                        case 1: {
                            boolean back = true;
                            while (back) {

                                System.out.println("""
                                          1 - Add Vehicle
                                          2 - Add Distributor
                                          3 - Add Route
                                          4 - Add User
                                          5 - Add Ticket
                                          0 - Back
                                        
                                        
                                        """);
                                int option = Integer.parseInt(sc.nextLine());
                                switch (option) {
                                    case 1: {
                                        System.out.print("Please enter Vehicle type (BUS or TRAM) : ");
                                        VehicleType type = VehicleType.valueOf(sc.nextLine().toUpperCase());
                                        System.out.print("Enter capacity : ");
                                        int capacity = sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Enter status ( MAINTENANCE or ACTIVE ) : ");
                                        VehicleStatus status = VehicleStatus.valueOf(sc.nextLine().toUpperCase());
                                        services.addVehicle(new Vehicle(type, capacity, status));
                                        break;
                                    }
                                    case 2: {
                                        System.out.print("Enter Distributor Type ( AUTOMATIC or AUTHORIZED ) : ");
                                        DistributorType type = DistributorType.valueOf(sc.nextLine().toUpperCase());
                                        System.out.print("Enter Distributor name : ");
                                        String name = sc.nextLine();
                                        System.out.print("Enter Distributor location : ");
                                        String location = sc.nextLine();
                                        System.out.print("Is Active? (true or false) : ");
                                        boolean isActive = sc.nextBoolean();
                                        sc.nextLine();
                                        services.addDistributor(new Distributor(type, name, location, isActive));
                                        break;
                                    }
                                    case 3: {
                                        System.out.print("Enter start point : ");
                                        String startPoint = sc.nextLine();
                                        System.out.print("Enter end point : ");
                                        String endPoint = sc.nextLine();
                                        System.out.print("Estimated duration : ");
                                        int duration = sc.nextInt();
                                        sc.nextLine();
                                        services.addRoute(new Route(startPoint, endPoint, duration));
                                        break;
                                    }
                                    case 4: {
                                        System.out.print("Enter User name : ");
                                        String name = sc.nextLine();
                                        System.out.print("Enter user surname : ");
                                        String surname = sc.nextLine();
                                        System.out.print("Enter password : ");
                                        String password = sc.nextLine();
                                        System.out.print("Enter Card number : ");
                                        String cardNumber = sc.nextLine();
                                        System.out.print("Is Admin? (True or False) : ");
                                        boolean isAdmin = sc.nextBoolean();
                                        sc.nextLine();
                                        services.addUser(new User(name, surname, password, cardNumber, isAdmin));
                                        break;
                                    }
                                    case 5: {
                                        System.out.print("This value is Unique");
                                        System.out.print("Enter Ticket code : ");
                                        String code = sc.nextLine();
                                        System.out.print("Enter issue date (yyyy-MM-dd'T'HH:mm) : ");
                                        LocalDateTime issueDate = LocalDateTime.parse(sc.nextLine());
                                        System.out.print("Enter expire Date (yyyy-MM-dd'T'HH:mm) : ");
                                        LocalDateTime expireDate = LocalDateTime.parse(sc.nextLine());
                                        System.out.print("Enter subscription type ( MONTHLY, DAITLY or ANNUAL ) : ");
                                        Subscription type = Subscription.valueOf(sc.nextLine().toUpperCase());
                                        System.out.print("Enter User id : ");
                                        long userId = sc.nextLong();
                                        sc.nextLine();
                                        User user = em.find(User.class, userId);
                                        if (user == null) {
                                            System.out.println("User not found!");
                                            break;
                                        }
                                        services.addTicket(new Ticket(code, issueDate, expireDate, type, user));
                                        break;
                                    }
                                    case 0: {
                                        back = false;
                                        break;
                                    }
                                    default: {
                                        System.out.println("Invalid Options! Write again");
                                    }
                                }
                            }
                        }
                        case 2: {
                            while (true) {
                                System.out.println("""
                                        1 - Delete User
                                        2 - Delete Distributor
                                        3 - Delete Ticket
                                        4 - Delete Route
                                        5 - Delete Vehicle 
                                        0 - Back
                                        """);
                                int option = Integer.parseInt(sc.nextLine());
                                switch (option) {
                                    case 1: {
                                        System.out.print("Enter User id : ");
                                        long id = sc.nextLong();
                                        sc.nextLine();
                                        services.deleteUser(em, id);
                                        System.out.println("User is deleted!");
                                        break;
                                    }
                                    case 2: {
                                        System.out.print("Enter Distributor id : ");
                                        long id = sc.nextLong();
                                        sc.nextLine();
                                        services.deleteDistributor(em, id);
                                        System.out.println("Distributor is deleted!");
                                        break;
                                    }
                                    case 3: {
                                        System.out.print("Enter Ticket id : ");
                                        long id = sc.nextLong();
                                        sc.nextLine();
                                        services.deleteTicket(em, id);
                                        System.out.println("Ticket is deleted!");
                                        break;
                                    }
                                    case 4: {
                                        System.out.print("Enter Route id : ");
                                        long id = sc.nextLong();
                                        sc.nextLine();
                                        services.deleteRoute(em, id);
                                        System.out.println("Route is deleted!");
                                        break;
                                    }
                                    case 5: {
                                        System.out.print("Enter Vehicle id : ");
                                        long id = sc.nextLong();
                                        sc.nextLine();
                                        services.deleteVehicle(em, id);
                                        System.out.println("Vehicle is deleted!");
                                        break;
                                    }
                                    case 0: {
                                        break;
                                    }
                                    default: {
                                        System.out.println("Invalid Options! Write again");
                                    }
                                }
                            }
                        }
                        case 3: {
                            while (true) {
                                System.out.println("""  
                                        1 - Display Vehicles
                                        2 - Display Distributors
                                        3 - Display Routes
                                        4 - Display Users
                                        5 - Display Tickets   
                                        0 - Back
                                        """);
                                int options = sc.nextInt();
                                sc.nextLine();
                                switch (options) {
                                    case 1: {
                                        services.displayVehicles();
                                        break;
                                    }
                                    case 2: {
                                        services.displayDistributors();
                                        break;
                                    }
                                    case 3: {
                                        services.displayRoutes();
                                        break;
                                    }
                                    case 4: {
                                        services.displayUsers();
                                        break;
                                    }
                                    case 5: {
                                        services.displayTickets();
                                        break;
                                    }
                                    case 0: {
                                        break;
                                    }
                                    default: {
                                        System.out.println("Invalid Options! Write again");
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Welcome User " + loggedinUser.getName());
                System.out.print("Enter Subscription type ( DAILY, MONTHLY or ANNUAL ) : ");
                Subscription subscription = Subscription.valueOf(sc.nextLine().toUpperCase());
                System.out.print("Enter Distributor id : ");
                long distributorId = sc.nextLong();
                sc.nextLine();
                long id = loggedinUser.getId();
                services.buyTicket(id, subscription, distributorId);
                log.info("Hai appena comprato un biglietto! Buon viaggio");
                //break;
            }
        } else {
            System.out.print("Login failed");
        }
    }
    private static User login(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        User authorizateUser = null;
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter User name : ");
            String name = sc.nextLine();
            System.out.print("Enter User password : ");
            String password = sc.nextLine();
            try {
                TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password = :password", User.class);
                query.setParameter("name", name);
                query.setParameter("password", password);
                authorizateUser = query.getSingleResult();
                break;
            } catch (RuntimeException e) {
                log.error("Login failed, try again!");
            }
        }
        return authorizateUser;
    }
}