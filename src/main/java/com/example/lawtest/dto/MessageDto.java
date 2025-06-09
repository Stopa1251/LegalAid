package com.example.lawtest.dto;

import com.example.lawtest.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long senderId;
    private Long recipientId;
    private String senderName;
    private String content;
    private String sentAt;


//    public static MessageDto fromEntity(Message message) {
//        MessageDto dto = new MessageDto();
//        dto.setSenderName(message.getSenderId().getLastName());
//        dto.setContent(message.getContent());
//        dto.setSentAt(message.getSentAt().toString());
//        return dto;
//    }


    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String timestamp) {
        this.sentAt = sentAt;
    }


}