package net.therap.therapshop.dao;

import net.therap.therapshop.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/6/21
 */
@Repository
public class OrderDao extends Dao {

    public List<Order> findAll() {
        return super.findAll("order.findAll", Order.class);
    }

    public List<Order> findByUserId(int userId) {
        return em.createNamedQuery("order.findByUserId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Order findById(int id) {
        return super.findById(id, Order.class);
    }

    @Transactional
    public Order saveOrUpdate(Order order) {
        return super.saveOrUpdate(order);
    }
}
