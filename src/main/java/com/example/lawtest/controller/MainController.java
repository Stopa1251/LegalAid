package com.example.lawtest.controller;

import com.example.lawtest.entity.User;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.repository.UserRepository;
import com.example.lawtest.service.UserService;
import com.example.lawtest.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/orders")
    public String ordersPage(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }

    @GetMapping("/")
    public String homePage() {
        return "home"; // => home.html
    }


    @GetMapping("/lawyers")
    public String lawyersPage(Model model) {
        model.addAttribute("lawyers", userRepository.findAllLawyers());
        return "lawyers";
    }

//    @GetMapping("/lawyers/avatar/{id}")
//    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) {
//        byte[] image = userService.getAvatarById(id);
//        if (image == null) {
//            return ResponseEntity.notFound().build();
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG); // або IMAGE_PNG
//        return new ResponseEntity<>(image, headers, HttpStatus.OK);
//    }
//
//    @PostMapping("/lawyers/avatar/delete/{id}")
//    public String deleteAvatar(@PathVariable Long id) {
//        userService.deleteAvatar(id);
//        return "redirect:/lawyers";
//    }


    @GetMapping("/lawyers/{id}")
    public String showLawyerDetail(Model model, @PathVariable Long id) {
        User lawyer = userRepository.findById(id).orElse(null);
        model.addAttribute("lawyer", lawyer);
        return "/lawyerDetailTest";
    }



}
