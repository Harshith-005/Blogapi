package com.harshith.blog.domain.service.impl;

import com.harshith.blog.domain.Repository.UserRepository;
import com.harshith.blog.domain.entity.User;
import com.harshith.blog.domain.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User getUserByid(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(()->new EntityNotFoundException("User not found"));
    }
}
