package com.example.lawtest.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

//    @ManyToOne
//    @JoinColumn(name = "sender_id")
    @Column(name = "sender_id")
    private Long senderId;

//    @ManyToOne
//    @JoinColumn(name = "recipient_id")
    @Column(name = "recipient_id")
    private Long recipientId;

    private LocalDateTime sentAt;

//    @Column(name = "message_has_read")

    private boolean isRead;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getSenderId() {
        return senderId;
    }

//    public LegalOrder getLegalOrder() {
//        return legalOrder;
//    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

//    public void setLegalOrder(LegalOrder legalOrder) {
//        this.legalOrder = legalOrder;
//    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}