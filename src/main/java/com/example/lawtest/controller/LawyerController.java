package com.example.lawtest.controller;

import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.service.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import com.example.lawtest.entity.Order;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.OrderRepository;
import com.example.lawtest.repository.MessageRepository;
import com.example.lawtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT', 'LAWYER')")
@Controller
@RequestMapping("/lawyer")
public class LawyerController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserServiceImpl userService;
//
////    @GetMapping("/reassign/{id}")
//////    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT_MANAGER')")
////    public String showReassignForm(@PathVariable Long id, Model model) {
////        LegalOrder order = orderRepository.findById(id).orElseThrow();
////        List<User> lawyers = userRepository.findUsersByRole(User.Role.ROLE_LAWYER);
////        model.addAttribute("order", order);
////        model.addAttribute("lawyers", lawyers);
////        return "reassignLawyer";
////    }
//
////    @PostMapping("/reassign/{id}")
//    @PostMapping
////    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT_MANAGER')")
//    public String reassignLawyer(@PathVariable Long id, @RequestParam Long lawyerId) {
//        Order order = orderRepository.findById(id).orElseThrow();
//        User lawyer = userRepository.findById(lawyerId).orElseThrow();
//        order.setAssignedLawyer(lawyer);
//        orderRepository.save(order);
//        return "redirect:/orders/reassign/view/" + id;
//    }
//
////    @GetMapping("/reassign/{id}")
//    @GetMapping
////    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT_MANAGER')")
//    public String showReassignForm(@PathVariable Long id, Model model) {
//        Order order = orderRepository.findById(id).orElseThrow();
//
//        int maxActiveOrders = 3; // поріг активних запитів
//        List<User> lawyers = userRepository.findAvailableLawyers(maxActiveOrders);
//
//        User currentLawyer = order.getAssignedLawyer();
//        if (currentLawyer != null && !lawyers.contains(currentLawyer)) {
//            lawyers.add(0, currentLawyer); // додаємо на початок
//        }
//
//        model.addAttribute("order", order);
//        model.addAttribute("lawyers", lawyers);
//        return "reassignLawyer";
//    }
//
////    @GetMapping
//////    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT_MANAGER')")
////    public String showReassignForm(@PathVariable Long id, Model model) {
////        LegalOrder order = orderRepository.findById(id).orElseThrow();
////        List<LawyerWithLoadDto> lawyers = userRepository.findLawyersWithActiveLoad();
////
////        model.addAttribute("order", order);
////        model.addAttribute("lawyers", lawyers);
////        return "reassign-lawyer";
////    }


    @GetMapping("/profile/edit")
    public String showEditProfileForm( @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        User lawyer = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("lawyer", lawyer);
        return "editLawyerProfile";
    }


    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute User lawyer,
                                @AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam(value = "avatar", required = false) MultipartFile avatarFile
    ) throws IOException {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) return "redirect:/login";

        user.setUser(lawyer);


//        if (avatarFile != null && !avatarFile.isEmpty()) {
//            String filename = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
//            Path uploadPath = Paths.get("uploads");
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            avatarFile.transferTo(uploadPath.resolve(filename));
//            user.setProfileImagePath(filename);
//        }

        userRepository.save(user);
        return "redirect:/lawyer/profile";
    }

//
//    @PostMapping("/edit/{id}")
//    public String updateLawyer(@PathVariable Long id, @ModelAttribute User lawyer) {
////        lawyer.setId(id);
//        userRepository.save(lawyer);
//        return "redirect:/lawyer/profile";
//    }


    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("lawyer", user);
        return "lawyerProfile";
    }


//
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
//                                 @RequestParam("image") MultipartFile imageFile) {
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