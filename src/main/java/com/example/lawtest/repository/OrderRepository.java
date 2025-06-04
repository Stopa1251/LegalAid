package com.example.lawtest.repository;

import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClient(User user);

    int countByAssignedLawyer(User lawyer);

    Optional<Order> getOrderById(Long id);
    @Query("SELECT o FROM Order o WHERE o.client = :clientId")
    Optional<Order> getOrderByUserId(@Param("clientId") Long id);

}