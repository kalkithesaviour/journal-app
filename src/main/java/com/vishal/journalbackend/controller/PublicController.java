package com.vishal.journalbackend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.service.UserService;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        boolean isCreated = userService.saveNewUser(user, Arrays.asList("USER"));
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with username: '" + user.getUsername() + "' already exists.");
        }
    }

}
