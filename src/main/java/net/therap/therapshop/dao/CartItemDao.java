package net.therap.therapshop.dao;

import net.therap.therapshop.model.CartItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

/**
 * @author al.imran
 * @since 08/06/2021
 */
@Repository
public class CartItemDao extends Dao {

    public List<CartItem> findCartItemByUserId(int userId) {
        return em.createNamedQuery("cartItem.findByUserId", CartItem.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public CartItem findCartItemByUserIdAndProductId(int userId, int productId) throws NonUniqueResultException {
        try {
            return em.createNamedQuery("cartItem.findByUserAndProductId", CartItem.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
