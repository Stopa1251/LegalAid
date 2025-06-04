package com.example.lawtest.controller;


import com.example.lawtest.entity.Message;
import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.repository.MessageRepository;
import com.example.lawtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT_MANAGER', 'CLIENT')")
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;



    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderRepository.findById(id).orElse(null));
        return "orderDetail";
    }



//    @PreAuthorize("hasRole('ROLE_LAWYER')")
//    @GetMapping("/view/{id}")
//    public String viewOrder(
//            @PathVariable Long id,
//            Model model,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        LegalOrder order = orderRepository.findById(id).orElseThrow();
//        List<Message> messages = messageRepository.findByLegalOrderOrderBySentAtAsc(order);
//        User currentUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
//
//        model.addAttribute("order", order);
//        model.addAttribute("messages", messages);
//        model.addAttribute("newMessage", new Message());
//        model.addAttribute("currentUser", currentUser);
//
//        return "viewOrder";
//    }

//    @PreAuthorize("hasRole('ROLE_LAWYER')")
    @PostMapping("/view/{id}/message")
    public String sendMessage(
            @PathVariable Long id,
            @ModelAttribute("newMessage") Message newMessage,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Order order = orderRepository.findById(id).orElseThrow();
        User sender = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

//        newMessage.setLegalorder(order);
        newMessage.setSenderId(sender.getId());
        newMessage.setSentAt(LocalDateTime.now());

        messageRepository.save(newMessage);
        return "redirect:/orders/view/" + id;
    }

//    @PutMapping("/my/{id}")
//    public void updateOrder(@PathVariable("id") Long id) {
//        orderRepository.updateBy(id);
//    }

}