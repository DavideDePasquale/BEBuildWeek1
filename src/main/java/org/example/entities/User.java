package org.example.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private  String surname;
    private String password;
    private String cardNumber;
    private boolean isAdmin;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();


    public User() {
    }

    public User(String name, String surname,String password, String cardNumber, boolean isAdmin) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.cardNumber = cardNumber;
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        System.out.println("CIAOOOO" + this.password);
    }

    public User(long id, String name, String surname, String password, String cardNumber, boolean isAdmin, List<Ticket> tickets) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.cardNumber = cardNumber;
        this.isAdmin = isAdmin;
        this.tickets = tickets;
    }

    public long getId
            () {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
