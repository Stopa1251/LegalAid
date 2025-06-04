package com.example.lawtest.repository;

import com.example.lawtest.entity.Review;
import com.example.lawtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
