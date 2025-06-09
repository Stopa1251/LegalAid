package com.example.lawtest.controller;

import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.repository.SpecializationRepository;
import com.example.lawtest.repository.UserRepository;
import com.example.lawtest.service.UserServiceImpl;
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
import java.util.UUID;


@PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'CLIENT')")
@Controller
@RequestMapping("/user")
public class ClientController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SpecializationRepository specializationRepository;


    @GetMapping("/orders/create")
    public String createOrder(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("communicationFormats", Order.CommunicationFormat.values());
        model.addAttribute("specializations", specializationRepository.findAll());
        return "createOrder";
    }

    @PostMapping("/orders/create")
    public String saveOrder(
            @ModelAttribute Order order,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("attachment") MultipartFile file,
            Model model
    ) throws IOException {
        try {
            User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
            order.setClient(user);

            if (!file.isEmpty()) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get("uploads");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                file.transferTo(uploadPath.resolve(filename));
                order.setAttachmentFilename(filename);
            }

            orderRepository.save(order);
            return "redirect:/user/orders";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/user/orders";
        }
    }

    @GetMapping("/orders")
    public String myOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("orders", orderRepository.findByClient(user));
        return "myOrders";
    }

    @GetMapping("/orders/{id}")
    public String myOrderDetails(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Order order = orderRepository.getOrderById(id).orElse(null);
        model.addAttribute("order", order);
        return "myOrderDetail";
    }


    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/orders/{id}/edit")
    public String editOrder(@PathVariable("id") Long id, Model model) {
        model.addAttribute("order", orderRepository.getOrderById(id).orElse(null));
        return "editOrder";
    }

    @PostMapping("/orders/{id}/edit")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id,
//                                         @RequestBody Order order) {
                                         @ModelAttribute Order order) {
        orderRepository.save(order);
        return ResponseEntity.ok(id);
    }



}
