package com.harshith.blog.domain.controller;

import com.harshith.blog.domain.dto.AuthResponse;
import com.harshith.blog.domain.dto.LoginRequest;
import com.harshith.blog.domain.dto.RegisterRequest;
import com.harshith.blog.domain.entity.User;
import com.harshith.blog.domain.service.AuthenticationService;
import com.harshith.blog.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> Authenticate(@RequestBody LoginRequest loginRequest) {
//        UserDetails userDetails = authenticationService.Authenticate(loginRequest.getEmail(), loginRequest.getPassword());
//        String token = authenticationService.generateToken(userDetails);
//        AuthResponse response = AuthResponse.builder()
//                .token(token)
//                .expiresIn(86400)
//                .build();
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {

        UserDetails userDetails = authenticationService.Authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword());

        String token = authenticationService.generateToken(userDetails);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .expiresIn(86400)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        userService.createUser(user);

        return ResponseEntity.ok("User registered successfully");
    }
}
