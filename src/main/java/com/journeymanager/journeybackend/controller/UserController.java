package com.journeymanager.journeybackend.controller;

import com.journeymanager.journeybackend.dto.UserRequest;
import com.journeymanager.journeybackend.dto.UserResponse;
import com.journeymanager.journeybackend.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsersForCurrentTenant();
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return userService.createUser(request);
    }
}