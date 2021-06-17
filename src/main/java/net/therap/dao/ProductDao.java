package net.therap.dao;

import net.therap.model.Product;
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
public class ProductDao implements GenericDao<Product> {

    @PersistenceContext
    private EntityManager em;

    private static final String JPQL_FIND_ALL = "FROM Product";

    private static final String JPQL_FIND_ALL_BY_NAME = "FROM Product " +
            "WHERE name LIKE :name";

    private static final String JPQL_FIND_BY_NAME = "FROM Product " +
            "WHERE name = :name";

    private static final String JPQL_FIND_BY_CATEGORY_ID = "FROM Product " +
            "WHERE category_id = :category_id";

    @Override
    public Product findById(int id) {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        return em.createQuery(JPQL_FIND_ALL, Product.class).getResultList();
    }

    public List<Product> findAllByName(String name) {
        return em.createQuery(JPQL_FIND_ALL_BY_NAME, Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    public Product findByName(String name) {
        try {
            return em.createQuery(JPQL_FIND_BY_NAME, Product.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Product> findProductByCategoryId(int id) {
        return em.createQuery(JPQL_FIND_BY_CATEGORY_ID, Product.class)
                .setParameter("category_id", id)
                .getResultList();
    }

    @Override
    @Transactional
    public Product saveOrUpdate(Product product) {
        if (product.isNew()) {
            em.persist(product);
            em.flush();

        } else {
            product = em.merge(product);
        }

        return product;
    }
}
