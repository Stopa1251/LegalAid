package com.example.lawtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @Lob
    private String comment;



//    @ManyToOne
//    @JoinColumn(name = "sender_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User sender;

//    @ManyToOne
//    @JoinColumn(name = "receiver_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private User receiver;



    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date sentAt = new Date();


    public Long getId() {
        return id;
    }


    public Date getSentAt() {
        return sentAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }


    @PrePersist
    protected void onCreate() {
        this.sentAt = new Date();
    }
}