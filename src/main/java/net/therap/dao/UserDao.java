package net.therap.dao;

import net.therap.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/5/21
 */
@Repository
public class UserDao implements GenericDao<User> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User findById(int id) {
        return em.find(User.class, id);
    }

    public List<User> findByEmail(String email) {
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<User> findByEmailAndPassword(String email, String password) {
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email and u.hashedPassword = :pass", User.class)
                .setParameter("email", email)
                .setParameter("pass", password)
                .getResultList();
    }

    @Override
    @Transactional
    public User saveOrUpdate(User user) {
        if (user.isNew()) {
            em.persist(user);
            em.flush();
        } else {
            em.merge(user);
        }

        return user;
    }
}
