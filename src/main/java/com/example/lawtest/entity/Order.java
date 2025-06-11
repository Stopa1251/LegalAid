package com.example.lawtest.entity;

import com.example.lawtest.repository.SpecializationRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.springframework.security.core.GrantedAuthority;
//import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @FullTextField(analyzer = "ukrainian")
    private String subject;

//    @Lob
    @Column(length = 1000)
    private String description;

//    @OneToOne(mappedBy = "assignedLawyer", cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = true)
//    @FullTextField(analyzer = "ukrainian")
    private Specialization specialization;

//    @FullTextField(analyzer = "ukrainian")
    private double price;

    private String attachmentFilename;

    private String status;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")  // FK до User (клієнт)
    private User client;

    @ManyToOne
    @JoinColumn(name = "assigned_lawyer_id")
    private User assignedLawyer;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date sentAt = new Date();

    private boolean publicView;

    @Enumerated(EnumType.STRING)
    private CommunicationFormat communicationFormat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.status = "NEW";
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public boolean isPublicView() {
        return publicView;
    }

    public void setPublicView(boolean publicView) {
        this.publicView = publicView;
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

    public String getAttachmentFilename() {
        return attachmentFilename;
    }

    public String getStatus() {
        return status;
    }

    public User getClient() {
        return client;
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

    public void setAttachmentFilename(String attachmentFilename) {
        this.attachmentFilename = attachmentFilename;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(User client) {
        this.client = client;
    }


    public User getAssignedLawyer() {
        return assignedLawyer;
    }

    public void setAssignedLawyer(User assignedLawyer) {
        this.assignedLawyer = assignedLawyer;
    }

    public CommunicationFormat getCommunicationFormat() {
        return communicationFormat;
    }

    public void setCommunicationFormat(CommunicationFormat communicationFormat) {
        this.communicationFormat = communicationFormat;
    }

    public enum CommunicationFormat implements GrantedAuthority {
        online, offline;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}