package layer1.controller.entityclasses;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {


    @PersistenceContext
    private EntityManager em;

    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult();
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

    public void save(User user) {

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void save(String username, String password) {

        User user = new User(username, password);
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}
