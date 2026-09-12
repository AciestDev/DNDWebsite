package com.example.dnd.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("username") String username,
                                                     @RequestParam("password") String password) {
        User user = userDAO.findSingleUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "Login successful!"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error", "message", "Invalid username or password!"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestParam("username") String username,
                                                     @RequestParam("password") String password) {
        User found = userDAO.findSingleUserByUsername(username);

        if (found != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error", "message", "User already exists!"));
        } else {
            userDAO.save(username, password);
            return ResponseEntity.ok(Map.of("status", "success", "message", "User registered successfully!"));
        }
    }
}
