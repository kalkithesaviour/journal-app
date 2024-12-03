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

import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.getAll();
        if (allUsers != null && !allUsers.isEmpty()) {
            return ResponseEntity.ok(allUsers);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestBody User user) {
        boolean isCreated = userService.saveNewUser(user, Arrays.asList("USER", "ADMIN"));
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create admin. Please check the input data.");
        }
    }

}
