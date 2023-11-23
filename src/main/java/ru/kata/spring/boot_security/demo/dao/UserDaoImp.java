package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class UserDaoImp implements UserDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        logger.info("saveUser " + user.toString());
        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id) {
        logger.info("getUserById " + id);
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("getAllUsers");
        String hql = "FROM User";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        return query.getResultList();
    }

    @Override
    public User getUserByEmail(String email) {
        logger.info("getUserByEmail " + email);
        String hql = "SELECT u FROM User u join fetch u.roles WHERE u.email =:email";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("email", email);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            logger.info("getUserByEmail: " + e.getMessage() + " " + email);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        logger.info("updateUser " + user);
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(Long id) {
        logger.info("removeUserById " + id);
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public long countUsers() {
        logger.info("countUsers");
        String hql = "SELECT count(u) FROM User u";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        return query.getSingleResult();
    }
}
