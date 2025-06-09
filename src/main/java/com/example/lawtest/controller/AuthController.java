package com.example.lawtest.controller;

import com.example.lawtest.config.CustomAuthenticationSuccessHandler;
import com.example.lawtest.dto.UserLoginDto;
import com.example.lawtest.dto.UserRegistrationDto;
import com.example.lawtest.repository.UserRepository;
import com.example.lawtest.security.JwtAuthenticationResponse;
import com.example.lawtest.security.JwtTokenProvider;
import com.example.lawtest.service.UserService;
import com.example.lawtest.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "register";
    }
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userDto", new UserLoginDto());
        return "login";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute UserRegistrationDto registrationRequest, Model model) {
        try {
            if (userRepository.existsByEmail(registrationRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Email is already in use!");
            }

            userService.register(registrationRequest);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registrationRequest.getEmail(),
                            registrationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}