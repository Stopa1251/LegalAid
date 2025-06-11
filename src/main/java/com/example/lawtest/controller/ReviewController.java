package com.example.lawtest.controller;

import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.Review;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.ReviewRepository;
import com.example.lawtest.repository.UserRepository;
import com.example.lawtest.service.ReviewService;
import com.example.lawtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{receiverId}/create")
    public String createOrder(Model model) {
        model.addAttribute("review", new Review());
        return "createReview";
    }

    //    @PreAuthorize("hasRole('ROLE_LAWYER')")
    @PostMapping("{receiverId}/create")
    public String saveOrder(
            @ModelAttribute Review review,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long receiverId
    ) throws IOException {
        System.out.println(userService.findByEmail(userDetails.getUsername()).getLastName());
        System.out.println(userRepository.getUsersById(receiverId).getLastName());
        System.out.println(review.getComment());
        System.out.println(review.getRating());
        Review created = reviewService.addReview(userService.findByEmail(userDetails.getUsername()), userRepository.getUsersById(receiverId), review.getComment(), review.getRating());
        return "redirect:/reviews";
    }

//    @GetMapping("/{receiverId}")
//    public String getReviews(@PathVariable Long receiverId,  @AuthenticationPrincipal UserDetails userDetails, Model model) {
//        return "addReview";
//    }
}


