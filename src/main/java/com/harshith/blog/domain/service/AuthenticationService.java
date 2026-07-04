package com.harshith.blog.domain.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails Authenticate(String email, String password);
    String generateToken(UserDetails userDetails);
    UserDetails validateToken(String token);
}
