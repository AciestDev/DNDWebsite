package com.example.dnd.auth;


import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class UserDAO {


    @PersistenceContext
    private EntityManager em;

    public User findSingleUserByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<User> findUsersByUsername(String username) {

        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    @Transactional
    public void save(String username, String password) {
        User user = new User(username, password);
        em.persist(user);
    }
}
