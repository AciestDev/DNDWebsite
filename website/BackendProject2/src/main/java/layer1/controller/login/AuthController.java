package layer1.controller.login;

import layer1.controller.entityclasses.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class AuthController {

    private UserDAO userDAO;

    public ResponseEntity<Map<String, String>> login(@RequestParam("username") String username,
                                                     @RequestParam("password") String password) {
        User user = userDAO.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "Login successful!"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error", "message", "Invalid username or password!"));
        }
    }

    public ResponseEntity<Map<String, String>> register(@RequestParam("username") String username,
                                                     @RequestParam("password") String password) {

        User found = userDAO.findByUsername(username);

        if (found != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error", "message", "User already exists!"));
        } else {
            userDAO.save(username, password);
            return ResponseEntity.ok(Map.of("status", "success", "message", "User registered successfully!"));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthController.class, args);
    }
}
