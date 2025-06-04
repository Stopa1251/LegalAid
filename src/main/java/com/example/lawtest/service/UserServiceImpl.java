package com.example.lawtest.service;

import com.example.lawtest.dto.UserRegistrationDto;
import com.example.lawtest.entity.User;
import com.example.lawtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegistrationDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(User.Role.valueOf(dto.getRole()));

        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> searchUsers(String query) {
        return List.of();
    }

//    public byte[] getAvatarById(Long id) {
//        User user = userRepository.findById(id).orElse(null);
//        if (user == null || user.getAvatar() == null) return null;
//        return user.getAvatar();
//    }
//
//    public void deleteAvatar(Long id) {
//        User user = userRepository.findById(id).orElse(null);
//        if (user != null) {
//            user.setAvatar(null);
//            userRepository.save(user);
//        }
//    }

    public List<User> getAllLawyers() {
        return userRepository.findAllWithSpecializations();
    }


    //

    public String saveProfileImage(MultipartFile file, Long lawyerId) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String uploadsDir = "uploads/lawyers/";
        File uploadPath = new File(uploadsDir);
        if (!uploadPath.exists()) uploadPath.mkdirs();

        String fileName = "lawyer_" + lawyerId + "_" + UUID.randomUUID() + "." +
                FilenameUtils.getExtension(file.getOriginalFilename());
        Path filePath = Paths.get(uploadsDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/lawyers/" + fileName;
    }
}