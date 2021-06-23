package net.therap.therapshop.dao;

import net.therap.therapshop.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/5/21
 */
@Repository
public class UserDao extends Dao {

    public List<User> findByEmail(String email) {
        return em.createNamedQuery("user.byEmail", User.class)
                .setParameter("email", email)
                .getResultList();
    }
}
