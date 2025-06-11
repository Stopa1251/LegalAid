package com.example.lawtest.repository;

import com.example.lawtest.entity.Lawyer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

//    @Query("SELECT l FROM Lawyer l RIGHT JOIN User u ON l.id = u.id WHERE u.email = :email")
    Optional<Lawyer> findByEmail(String email);

    List<Lawyer> findAll();

//    @EntityGraph(attributePaths = {"specializations"})
//    List<Lawyer> findAllWithSpecializations();

    @Query("SELECT l FROM Lawyer l " +
            "WHERE LOWER(l.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(l.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(l.portfolio) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(l.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Lawyer> search(@Param("keyword") String keyword);


    @Query("SELECT l FROM Lawyer l " +
            "WHERE (LOWER(l.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(l.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(l.portfolio) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:specialization IS NULL OR :specialization member of l.specializations) " +
            "AND (:minRating IS NULL OR l.rating >= :minRating) " +
            "AND (:active IS NULL OR l.active = :active)")
    List<Lawyer> searchWithFilters(
            @Param("keyword") String keyword,
            @Param("specialization") String specialization,
            @Param("minRating") Double minRating,
            @Param("active") Boolean active
    );
}
