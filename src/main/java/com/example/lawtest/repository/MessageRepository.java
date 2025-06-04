package com.example.lawtest.repository;


import com.example.lawtest.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findByLegalOrderOrderByAsc(Order order);

//    List<Message> findByLegalRequestOrderBySentAtAsc(LegalRequest legalRequest);

//    List<Message> findByUserIdOrderBySentAt(Long userId);
    List<Message> findMessageBySenderId(Long userId);
}