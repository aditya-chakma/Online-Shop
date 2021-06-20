package net.therap.therapshop.dao;

import net.therap.therapshop.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author aditya.chakma
 * @since 6/6/21
 */
@Repository
public class OrderDao implements GenericDao<Order> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findAll() {
        return em.createQuery("FROM Order", Order.class)
                .getResultList();
    }

    public List<Order> findByUserId(int userId) {
        return em.createQuery("SELECT o FROM Order o WHERE o.user.id = :id", Order.class)
                .setParameter("id", userId)
                .getResultList();
    }

    public Order findById(int id) {
        return em.find(Order.class, id);
    }

    @Override
    @Transactional
    public Order saveOrUpdate(Order order) {
        if (order.isNew()) {
            em.persist(order);
            em.flush();

        } else {
            order = em.merge(order);
        }

        return order;
    }
}
