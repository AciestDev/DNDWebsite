package com.example.dnd.character.controller;

import com.example.dnd.auth.User;
import com.example.dnd.auth.UserDAO;
import com.example.dnd.character.databaseCommuncation.CharacterDAO;
import com.example.dnd.character.model.Character;
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
    private CharacterDAO characterDAO;

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/my-characters")
    public ResponseEntity<?> getMyCharacters(HttpSession session) {
        Long id = (Long) session.getAttribute("user");

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        User user = userDAO.findById(id);
        List<Character> characters = characterDAO.findByUserId(user.getId());
        return ResponseEntity.ok(characters);
    }
}
