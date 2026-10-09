package com.example.dnd.auth;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthDTO request, HttpSession session) {
        User user = userDAO.findSingleUserByUsername(request.username());

        if (user != null && user.getPassword().equals(request.password())) {
            session.setAttribute("user", user.getId());
            log.info("Login succeeded for userId={}", user.getId());
            return ResponseEntity.ok(Map.of("status", "success", "message", "Login successful!"));

        } else {
            log.warn("Login failed for username={}", request.username());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "Invalid username or password!"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthDTO request, HttpSession session) {
        User found = userDAO.findSingleUserByUsername(request.username());

        if (found != null) {
            log.warn("Registering failed for username={} since user exists already", request.username());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error", "message", "User already exists!"));
        }
        User newUser = new User(request.username(), request.password());
        userDAO.save(newUser);
        log.info("Register succeeded for userId = {} username={}", newUser.getId(), newUser.getUsername());
        session.setAttribute("user", newUser.getId());
        return ResponseEntity.ok(Map.of("status", "success", "message", "User registered successfully!"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {

        Long userId = (Long) session.getAttribute("user");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not logged in"));
        }

        User user = userDAO.findById(userId);
        if (user == null) {
            log.warn("User was not found using DAO. " +
                    "The user is null making the id = {} valid or nonexistent in database"
                    , userId);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of("message", "Not logged in"));
        }
        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername()
        ));

    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

}
