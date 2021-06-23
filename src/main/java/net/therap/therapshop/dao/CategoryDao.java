package net.therap.therapshop.dao;

import net.therap.therapshop.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Repository
public class CategoryDao extends Dao {

    public Category findByName(String name) {
        try {
            return em.createNamedQuery("category.findByName", Category.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
