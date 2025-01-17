package com.vishal.journalbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.journalbackend.api.response.WeatherResponse;
import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.dto.UserDTO;
import com.vishal.journalbackend.service.UserService;
import com.vishal.journalbackend.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Read, Update, and Delete User")
public class UserController {

    private final UserService userService;
    private final WeatherService weatherService;

    @Autowired
    public UserController(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
    }

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PutMapping
    @Operation(summary = "Update the user")
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUsername(username);

        if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()
                && !userDTO.getUsername().equals(userInDb.getUsername())) {
            if (userService.existsByUsername(userDTO.getUsername())) {
                throw new IllegalArgumentException("Username '" + userDTO.getUsername() + "' is already taken.");
            }
            userInDb.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            userInDb.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            userInDb.setEmail(userDTO.getEmail());
        }
        if (userDTO.getSentimentAnalysis() != null) {
            userInDb.setSentimentAnalysis(userDTO.getSentimentAnalysis());
        }
        userService.saveUser(userInDb);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Delete the user by its Username")
    public ResponseEntity<Void> deleteUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Greet the user")
    public ResponseEntity<String> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String weather = "";
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        if (weatherResponse != null) {
            weather = " Weather feels like " + weatherResponse.getCurrent().getFeelslike() + " degree Celsius.";
        }

        return ResponseEntity.status(HttpStatus.OK).body("Hi " + username + "!" + weather);
    }

}
