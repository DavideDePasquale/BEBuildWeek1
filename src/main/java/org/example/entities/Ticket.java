package org.example.entities;


import org.example.enumeration.Subscription;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String code;
    private LocalDateTime issueDate;
    private LocalDateTime expireDate;
    @Enumerated(EnumType.STRING)
    private Subscription subscription;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Ticket() {
    }

    public Ticket(String code, LocalDateTime issueDate, LocalDateTime expireDate, Subscription subscription, User user,Route route) {
        this.code = code;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.subscription = subscription;
        this.user = user;
        this.route = route;

    }

    public Ticket(String code, LocalDateTime issueDate, LocalDateTime expireDate, User user, Distributor distributor, Route route) {
        this.code = code;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.user = user;
        this.distributor = distributor;
        this.route = route;
    }

    public Ticket(String code, LocalDateTime issueDate, LocalDateTime expireDate, User user, Distributor distributor, Trip trip) {
        this.code = code;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.user = user;
        this.distributor = distributor;
        this.trip = trip;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", issueDate=" + issueDate +
                ", expireDate=" + expireDate +
                ", subscription=" + subscription +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", distributor=" + distributor +
                ", route=" + route +
                '}';
    }
}
