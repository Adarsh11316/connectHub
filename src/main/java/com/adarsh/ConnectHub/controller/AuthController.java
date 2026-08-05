package com.adarsh.ConnectHub.controller;


import com.adarsh.ConnectHub.dto.LoginRequest;
import com.adarsh.ConnectHub.dto.LoginResponse;
import com.adarsh.ConnectHub.dto.RegisterRequest;
import com.adarsh.ConnectHub.entity.User;
import com.adarsh.ConnectHub.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService=userService;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }


}
