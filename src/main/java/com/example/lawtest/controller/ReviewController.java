package com.example.lawtest.controller;

import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.Review;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.ReviewRepository;
import com.example.lawtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/{id}/create")
    public String createOrder(Model model) {
        model.addAttribute("order", new Review());
        return "createReview";
    }

    //    @PreAuthorize("hasRole('ROLE_LAWYER')")
    @PostMapping("{id}/create")
    public String saveOrder(
            @ModelAttribute Review review,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) throws IOException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        review.setSender(user);
        review.setReceiver(userRepository.findById(id).orElse(null));

        reviewRepository.save(review);
        return "redirect:/reviews";
    }
}


