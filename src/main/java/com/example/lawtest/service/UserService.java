package com.example.lawtest.service;

import com.example.lawtest.dto.UserRegistrationDto;
import com.example.lawtest.entity.User;

import java.util.List;

public interface UserService {
    void register(UserRegistrationDto dto);

    //    User register(UserRegistrationDto dto);
    User findByEmail(String email);

    List<User> searchUsers(String query);
}