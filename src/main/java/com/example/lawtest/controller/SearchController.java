package com.example.lawtest.controller;

import com.example.lawtest.entity.Lawyer;
import com.example.lawtest.service.SearchIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
//    @Autowired
//    private SearchIndexService lawyerSearchService;
//
//    @GetMapping("/lawyers/search")
//    public String searchLawyers(@RequestParam String q, Model model) {
//        List<Lawyer> results = lawyerSearchService.searchLawyers(q);
//        model.addAttribute("lawyers", results);
//        return "lawyers/search-results";
//    }
}
