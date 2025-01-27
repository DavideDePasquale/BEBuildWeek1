package org.example;

import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main 
{
    public static void main( String[] args ) {

         EntityManagerFactory emf = Persistence.createEntityManagerFactory("BEBuildWeek1");
         EntityManager em =  emf.createEntityManager();
         Faker faker = new Faker();

    }
}
