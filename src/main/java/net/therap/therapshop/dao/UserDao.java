package net.therap.therapshop.dao;

import net.therap.therapshop.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/5/21
 */
@Repository
public class UserDao extends Dao {

    public User findById(int id) {
        return super.finById(id, User.class);
    }

    public List<User> findByEmail(String email) {
        return em.createNamedQuery("user.byEmail", User.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Transactional
    public User saveOrUpdate(User user) {
        return super.saveOrUpdate(user);
    }
}
