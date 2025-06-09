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

    Object findByClient(User client);



}