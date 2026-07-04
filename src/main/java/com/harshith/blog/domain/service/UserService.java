package com.harshith.blog.domain.service;

import com.harshith.blog.domain.entity.User;

import java.util.UUID;

public interface UserService {
    User getUserByid(UUID uuid);
}
