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

    public Ticket() {
    }

    public Ticket(String code, LocalDateTime issueDate, LocalDateTime expireDate, Subscription subscription, User user) {
        this.code = code;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.subscription = subscription;
        this.user = user;
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

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", issueDate=" + issueDate +
                ", expireDate=" + expireDate +
                ", subscription=" + subscription +
                ", user=" + (user != null ? user.getId() : "null") +
                ", distributor=" + (distributor!= null ? distributor.getId() : "null") +
                '}';
    }
}
