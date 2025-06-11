package com.example.lawtest.service;

import com.example.lawtest.entity.Lawyer;
import com.example.lawtest.repository.LawyerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LawyerService {
    private final LawyerRepository lawyerRepository;

    public LawyerService(LawyerRepository lawyerRepository) {
        this.lawyerRepository = lawyerRepository;
    }

    public List<Lawyer> searchLawyers(String keyword) {
        return lawyerRepository.search(keyword);
    }

    public List<Lawyer> searchLawyers(String keyword, String specialization, Double minRating, Boolean active) {
        return lawyerRepository.searchWithFilters(
                keyword == null || keyword.isBlank() ? null : keyword,
                specialization == null || specialization.isBlank() ? null : specialization,
                minRating,
                active
        );
    }
}