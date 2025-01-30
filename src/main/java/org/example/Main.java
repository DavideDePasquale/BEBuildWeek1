package org.example;


import antlr.CodeGenerator;
import org.example.DAO.*;
import org.example.entities.*;
import org.example.enumeration.DistributorType;
import org.example.enumeration.Subscription;
import org.example.enumeration.VehicleStatus;
import org.example.enumeration.VehicleType;
import org.example.services.Reports;
import org.example.services.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static org.example.services.Services.*;


public class Main {
       private static final Logger log = LoggerFactory.getLogger(Main.class);

       private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BEBuildWeek1");
       private static final  EntityManager em = emf.createEntityManager();
       private static final   User loggedinUser = login(em);
       private static final   Scanner sc = new Scanner(System.in);
       private static final Reports reports = new Reports();


    public static void main(String[] args) {
          Services services = new Services(em);

        if (loggedinUser != null) {
            System.out.println("Login Successfull!!!");

            if (loggedinUser.isAdmin()) {
                adminMenu(sc,services,em);
            }  else {
             userMenu(sc,loggedinUser,services,em);
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

    }

    private static void deleteTicket(Scanner sc, Services services, EntityManager em) {
        System.out.print("Enter Ticket id : ");
        long id = sc.nextLong();
        sc.nextLine();
        services.deleteTicket(em, id);
        System.out.println("Ticket is deleted!");

    }

    private static void deleteDistributor(Scanner sc, Services services, EntityManager em) {
        System.out.print("Enter Distributor id : ");
        long id = sc.nextLong();
        sc.nextLine();
        services.deleteDistributor(em, id);
        System.out.println("Distributor is deleted!");

    }

    private static void deleteUser(Scanner sc, Services services, EntityManager em) {
        System.out.print("Enter User id : ");
        long id = sc.nextLong();
        sc.nextLine();
        services.deleteUser(em, id);
        System.out.println("User is deleted!");

    }
    private static void deleteTrip(Scanner sc,Services services,EntityManager em){
        services.displayTrips(em);
        System.out.print("Please Select Trip id : ");
        int trip_id = Integer.parseInt(sc.nextLine());
        services.deleteTrip(em,trip_id);
        System.out.println("Trip is deleted!");
    }

    private static void addTicket(Scanner sc, EntityManager em, Services services) {
        RouteDAO routeDao = new RouteDAO(em);

        String code = codeGenerator(em);
        System.out.print("Enter issue date (yyyy-MM-dd'T'HH:mm) : ");
        LocalDateTime issueDate = LocalDateTime.parse(sc.nextLine());
        System.out.print("Enter expire Date (yyyy-MM-dd'T'HH:mm) : ");
        LocalDateTime expireDate = LocalDateTime.parse(sc.nextLine());
        services.displayUsers();
        System.out.print("Enter User id : ");
        long userId = sc.nextLong();
        sc.nextLine();
        User user = em.find(User.class, userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        services.displayRoutes(em);
        System.out.println("Please, enter Route id : ");
        int option = Integer.parseInt(sc.nextLine());
        Route route = routeDao.getFinById(option);
        services.displayDistributors();
        System.out.println("Please, enter Distributor id : ");
        long dis_id = sc.nextLong();
        sc.nextLine();
        Distributor distributor = em.find(Distributor.class,dis_id);
        services.addTicket(new Ticket(code,issueDate,expireDate,user,distributor,route));
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
        System.out.print("Please enter Vehicle type (BUS, TRAM or TRAIN) : ");
        VehicleType type = VehicleType.valueOf(sc.nextLine().toUpperCase());
        System.out.print("Enter capacity : ");
        int capacity = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter status ( MAINTENANCE or ACTIVE ) : ");
        VehicleStatus status = VehicleStatus.valueOf(sc.nextLine().toUpperCase());
        services.addVehicle(new Vehicle(type, capacity, status));
        return;
    }
    private static void addTrip(Scanner sc,Services services, EntityManager em){
        displayVehicles(em);
        System.out.println("Please, Select Vehicle id : ");
        int vehicleType = sc.nextInt();
        sc.nextLine();
        VehicleDAO vehicleDAO = new VehicleDAO(em);
        Vehicle vehicle = vehicleDAO.getFinById(vehicleType);
        services.displayRoutes(em);
        System.out.print("Please, Select Route id : ");
        int route_id = Integer.parseInt(sc.nextLine());
        RouteDAO routeDAO = new RouteDAO(em);
        Route route = routeDAO.getFinById(route_id);
        System.out.print("Please, select Start Time : ");
        LocalDateTime startTime = LocalDateTime.parse(sc.nextLine());
        System.out.print("Please, select End Time : ");
        LocalDateTime endTime = LocalDateTime.parse(sc.nextLine());
        Trip trip = new Trip(vehicle,route,startTime,endTime);
        services.addTrip(trip);
    }
    public static void purchaseTicketMenu(EntityManager em, Scanner sc, User loggedinUser, Services services){
        services.displayTrips(em);
        System.out.print("SELECT TRIP ID : ");
        int trip_id = Integer.parseInt(sc.nextLine());
        TripDAO tripDAO = new TripDAO(em);
        Trip trip = tripDAO.getFinById(trip_id);

        if (trip == null) {
            System.out.println("Trip not found!");
            return;
        }

        services.displayDistributors();
        System.out.print("SELECT DISTRIBUTOR ID : ");
        int distributor_id = Integer.parseInt(sc.nextLine());
        DistributorDAO distributorDAO = new DistributorDAO(em);
        Distributor distributor = distributorDAO.getFinById(distributor_id);

        if (distributor == null) {
            System.out.println("Distributor not found!");
            return;
        }

        System.out.println(loggedinUser + " " + trip + " " + distributor);
        services.purchaseTicket(loggedinUser, trip, distributor, em);
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
                        6 - Add Trip
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
                    case 6: {
                        addTrip(sc,services,em);
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
                        6 - Delete Trip
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
                    case 6: {
                        deleteTrip(sc,services,em);
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
                        6 - Display Trips
                        0 - Back
                        """);
                int options = Integer.parseInt(sc.nextLine());
                switch (options) {
                    case 1: {
                        services.displayVehicles(em);
                        break;
                    }
                    case 2: {
                        services.displayDistributors();
                        break;
                    }
                    case 3: {
                        services.displayRoutes(em);
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
                    case 6: {
                        services.displayTrips(em);
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
                        4 REPORTS
                        0 EXIT
                        """);
                int option = Integer.parseInt(sc.nextLine());
                if (option == 1) {
                    addMenu(sc, services, em);
                } else if (option == 2) {
                    deleteMenu(sc, services, em);
                } else if (option == 3) {
                    displayMenu(sc, services, em);
                } else if (option == 4) {
                    reportsMenu(services);
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
    public static void userMenu(Scanner sc, User loggedinUser, Services services, EntityManager em){
        while (true){
            try {
                System.out.println("""
                1 - BUY TICKET
                2 - SHOW TICKETS
                0 - EXIT
                """);
                int option = Integer.parseInt(sc.nextLine());
                if ( option == 1 ){
                    System.out.println("""
                            1 - PUBLIC TRANSPORTATION
                            2 - INTERCITY TRANSPORTATION
                            """);
                    int opt = Integer.parseInt(sc.nextLine());
                    if(opt == 1 ){
                        buyTicket(sc,loggedinUser,services,em);
                    } else if(opt == 2){
                        purchaseTicketMenu(em,sc,loggedinUser,services);
                    }
                } else if ( option == 2 ){
                    displayUserTickets(em,loggedinUser);
                } else if ( option == 0 ){
                    log.info("BYE BYE");
                    break;
                } else {
                    log.error("INVALID OPTION!");
                }
            } catch (RuntimeException e) {
               log.error("SELECT NUMBER BETWEEN 1 and 2 and for EXIT insert 0");
            }
        }

    }
    public static void reportsMenu(Services services){
        while (true) {
            System.out.println("""
                    1 - SEARCH SOLED TICKET BY USER ID
                    2 - SEARCH SOLED TICKET BY TRIP
                    3 - SEARCH SOLED TICKET BY DATE
                    4 - SEARCH SOLED TICKET BY VEHICLE
                    0 - BACK
                    """);
            int option = sc.nextInt();
            sc.nextLine();
            if (option == 1) {
                services.displayUsers();
                System.out.print("INSERT USER ID : ");
                long user_id = Long.parseLong(sc.nextLine());
                UserDAO userDAO = new UserDAO(em);
                User user = userDAO.getFinById(user_id);
                Reports.searchTicketsById(user,em);

            } else if (option == 0) {
                break;

            }
        }

    }

    public static void buyTicket(Scanner sc, User loggedinUser, Services services,EntityManager em){
        System.out.println("Welcome User " + loggedinUser.getName());
        System.out.print("Enter Subscription type ( DAILY, MONTHLY or ANNUAL ) : ");
        Subscription subscription = Subscription.valueOf(sc.nextLine().toUpperCase());
        System.out.print("STARTPOINT : ");
        String startPoint = sc.nextLine();
        System.out.print("ENDPOINT : ");
        String endPoint = sc.nextLine();
        System.out.print("What do you want? BUS or TRAM?");
        String resp = sc.nextLine();
        List<Route> listRoute = null;
        if(resp.equals("BUS")){
            listRoute = displayRoutesActiveVehicles(VehicleStatus.ACTIVE,VehicleType.BUS,em,startPoint,endPoint);

        } else if (resp.equals("TRAM")) {

            listRoute = displayRoutesActiveVehicles(VehicleStatus.ACTIVE,VehicleType.TRAM,em,startPoint,endPoint);
        }
        System.out.println("-------------------------------------------------------------------------------");
        services.displayActiveDistributors(true);
        System.out.print("Enter Distributor id : ");
        long distributorId = sc.nextLong();
        System.out.println("-------------------------------------------------------------------------------");
        sc.nextLine();
        long id = loggedinUser.getId();
        services.buyTicket(id, subscription, distributorId,sc,listRoute);
    }
    public static void displayUserTickets(EntityManager em, User loggedinUser){
        try {
            Query q = em.createQuery("SELECT t FROM Ticket t WHERE t.user.id = :userId", Ticket.class);
            q.setParameter("userId",loggedinUser.getId());
            List<Ticket> listTickets = q.getResultList();
            if(listTickets.isEmpty()) {
                System.out.println("NO TICKET FOUND FOR THIS USER");
            } else {
                System.out.println("TICKETS FOR USER : ");
                listTickets.forEach(System.out::println);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}