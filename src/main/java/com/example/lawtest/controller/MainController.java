package com.example.lawtest.controller;

import com.example.lawtest.entity.Client;
import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.Review;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.*;
import com.example.lawtest.service.UserService;
import com.example.lawtest.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private LawyerRepository lawyerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @GetMapping("/orders")
    public String ordersPage(Model model) {
        model.addAttribute("orders", orderRepository.getOrdersByPublicView(true));
        return "orders";
    }

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
//        model.addAttribute("user", user);

//        if(user != null) {
//            model.addAttribute("role", user.getRole());
//        } else {
//            model.addAttribute("role", null);
//        }

        return "home1"; // => home.html
    }


    @GetMapping("/lawyers")
    public String lawyersPage(Model model) {
        model.addAttribute("lawyers", lawyerRepository.findAll());
        return "lawyers";
    }

//    @GetMapping("/lawyers/avatar/{id}")
//    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) {
//        byte[] image = userService.getAvatarById(id);
//        if (image == null) {
//            return ResponseEntity.notFound().build();
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG); // або IMAGE_PNG
//        return new ResponseEntity<>(image, headers, HttpStatus.OK);
//    }
//
//    @PostMapping("/lawyers/avatar/delete/{id}")
//    public String deleteAvatar(@PathVariable Long id) {
//        userService.deleteAvatar(id);
//        return "redirect:/lawyers";
//    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT_MANAGER', 'ADMIN', 'CLIENT')")
    @GetMapping("/lawyers/{id}")
    public String showLawyerDetail(Model model, @PathVariable Long id,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        User lawyer = userRepository.findById(id).orElse(null);
        model.addAttribute("lawyer", lawyer);
        List<Order> orders = orderRepository.findByClientEmail(userDetails.getUsername());
        List<Review> reviews = reviewRepository.findByReceiverId(id);
        Double averageRating = reviewRepository.findAverageRatingByReceiverId(id);

        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("review", new Review());


        model.addAttribute("orders", orders);
        model.addAttribute("order", new Order());
        model.addAttribute("communicationFormats", Order.CommunicationFormat.values());
        model.addAttribute("specializations", specializationRepository.findAll());
        return "/lawyerDetailTest";
    }
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT_MANAGER', 'ADMIN', 'CLIENT')")
//    @GetMapping("/lawyers/{id}")
//    public String showLawyerDetail(Model model, @PathVariable Long id,
//                                   @AuthenticationPrincipal UserDetails userDetails) {
//        User lawyer = userRepository.findById(id).orElse(null);
//        model.addAttribute("lawyer", lawyer);
//        List<Order> orders = orderRepository.findByClientEmail(userDetails.getUsername());
//        model.addAttribute("orders", orders);
//        model.addAttribute("order", new Order());
//        model.addAttribute("communicationFormats", Order.CommunicationFormat.values());
//        model.addAttribute("specializations", specializationRepository.findAll());
//        return "/lawyerDetailTest";
//    }





}
