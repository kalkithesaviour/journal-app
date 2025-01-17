package com.vishal.journalbackend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.dto.UserDTO;
import com.vishal.journalbackend.service.CustomUserDetailsService;
import com.vishal.journalbackend.service.UserService;
import com.vishal.journalbackend.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs", description = "Signup, Login, and Health Check")
public class PublicController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public PublicController(UserService userService, AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/health-check")
    @Operation(summary = "Health check of running APIs")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/signup")
    @Operation(summary = "Create a new User")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setSentimentAnalysis(userDTO.getSentimentAnalysis());
        user.setRoles(Arrays.asList("USER"));

        boolean isCreated = userService.saveNewUser(user);
        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with username: '" + user.getUsername() + "' already exists.");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userDTO.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(jwt);
        } catch (Exception e) {
            log.error("Exception occurred while authenticating token: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect username or password!");
        }
    }

}
