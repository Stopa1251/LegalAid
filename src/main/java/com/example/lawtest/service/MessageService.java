package com.example.lawtest.service;

import com.example.lawtest.entity.Message;
import com.example.lawtest.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository chatMessageRepository;

    public Message save(Message message) {
        message.setSentAt(LocalDateTime.now());
        message.setIsRead(false);
        return chatMessageRepository.save(message);
    }

    public List<Message> getHistory(Long userId) {
        return chatMessageRepository.findMessageBySenderId(userId);
    }
}