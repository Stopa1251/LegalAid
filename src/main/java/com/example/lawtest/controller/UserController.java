package com.example.lawtest.controller;

import com.example.lawtest.entity.Lawyer;
import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.LawyerRepository;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.repository.UserRepository;
import com.example.lawtest.service.UserService;
import com.example.lawtest.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//@PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'CLIENT')")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (user.getRole() == User.Role.ROLE_CLIENT) {
            model.addAttribute("user", user);
            return "/clientProfile";
        } else if (user.getRole() == User.Role.ROLE_LAWYER) {
            Lawyer lawyer = lawyerRepository.findByEmail(userDetails.getUsername()).orElse(null);
            model.addAttribute("lawyer", lawyer);
            return "/lawyerProfile";
        } else {
            return "redirect:/login";
        }


    }

    @PostMapping("/profile/edit")
    public String updateProfile(
            @ModelAttribute Lawyer user,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile
    ) throws IOException {
        User userSystem = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (userSystem == null) return "redirect:/login";


        if (!userSystem.getEmail().equals(user.getEmail())) {
            userSystem.setEmail(user.getEmail());
        }

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            avatarFile.transferTo(uploadPath.resolve(filename));
            user.setProfileImagePath(filename);
        }

        if (userSystem.getRole() == User.Role.ROLE_CLIENT) {
            userSystem.setUser(user);
            userRepository.save(userSystem);
        }
        else if (userSystem.getRole() == User.Role.ROLE_LAWYER) {
            Lawyer lawyer = lawyerRepository.findByEmail(userDetails.getUsername()).orElse(null);
            if (lawyer == null) return "redirect:/login";
            lawyer.setLawyer(user);
            lawyerRepository.save(lawyer);
        }

        return "redirect:/user/profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);


        if (user.getRole() == User.Role.ROLE_CLIENT) {
            model.addAttribute("user", user);
            return "/editClientProfile";
        } else if (user.getRole() == User.Role.ROLE_LAWYER && user instanceof Lawyer) {
            Lawyer lawyer = lawyerRepository.findByEmail(userDetails.getUsername()).orElse(null);
            model.addAttribute("user", lawyer);
            return "/editLawyerProfile";
        } else {
            return "redirect:/login";
        }
    }


//    @PostMapping("/lawyers/save")
//    public String saveLawyer(@ModelAttribute User lawyer,
//                             @RequestParam("image") MultipartFile imageFile) {
//        try {
//            if (imageFile != null && !imageFile.isEmpty()) {
//                String imagePath = userService.saveProfileImage(imageFile, lawyer.getId());
//                lawyer.setProfileImagePath(imagePath);
//            }
//            userRepository.save(lawyer);
//        } catch (IOException e) {
//            e.printStackTrace(); // обробка
//        }
//        return "redirect:/lawyers";
//    }
//
//    @GetMapping("/lawyers/{id}")
//    public String testEditLawyer(@ModelAttribute User lawyer,
//                             @RequestParam("image") MultipartFile imageFile) {
//        try {
//            if (imageFile != null && !imageFile.isEmpty()) {
//                String imagePath = userService.saveProfileImage(imageFile, lawyer.getId());
//                lawyer.setProfileImagePath(imagePath);
//            }
//            userRepository.save(lawyer);
//        } catch (IOException e) {
//            e.printStackTrace(); // обробка
//        }
//        return "redirect:/lawyers";
//    }
//
//    public void deleteProfileImage(User lawyer) {
//        if (lawyer.getProfileImagePath() != null) {
//            Path path = Paths.get(lawyer.getProfileImagePath().replace("/uploads/", "uploads/"));
//            try {
//                Files.deleteIfExists(path);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}