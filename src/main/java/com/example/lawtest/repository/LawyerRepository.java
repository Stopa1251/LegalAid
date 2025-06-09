package com.example.lawtest.repository;

import com.example.lawtest.entity.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

//    @Query("SELECT l FROM Lawyer l RIGHT JOIN User u ON l.id = u.id WHERE u.email = :email")
    Optional<Lawyer> findByEmail(String email);

    List<Lawyer> findAll();
}
