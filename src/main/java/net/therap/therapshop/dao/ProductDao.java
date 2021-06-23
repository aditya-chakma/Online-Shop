package net.therap.therapshop.dao;

import net.therap.therapshop.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Repository
public class ProductDao extends Dao {

    public List<Product> findAllByName(String name) {
        return em.createNamedQuery("product.findAllByName", Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    public Product findByName(String name) {
        try {
            return em.createNamedQuery("product.findByName", Product.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Product> findProductByCategoryId(int id) {
        return em.createNamedQuery("product.findByCategoryId", Product.class)
                .setParameter("categoryId", id)
                .getResultList();
    }
}
