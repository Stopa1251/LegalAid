package com.example.lawtest.repository;

import com.example.lawtest.entity.Role;
import com.example.lawtest.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization,Long>  {
}
