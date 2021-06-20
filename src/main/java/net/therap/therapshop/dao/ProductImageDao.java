package net.therap.therapshop.dao;

import net.therap.therapshop.model.ProductImage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author al.imran
 * @since 05/06/2021
 */
@Repository
public class ProductImageDao implements GenericDao<ProductImage> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ProductImage findById(int id) {
        return em.find(ProductImage.class, id);
    }

    @Override
    @Transactional
    public ProductImage saveOrUpdate(ProductImage productImage) {
        if (productImage.isNew()) {
            em.persist(productImage);
            em.flush();

        } else {
            productImage = em.merge(productImage);
        }

        return productImage;
    }

    @Override
    @Transactional
    public void delete(int id) {
        ProductImage productImage = findById(id);
        em.remove(productImage);
    }
}
