package com.example.dnd.auth;

import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthDTO request, HttpSession session) {
        User user = userDAO.findSingleUserByUsername(request.username());

        if (user != null && user.getPassword().equals(request.password())) {

            session.setAttribute("user", user.getId());
            return ResponseEntity.ok(Map.of("status", "success", "message", "Login successful!"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "Invalid username or password!"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthDTO request, HttpSession session) {
        User found = userDAO.findSingleUserByUsername(request.username());

        if (found != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error", "message", "User already exists!"));
        }
        User newUser = new User(request.username(), request.password());
        userDAO.save(newUser);
        session.setAttribute("user", newUser.getId());
        return ResponseEntity.ok(Map.of("status", "success", "message", "User registered successfully!"));

    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not logged in"));
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
