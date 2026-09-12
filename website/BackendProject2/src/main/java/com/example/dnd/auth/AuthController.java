package com.example.dnd.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthDTO request) {
        User user = userDAO.findSingleUserByUsername(request.username());

        if (user != null && user.getPassword().equals(request.password())) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "Login successful!"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "Invalid username or password!"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthDTO request) {
        User found = userDAO.findSingleUserByUsername(request.username());

        if (found != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error", "message", "User already exists!"));
        } else {
            userDAO.save(request.username(), request.password());
            return ResponseEntity.ok(Map.of("status", "success", "message", "User registered successfully!"));
        }
    }
}
