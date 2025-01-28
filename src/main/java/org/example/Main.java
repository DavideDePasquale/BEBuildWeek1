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
            System.out.println("Login Successfull!!!");

            if (loggedinUser.isAdmin()) {
                adminMenu(sc,services,em);
            }  else {
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

    private static void deleteVehicle(Scanner sc, Services services, EntityManager em) {
        System.out.print("Enter Vehicle id : ");
        long id = sc.nextLong();
        sc.nextLine();
        services.deleteVehicle(em, id);
        System.out.println("Vehicle is deleted!");
        return;
    }

    private static void deleteRoute(Scanner sc, Services services, EntityManager em) {
        System.out.print("Enter Route id : ");
        long id = sc.nextLong();
        sc.nextLine();
        services.deleteRoute(em, id);
        System.out.println("Route is deleted!");
        return;
    }

    private static void deleteTicket(Scanner sc, Services services, EntityManager em) {
        System.out.print("Enter Ticket id : ");
        long id = sc.nextLong();
        sc.nextLine();
        services.deleteTicket(em, id);
        System.out.println("Ticket is deleted!");
        return;
    }

    private static void deleteDistributor(Scanner sc, Services services, EntityManager em) {
        System.out.print("Enter Distributor id : ");
        long id = sc.nextLong();
        sc.nextLine();
        services.deleteDistributor(em, id);
        System.out.println("Distributor is deleted!");
        return;
    }

    private static void deleteUser(Scanner sc, Services services, EntityManager em) {
        System.out.print("Enter User id : ");
        long id = sc.nextLong();
        sc.nextLine();
        services.deleteUser(em, id);
        System.out.println("User is deleted!");
        return;
    }

    private static void addTicket(Scanner sc, EntityManager em, Services services) {
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
            return;
        }
        services.addTicket(new Ticket(code, issueDate, expireDate, type, user));
        return;
    }

    private static void addUser(Scanner sc, Services services) {
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
        return;
    }

    private static void addRoute(Scanner sc, Services services) {
        System.out.print("Enter start point : ");
        String startPoint = sc.nextLine();
        System.out.print("Enter end point : ");
        String endPoint = sc.nextLine();
        System.out.print("Estimated duration : ");
        int duration = sc.nextInt();
        sc.nextLine();
        services.addRoute(new Route(startPoint, endPoint, duration));
        return;
    }

    private static void addDistributor(Scanner sc, Services services) {
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
        return;
    }

    private static void addVehicle(Scanner sc, Services services) {
        System.out.print("Please enter Vehicle type (BUS or TRAM) : ");
        VehicleType type = VehicleType.valueOf(sc.nextLine().toUpperCase());
        System.out.print("Enter capacity : ");
        int capacity = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter status ( MAINTENANCE or ACTIVE ) : ");
        VehicleStatus status = VehicleStatus.valueOf(sc.nextLine().toUpperCase());
        services.addVehicle(new Vehicle(type, capacity, status));
        return;
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

    public static void addMenu(Scanner sc, Services services, EntityManager em) {
        boolean back = true;
        while (back) {
            try {

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
                        addVehicle(sc, services);
                        break;
                    }
                    case 2: {
                        addDistributor(sc, services);
                        break;
                    }
                    case 3: {
                        addRoute(sc, services);
                        break;
                    }
                    case 4: {
                        addUser(sc, services);
                        break;
                    }
                    case 5: {
                        addTicket(sc, em, services);
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
            }   catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void deleteMenu(Scanner sc, Services services, EntityManager em){
        boolean back = true;
        while (back) {
            try {
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
                        deleteUser(sc, services, em);
                        break;
                    }
                    case 2: {
                        deleteDistributor(sc, services, em);
                        break;
                    }
                    case 3: {
                        deleteTicket(sc, services, em);
                        break;
                    }
                    case 4: {
                        deleteRoute(sc, services, em);
                        break;
                    }
                    case 5: {
                        deleteVehicle(sc, services, em);
                    }
                    case 0: {
                        back = false;
                        break;
                    }
                    default: {
                        System.out.println("Invalid Options! Write again");
                    }
                }
            }   catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void displayMenu(Scanner sc, Services services, EntityManager em){
        boolean back = true;
        while (back) {
            try {
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
                        back = false;
                        break;
                    }
                    default: {
                        System.out.println("Invalid Options! Write again");
                    }
                }
            }   catch (RuntimeException e) {
                log.error("AOOOOOOOOOO" + e);
                break;
            }
        }
    }
    public static void adminMenu(Scanner sc, Services services,EntityManager em){
        while (true) {
            try {
                System.out.print("""
                        1 ADD ITEMS
                        2 DELETE ITEMS
                        3 DISPLAY ITEMS
                        0 EXIT
                        """);
                int option = Integer.parseInt(sc.nextLine());
                if (option == 1) {
                    addMenu(sc, services, em);
                } else if (option == 2) {
                    deleteMenu(sc, services, em);
                } else if (option == 3) {
                    displayMenu(sc, services, em);
                } else if (option == 0) {
                    System.out.println("EXITING");
                    break;
                } else {
                    log.error("INVALID NUMBER! TRY AGAIN");
                }
            } catch (RuntimeException e) {
               log.error("INVALID OPTION!");
            }
        }
    }

}
