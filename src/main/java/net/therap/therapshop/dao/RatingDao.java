package net.therap.therapshop.dao;

import net.therap.therapshop.model.Rating;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author al.imran
 * @since 09/06/2021
 */
@Repository
public class RatingDao implements GenericDao<Rating> {

    private static final String JPQL_FIND = "FROM Rating " +
            "WHERE user_id = :userId AND product_id = :productId";

    private static final String JPQL_AGGREGATE = "SELECT COUNT(r), SUM(r.ratingValue) FROM Rating r " +
            "WHERE product_id = :productId";

    @PersistenceContext
    private EntityManager em;

    public Rating findByUserIdAndProdutId(int userId, int productId) {
        try {
            return em.createQuery(JPQL_FIND, Rating.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> findByProductId(int id) {
        List<Object[]> list = em.createQuery(JPQL_AGGREGATE, Object[].class)
                .setParameter("productId", id)
                .getResultList();

        return list;
    }

    @Override
    @Transactional
    public Rating saveOrUpdate(Rating rating) {
        if (rating.isNew()) {
            em.persist(rating);
            em.flush();

        } else {
            rating = em.merge(rating);
        }

        return rating;
    }
}
