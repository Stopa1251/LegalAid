package com.example.lawtest.repository;

import com.example.lawtest.entity.Review;
import com.example.lawtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.receiver.id = :receiverId")
    List<Review> findByReceiverId(Long receiverId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.receiver.id = :id")
    Double findAverageRatingByReceiverId(Long id);
}
