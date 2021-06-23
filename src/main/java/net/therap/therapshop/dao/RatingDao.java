package net.therap.therapshop.dao;

import net.therap.therapshop.model.Rating;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author al.imran
 * @since 09/06/2021
 */
@Repository
public class RatingDao extends Dao {

    public Rating findByUserIdAndProdutId(int userId, int productId) {
        try {
            return em.createNamedQuery("rating.findByUserAndProductId", Rating.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> findByProductId(int id) {
        return em.createNamedQuery("rating.findByProductId", Object[].class)
                .setParameter("productId", id)
                .getResultList();
    }
}
