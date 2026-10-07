package com.example.dnd.character.databaseCommuncation;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CharacterDAO {

    @PersistenceContext
    private EntityManager em;

    public List<com.example.dnd.character.model.Character> findByUserId(Long userId) {
        return em.createQuery(
                        "SELECT c FROM Character c WHERE c.user.id = :userId", com.example.dnd.character.model.Character.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Character findById(Long id) {
        return em.find(Character.class, id);
    }

    @Transactional
    public com.example.dnd.character.model.Character save(com.example.dnd.character.model.Character character) {
        if (character.getId() == null) {
            em.persist(character);
            return character;
        } else {
            return em.merge(character);
        }
    }
}
