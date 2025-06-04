package com.example.lawtest.controller;

import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.repository.UserRepository;
import com.example.lawtest.service.UserService;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'CLIENT')")
@Controller
@RequestMapping("/client")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("user", user);
        return "/clientProfile";
    }
    @PostMapping("/profile/edit")
    public String updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile
    ) throws IOException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) return "redirect:/login";

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        // Завантаження аватара
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            avatarFile.transferTo(uploadPath.resolve(filename));
            user.setProfileImagePath(filename);
        }

        userRepository.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("user", user);
        return "editProfile";
    }



//    @PostMapping("/lawyers/save")
//    public String saveLawyer(@ModelAttribute User lawyer,
//                             @RequestParam("image") MultipartFile imageFile) {
//        try {
//            if (imageFile != null && !imageFile.isEmpty()) {
//                String imagePath = userService.saveProfileImage(imageFile, lawyer.getId());
//                lawyer.setProfileImagePath(imagePath);
//            }
//            userRepository.save(lawyer);
//        } catch (IOException e) {
//            e.printStackTrace(); // обробка
//        }
//        return "redirect:/lawyers";
//    }
//
//    @GetMapping("/lawyers/{id}")
//    public String testEditLawyer(@ModelAttribute User lawyer,
//                             @RequestParam("image") MultipartFile imageFile) {
//        try {
//            if (imageFile != null && !imageFile.isEmpty()) {
//                String imagePath = userService.saveProfileImage(imageFile, lawyer.getId());
//                lawyer.setProfileImagePath(imagePath);
//            }
//            userRepository.save(lawyer);
//        } catch (IOException e) {
//            e.printStackTrace(); // обробка
//        }
//        return "redirect:/lawyers";
//    }
//
//    public void deleteProfileImage(User lawyer) {
//        if (lawyer.getProfileImagePath() != null) {
//            Path path = Paths.get(lawyer.getProfileImagePath().replace("/uploads/", "uploads/"));
//            try {
//                Files.deleteIfExists(path);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }







    @GetMapping("/orders/create")
    public String createOrder(Model model) {
        model.addAttribute("order", new Order());
        return "createOrder";
    }

    //    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT_MANAGER', 'CLIENT')")
    @PostMapping("/orders/create")
    public String saveOrder(
            @ModelAttribute Order order,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("attachment") MultipartFile file
    ) throws IOException {
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
        return "redirect:/orders";
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