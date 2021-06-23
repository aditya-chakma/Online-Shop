package net.therap.therapshop.dao;

import net.therap.therapshop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/6/21
 */
@Repository
public class OrderDao extends Dao {

    public List<Order> findByUserId(int userId) {
        return em.createNamedQuery("order.findByUserId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
