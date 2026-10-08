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

    @GetMapping("/{id}")
    public ResponseEntity<?> getCharacterById(@PathVariable("id") Long characterId, HttpSession session) {
        Long userId = (Long) session.getAttribute("user");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Character character = characterDAO.findById(characterId);

        if (character == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character not found");
        }


        if (!character.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: You do not own this character");
        }

        return ResponseEntity.ok(character);
    }



    @PostMapping
    public ResponseEntity<?> createCharacter(@RequestBody Character characterData, HttpSession session) {
        Long userId = (Long) session.getAttribute("user");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        characterData.setId(null);

        User user = userDAO.findById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User account not found");
        }

        characterData.setUser(user);

        Character savedCharacter = characterDAO.save(characterData);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCharacter(
            @PathVariable("id") Long characterId,
            @RequestBody Character updatedData,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("user");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Character existingCharacter = characterDAO.findById(characterId);

        if (existingCharacter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character not found");
        }

        if (!existingCharacter.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: You do not own this character");
        }


        existingCharacter.setName(updatedData.getName());
        // TODO Apply future fields here (e.g., class, level, stats)

        Character savedCharacter = characterDAO.save(existingCharacter);
        return ResponseEntity.ok(savedCharacter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCharacter(@PathVariable("id") Long characterId, HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        Character character = characterDAO.findById(characterId);
        if (character == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character not found");
        }

        if (!character.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: You do not own this character");
        }
        characterDAO.delete(characterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
