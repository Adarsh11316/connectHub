package com.adarsh.ConnectHub.service;

import com.adarsh.ConnectHub.dto.LoginRequest;
import com.adarsh.ConnectHub.dto.LoginResponse;
import com.adarsh.ConnectHub.dto.RegisterRequest;
import com.adarsh.ConnectHub.entity.User;
import com.adarsh.ConnectHub.enums.Role;
import com.adarsh.ConnectHub.repository.    UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email alrady exists");
        }
        String encodedPass=passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPass);
        user.setRole(Role.MEMBER);
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new RuntimeException(
                    "Invalid email or password"
            );
        }
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("invalid email or password");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
    }




