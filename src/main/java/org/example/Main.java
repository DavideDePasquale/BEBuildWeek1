package org.example;

import com.github.javafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Locale;

/**
 * Hello world!
 *
 */
public class Main 
{
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("BEBuildWeek1");
    private EntityManager em =  emf.createEntityManager();
    Faker faker = new Faker();

    public static void main( String[] args ) {


    }
}
