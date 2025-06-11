package com.example.lawtest.controller;

import com.example.lawtest.entity.Lawyer;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.service.LawyerService;
import com.example.lawtest.service.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.repository.MessageRepository;
import com.example.lawtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'LAWYER')")
@Controller
@RequestMapping
public class LawyerController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private LawyerService lawyerService;


//    @GetMapping("/search")
//    public String searchLawyers(@RequestParam("q") String keyword, Model model) {
//        List<Lawyer> results = lawyerService.searchLawyers(keyword);
//        model.addAttribute("lawyers", results);
//        model.addAttribute("keyword", keyword);
//        return "searchResults"; // Thymeleaf-шаблон
//    }

//    @GetMapping("/lawyers/{id}")
//    public String testEditLawyer(@ModelAttribute User lawyer,
//                                 @RequestParam("image") MultipartFile imageFile) {
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

}