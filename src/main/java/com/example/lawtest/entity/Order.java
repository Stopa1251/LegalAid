package com.example.lawtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Lob
    private String description;

    private String category;

    private String attachmentFilename;

    private String status = "ACTIVE";

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "assigned_lawyer_id")
    private User assignedLawyer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt = new Date();


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    public User getAssignedLawyer() {
        return assignedLawyer;
    }

    public void setAssignedLawyer(User assignedLawyer) {
        this.assignedLawyer = assignedLawyer;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getAttachmentFilename() {
        return attachmentFilename;
    }

    public String getStatus() {
        return status;
    }

    public User getClient() {
        return client;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAttachmentFilename(String attachmentFilename) {
        this.attachmentFilename = attachmentFilename;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}