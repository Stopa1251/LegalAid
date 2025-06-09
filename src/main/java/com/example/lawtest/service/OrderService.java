package com.example.lawtest.service;

import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
//
//    public Order createOrderWithAutoAssign(Order order, User client) {
//        order.setClient(client);
//
//        List<User> lawyers = userRepository.findUsersByRole(User.Role.ROLE_LAWYER);
//
//        User bestCandidate = lawyers.stream()
//                .min(Comparator.comparingInt(lawyer ->
//                        orderRepository.countByAssignedLawyer(lawyer)))
//                .orElse(null);
//
//        order.setAssignedLawyer(bestCandidate);  // може бути null
//        order.setStatus("OPEN");
//
//        return orderRepository.save(order);
//    }






//    public Order createOrderWithAutoAssign(Order order, User client) {
//        order.setClient(client);
//
//        List<User> lawyers = userRepository.findUsersByRole(User.Role.ROLE_LAWYER);
//
//        User bestCandidate = lawyers.stream()
//                .min(Comparator.comparingInt(lawyer ->
//                        orderRepository.countByAssignedLawyer(lawyer)))
//                .orElse(null);
//
//        order.setAssignedLawyer(bestCandidate);  // може бути null
//        order.setStatus("OPEN");
//
////        return orderRepository.save(order);
//        return orderRepository.save(order);
//    }


    public List<Order> getOrdersForLawyer(Long lawyerId) {
        return orderRepository.findOrdersByLawyerIdOrderByCreatedAtDesc(lawyerId);
    }
//
//    public List<Order> getOrdersForLawyer(Long lawyerId) {
//        return orderRepository.findOrdersByLawyerIdOrderBySentAtDesc(lawyerId);
//    }

    public List<Order> getOrdersBetweenLawyerAndClient(Long lawyerId, Long clientId) {
        return orderRepository.findByLawyerIdAndClientId(lawyerId, clientId);
    }
}
