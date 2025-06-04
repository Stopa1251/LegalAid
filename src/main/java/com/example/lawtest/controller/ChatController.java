package com.example.lawtest.controller;

import com.example.lawtest.dto.MessageDto;
import com.example.lawtest.entity.Message;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.MessageRepository;
import com.example.lawtest.repository.UserRepository;
import com.example.lawtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping
public class ChatController {

//    private final LegalRequestRepository legalRequestRepo;
    @Autowired
    private MessageRepository messageRepository;
//    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController( UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

//
//    public ChatController(LegalRequestRepository legalRequestRepo, MessageRepository messageRepo, UserRepository userRepo) {
//        this.legalRequestRepo = legalRequestRepo;
//        this.messageRepo = messageRepo;
//        this.userRepo = userRepo;
//    }
//
//    @GetMapping
//    public String chat(@PathVariable Long requestId, Model model, Principal principal) {
//        LegalRequest request = legalRequestRepo.findById(requestId).orElseThrow();
//        List<Message> messages = messageRepo.findByLegalRequestOrderBySentAtAsc((request));
//
//        model.addAttribute("request", request);
//        model.addAttribute("messages", messages);
//        model.addAttribute("newMessage", new Message());
//        return "chat";
//    }
//
//    @PostMapping
//    public String sendMessage(@PathVariable Long requestId, @ModelAttribute Message newMessage, Principal principal) {
//        LegalRequest request = legalRequestRepo.findById(requestId).orElseThrow();
//        User sender = userRepo.findByEmail(principal.getName()).orElseThrow();
//
////        newMessage.setLegalRequest(request);
//        newMessage.setSender(sender);
//        newMessage.setSentAt(LocalDateTime.now());
//
//        messageRepo.save(newMessage);
//        return "redirect:/orders/" + requestId + "/chat";
//    }

    @GetMapping("/chat")
//    public String chatPage(@PathVariable Long recipientId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
    public String chatPage(@RequestParam Long recipientId, Model model, Principal principal) {
        User currentUser = userService.findByEmail(principal.getName());

        model.addAttribute("recipientId", recipientId);
//        model.addAttribute("currentUserId",  userRepository.findByEmail(userDetails.getUsername())); // реалізуйте метод
        model.addAttribute("currentUserId",  currentUser.getId());
        return "chat";
    }

    @MessageMapping("/chat.send.{orderId}")
    public void send(@DestinationVariable Long orderId, MessageDto messageDto, Principal principal) {
        User sender = userRepository.findByEmail(principal.getName()).orElseThrow();

        Message message = new Message();
        message.setSenderId(sender.getId());
        message.setRecipientId(messageDto.getRecipientId()); // або отримай об'єкт користувача, якщо треба
        message.setContent(messageDto.getContent());
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);

        messageDto.setSenderName(sender.getLastName());
        messageDto.setSentAt(LocalDateTime.now().toString());
//        messageDto.setContent(LocalDateTime.now().toString());

        messagingTemplate.convertAndSend("/topic/chat/" + orderId, messageDto);
    }
}