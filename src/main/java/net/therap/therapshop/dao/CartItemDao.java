package net.therap.therapshop.dao;

import net.therap.therapshop.model.CartItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author al.imran
 * @since 08/06/2021
 */
@Repository
public class CartItemDao implements GenericDao<CartItem> {

    private static final String JPQL_FIND = "FROM CartItem " +
            "WHERE user_id = :userId AND product_id = :productId";

    private static final String JPQL_FIND_BY_USER_ID = "FROM CartItem " +
            "WHERE user_id = :userId";

    @PersistenceContext
    private EntityManager em;

    @Override
    public CartItem findById(int id) {
        return em.find(CartItem.class, id);
    }

    public List<CartItem> findCartItemByUserId(int userId) {
        return em.createQuery(JPQL_FIND_BY_USER_ID, CartItem.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public CartItem findCartItemByUserIdAndProductId(int userId, int productId) {
        try {
            return em.createQuery(JPQL_FIND, CartItem.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public CartItem saveOrUpdate(CartItem cartItem) {
        if (cartItem.isNew()) {
            em.persist(cartItem);
            em.flush();

        } else {
            cartItem = em.merge(cartItem);
        }

        return cartItem;
    }

    @Override
    @Transactional
    public void delete(int id) {
        CartItem cartItem = findById(id);
        em.remove(cartItem);
    }
}
