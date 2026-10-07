package com.example.dnd.character.databaseCommuncation;


import com.example.dnd.auth.User;
import com.example.dnd.auth.UserDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.dnd.character.model.Character;

@Repository
public class CharacterDAO {

    private final UserDAO userDAO;
    @PersistenceContext
    private EntityManager em;

    public CharacterDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<Character> findByUserId(Long userId) {
        return em.createQuery(
                        "SELECT c FROM Character c WHERE c.user.id = :userId", Character.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Character findById(Long id) {
        return em.find(Character.class, id);
    }

    @Transactional
    public Character save(Character character) {
        if (character.getId() == null) {
            em.persist(character);
            return character;
        } else {
            return em.merge(character);
        }
    }

    @Transactional
    public Character save(Long userId, String name) {
        Character character = new Character();
        User user = userDAO.findById(userId);
        character.setName(name);
        character.setUser(user);
        if (character.getId() == null) {
            em.persist(character);
            return character;
        } else {
            return em.merge(character);
        }
    }
}
