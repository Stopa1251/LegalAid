package com.example.lawtest.repository;


import com.example.lawtest.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findByLegalOrderOrderByAsc(Order order);

//    List<Message> findByLegalRequestOrderBySentAtAsc(LegalRequest legalRequest);

//    List<Message> findByUserIdOrderBySentAt(Long userId);
    List<Message> findMessageBySenderId(Long userId);

    List<Message> findMessageBySenderIdAndRecipientId(Long senderId, Long recipientId);

    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderId = :userId AND m.recipientId = :recipientId) OR " +
            "(m.senderId = :recipientId AND m.recipientId = :userId) " +
            "ORDER BY m.sentAt ASC")
    List<Message> findChatHistory(Long userId, Long recipientId);
    
}