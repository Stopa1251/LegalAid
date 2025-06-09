package com.example.lawtest.controller;

import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.Review;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.ReviewRepository;
import com.example.lawtest.repository.UserRepository;
import com.example.lawtest.service.ReviewService;
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
            @PathVariable Long id
    ) throws IOException {
        Review created = reviewService.addReview(review.getSender(), review.getReceiver(), review.getComment(), review.getRating());
        return "redirect:/reviews";
    }

//    @GetMapping("/{receiverId}")
//    public String getReviews(@PathVariable Long receiverId,  @AuthenticationPrincipal UserDetails userDetails, Model model) {
//        return "addReview";
//    }
}


