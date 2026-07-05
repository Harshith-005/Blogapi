package com.harshith.blog.domain.service.impl;

import com.harshith.blog.domain.Repository.UserRepository;
import com.harshith.blog.domain.entity.User;
import com.harshith.blog.domain.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserByid(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(()->new EntityNotFoundException("User not found"));
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
