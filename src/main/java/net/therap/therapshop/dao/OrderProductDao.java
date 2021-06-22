package net.therap.therapshop.dao;

import net.therap.therapshop.model.OrderProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/7/21
 */
@Repository
public class OrderProductDao extends Dao {

    public List<OrderProduct> findAllByOrderId(int orderId) {
        return em.createNamedQuery("orderProduct.findAllByOrderId", OrderProduct.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
