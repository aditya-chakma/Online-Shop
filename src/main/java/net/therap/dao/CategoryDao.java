package net.therap.dao;

import net.therap.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Repository
public class CategoryDao implements GenericDao<Category> {

    @PersistenceContext
    private EntityManager em;

    private static final String JPQL_FIND_ALL = "FROM Category";

    private static final String JPQL_FIND_BY_NAME = "FROM Category " +
            "WHERE name = :name";

    @Override
    public Category findById(int id) {
        return em.find(Category.class, id);
    }

    public Category findByName(String name) {
        try {
            return em.createQuery(JPQL_FIND_BY_NAME, Category.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery(JPQL_FIND_ALL, Category.class).getResultList();
    }

    @Override
    @Transactional
    public Category saveOrUpdate(Category category) {
        if (category.isNew()) {
            em.persist(category);
            em.flush();

        } else {
            category = em.merge(category);
        }

        return category;
    }
}
