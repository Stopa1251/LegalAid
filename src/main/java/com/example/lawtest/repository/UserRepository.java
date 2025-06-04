package com.example.lawtest.repository;

import com.example.lawtest.dto.LawyerWithLoadDto;
import com.example.lawtest.entity.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

//    List<User> findByRole(User.Role role, Limit limit);
    List<User> findUsersByRole(User.Role role);

    @Query("SELECT u FROM User u WHERE u.role = 'ROLE_LAWYER' AND " +
            "(SELECT COUNT(r) FROM Order r WHERE r.assignedLawyer = u AND r.status = 'ACTIVE') < :maxActive")
    List<User> findAvailableLawyers(@Param("maxActive") int maxActive);

    @Query("SELECT u FROM User u WHERE u.role = 'ROLE_LAWYER'")
    List<User> findAllLawyers();

//    findByRole("LAWYER")

//    @Query("SELECT new com.example.lawtest.dto.LawyerWithLoadDto(u.id, u.lastName, COUNT(r)) " +
//            "FROM User u LEFT JOIN Orders r ON r.assignedLawyer = u AND r.status = 'ACTIVE' " +
//            "WHERE u.role = 'ROLE_LAWYER' " +
//            "GROUP BY u.id, u.lastName")
//    List<LawyerWithLoadDto> findLawyersWithActiveLoad();

    @Query("SELECT l FROM User l JOIN FETCH l.specializations WHERE l.role = 'ROLE_LAWYER'")
    List<User> findAllWithSpecializations();
}