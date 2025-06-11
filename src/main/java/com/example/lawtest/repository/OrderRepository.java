package com.example.lawtest.repository;

import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.Specialization;
import com.example.lawtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientEmail(String email);

    int countByAssignedLawyer(User lawyer);

    Optional<Order> getOrderById(Long id);

    List<Order> getOrdersByPublicView(boolean publicView);


//    @Query("SELECT o FROM Order o WHERE o.client = :clientId")
//    Optional<Order> getOrderByUserId(@Param("clientId") Long id);
//    Optional<Order> getOrderByUserIdDesc(@Param("clientId") Long id);
//    List<Order> getByUserIdAndClientId(Long lawyerId, Long clientId);

//    @Query("SELECT oa.orderId FROM OrderAssigned oa " +
//            "WHERE oa.assignedLawyer.id = :lawyerId " +
//            "ORDER BY oa.orderId.sentAt DESC")
//    List<Order> findOrdersByLawyerIdOrderBySentAtDesc(@Param("lawyerId") Long lawyerId);
//
//    @Query("SELECT oa.orderId FROM OrderAssigned oa " +
//            "WHERE oa.assignedLawyer.id = :lawyerId " +
//            "AND oa.orderId.client = :clientId")
//    List<Order> findByLawyerIdAndClientId(@Param("lawyerId") Long lawyerId, @Param("clientId") Long clientId);


    @Query("SELECT o.id FROM Order o " +
            "WHERE o.assignedLawyer.id = :lawyerId " +
            "ORDER BY o.createdAt DESC")
    List<Order> findOrdersByLawyerIdOrderByCreatedAtDesc(@Param("lawyerId") Long lawyerId);

    @Query("SELECT o.id FROM Order o " +
            "WHERE o.assignedLawyer.id = :lawyerId " +
            "AND o.client.id = :clientId")
    List<Order> findByLawyerIdAndClientId(@Param("lawyerId") Long lawyerId, @Param("clientId") Long clientId);

    List<Order> findByClient(User client);

    @Query("SELECT o FROM Order o " +
            "WHERE (LOWER(o.client.address) LIKE LOWER(CONCAT('%', :address, '%'))) " +
            "AND (o.status = :status) " +
            "AND (LOWER(o.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.client.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(o.subject) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(o.client.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(o.client.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(o.specialization.specializationName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:startDate IS NULL OR o.createdAt >= :startDate) " +
            "AND (:endDate IS NULL OR o.createdAt <= :endDate) " +
            "AND (:specialization IS NULL OR o.specialization.specializationName = :specialization)")
    List<Order> searchOrders(
            @Param("address") String address,
            @Param("status") String status,
            @Param("keyword") String keyword,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("specialization") String specialization
    );

}