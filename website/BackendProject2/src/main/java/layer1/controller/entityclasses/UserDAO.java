package layer1.controller.entityclasses;

import jakarta.persistence.*;

import java.util.List;

public class UserDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("backendProject2");

    public User findByUsername(String username) {

        try (EntityManager em = emf.createEntityManager()) {
            return em.find(User.class, username);
        }
    }

    public List<User> findUsersByUsername(String username) {

        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultList();
        }
    }

    public void save(User user) {

        EntityManager em = emf.createEntityManager();
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
        } finally {
            em.close();
        }
    }

    public void save(String username, String password) {

        User user = new User(username, password);
        EntityManager em = emf.createEntityManager();
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
        } finally {
            em.close();
        }
    }
}
