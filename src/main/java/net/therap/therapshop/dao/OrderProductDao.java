package net.therap.therapshop.dao;

import net.therap.therapshop.model.OrderProduct;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/7/21
 */
@Repository
public class OrderProductDao {

    @PersistenceContext
    private EntityManager em;

    public List<OrderProduct> findAllByOrderId(int orderId) {
        return em.createQuery("SELECT op FROM OrderProduct op WHERE op.order.id = :id", OrderProduct.class)
                .setParameter("id", orderId)
                .getResultList();
    }
}
