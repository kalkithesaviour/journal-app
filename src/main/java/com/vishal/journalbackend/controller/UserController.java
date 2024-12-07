package com.vishal.journalbackend.controller;

import java.util.Arrays;

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
import com.vishal.journalbackend.entity.UserDTO;
import com.vishal.journalbackend.service.UserService;
import com.vishal.journalbackend.service.WeatherService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
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
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUsername(username);

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRoles(Arrays.asList("USER"));

        if (user.getUsername() != null && !user.getUsername().isEmpty()
                && !user.getUsername().equals(userInDb.getUsername())) {
            if (userService.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("Username '" + user.getUsername() + "' is already taken.");
            }
            userInDb.setUsername(user.getUsername());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.saveUser(userInDb);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<String> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String weather = "";
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        if (weatherResponse != null) {
            weather = " Weather feels like " + weatherResponse.getCurrent().getFeelslike() + " degrees.";
        }

        return ResponseEntity.status(HttpStatus.OK).body("Hi " + username + "!" + weather);
    }

}
