package org.example.services;

import org.apache.commons.io.FileUtils;
import org.example.entities.*;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FileExport {

    public static void writeListOfUsersToFile(List<User> listUser, String filePath){
        File file = new File(filePath);
        try {
            List<String> users = listUser.stream().map(User::toString).collect(Collectors.toList());
            FileUtils.writeLines(file,users);
            System.out.println("LIST HAS BEEN WRITEN TO " + filePath + "users");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeListOfVehiclesToFile(List<Vehicle> vehicles, String filePath){
        File file = new File(filePath);
        try {
            List<String> listStringVehicles = vehicles.stream().map(Vehicle::toString).collect(Collectors.toList());
            FileUtils.writeLines(file,listStringVehicles);
            System.out.println("LIST HAS BEEN WRITEN TO " + filePath + "vehicles");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeListOfRoutesToFile(List<Route> routes, String filePath){
        File file = new File(filePath);
        try {
            List<String> listStringRoutes = routes.stream().map(Route::toString).collect(Collectors.toList());
            FileUtils.writeLines(file,listStringRoutes);
            System.out.println("LIST HAS BEEN WRITEN TO " + filePath + "routes");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeListOfTripsToFile(List<Trip> trips, String filePath){
        File file = new File(filePath);
        try {
            List<String> listStringTrips = trips.stream().map(Trip::toString).collect(Collectors.toList());
            FileUtils.writeLines(file,listStringTrips);
            System.out.println("LIST HAS BEEN WRITEN TO " + filePath + "trips");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeListOfTicketToFile(List<Ticket> tickets, String filePath){
        File file = new File(filePath);
        try {
            List<String> listStringTickets = tickets.stream().map(Ticket::toString).collect(Collectors.toList());
            FileUtils.writeLines(file,listStringTickets);
            System.out.println("LIST HAS BEEN WRITEN TO " + filePath + "tickets");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
