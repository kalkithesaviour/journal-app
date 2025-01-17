package com.vishal.journalbackend.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.journalbackend.cache.AppCache;
import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.dto.UserDTO;
import com.vishal.journalbackend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs", description = "Read all users, Create admin, and clear app-cache")
public class AdminController {

    private final UserService userService;
    private final AppCache appCache;

    @Autowired
    public AdminController(UserService userService, AppCache appCache) {
        this.userService = userService;
        this.appCache = appCache;
    }

    @GetMapping("/all-users")
    @Operation(summary = "Retrieve all users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAll();
        if (allUsers != null && !allUsers.isEmpty()) {
            return ResponseEntity.ok(allUsers);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create-admin")
    @Operation(summary = "Create a new Admin")
    public ResponseEntity<String> createAdmin(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setSentimentAnalysis(userDTO.getSentimentAnalysis());
        user.setRoles(Arrays.asList("USER", "ADMIN"));

        boolean isCreated = userService.saveNewUser(user);
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create admin. Please check the input data.");
        }
    }

    @GetMapping("/clear-app-cache")
    @Operation(summary = "Clear the app cache")
    public void clearAppCache() {
        appCache.init();
    }

}
