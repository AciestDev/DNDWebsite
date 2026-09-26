package com.example.dnd.characterCreation;

import com.example.dnd.auth.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    @Autowired
    //private CharacterDAO characterDAO;

    @GetMapping("/my-characters")
    public ResponseEntity<?> getMyCharacters(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

       // List<Character> characters = characterDAO.findByUserId(user.getId());
        //return ResponseEntity.ok(characters);
        return null;
    }
}
