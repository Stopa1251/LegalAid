package com.example.lawtest.service;

import com.example.lawtest.entity.Review;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.ReviewRepository;
import com.example.lawtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    public Review addReview(User sender, User receiver, String comment, int rating) {
        Review review = new Review();

        review.setReceiver(sender);
        review.setSender(receiver);
        review.setComment(comment);
        review.setRating(rating);
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForLawyer(Long lawyerId) {
        return reviewRepository.findByReceiverId(lawyerId);
    }
}
