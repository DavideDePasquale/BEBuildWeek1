package org.example;

import com.github.javafaker.Faker;
import org.example.DAO.*;
import org.example.entities.*;
import org.example.enumeration.DistributorType;
import org.example.enumeration.Subscription;
import org.example.enumeration.VehicleStatus;
import org.example.enumeration.VehicleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Scanner;


public class Main 
{
    public static void main( String[] args ) {

         EntityManagerFactory emf = Persistence.createEntityManagerFactory("BEBuildWeek1");
         EntityManager em =  emf.createEntityManager();
         Faker faker = new Faker();
      //   Scanner sc = new Scanner(System.in);
        UserDAO userDao = new UserDAO(em);
        VehicleDAO vehicleDao = new VehicleDAO(em);
        DistributorDAO distributorDao = new DistributorDAO(em);
        RouteDAO routeDao = new RouteDAO(em);
        TicketDAO ticketDao = new TicketDAO(em);


//        User user1 = new User("Mahdi","Nazari","1234AB234",true);
       Vehicle v1 = new Vehicle(VehicleType.BUS,50, VehicleStatus.ACTIVE);
//
//        userDao.save(user1);

        Distributor d1 = new Distributor(DistributorType.AUTOMATIC,"Tabaccheria 127","Bari", true);
        distributorDao.save(d1);

        Route r1 = new Route("Bari","Roma",240);
        r1.setVehicle(v1);
        vehicleDao.save(v1);
        routeDao.save(r1);

        Ticket t1 = new Ticket("ACcccc12345", LocalDateTime.of(2025,1,24,10,40),LocalDateTime.of(2025,1,24,10,40).plusDays(30), Subscription.MONTHLY,userDao.getFinById(1));

        t1.setDistributor(d1);
        ticketDao.save(t1);





    }
}
