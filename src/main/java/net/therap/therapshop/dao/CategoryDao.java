package net.therap.therapshop.dao;

import net.therap.therapshop.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Repository
public class CategoryDao extends Dao {

    public Category findById(int id) {
        return super.findById(id, Category.class);
    }

    public List<Category> findAll() {
        return super.findAll("category.findAll", Category.class);
    }

    public Category findByName(String name) {
        try {
            return em.createNamedQuery("category.findByName", Category.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Category saveOrUpdate(Category category) {
        return super.saveOrUpdate(category);
    }
}
