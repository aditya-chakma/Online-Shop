package net.therap.service;

import net.therap.dao.CartItemDao;
import net.therap.dao.OrderDao;
import net.therap.dao.UserDao;
import net.therap.model.*;
import net.therap.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author aditya.chakma
 * @since 6/7/21
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemDao cartItemDao;

    public Order saveOrUpdate(Order o) {
        if (o.isNew()) {
            o.setCreatedAt(new Date());
        }

        o.setUpdatedAt(new Date());
        return orderDao.saveOrUpdate(o);
    }

    @Transactional
    public Order saveOrUpdateFromCart(User u) {
        Set<CartItem> cartItems = u.getCartItems();
        Set<OrderProduct> orderProducts = new HashSet<>();
        Order order = new Order();
        order.setUser(u);
        double price = 0.0;

        for (CartItem item : cartItems) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            Product product = item.getProduct();
            orderProduct.setProduct(product);

            if (item.getQuantity() > product.getQuantity()) {
                if (product.getQuantity() > 0) {
                    orderProduct.setQuantity(product.getQuantity());
                    product.setQuantity(0);

                } else {
                    continue;
                }

            } else {
                orderProduct.setQuantity(item.getQuantity());
                product.setQuantity(product.getQuantity() - item.getQuantity());
            }

            productService.saveOrUpdate(product);
            orderProducts.add(orderProduct);
            price += item.getQuantity() * item.getProduct().getPrice();
        }

        order.setOrderProducts(orderProducts);
        order.setSubTotal(price);

        for (CartItem item : cartItems) {
            cartItemDao.delete(item.getId());
        }

        u.getCartItems().clear();
        return saveOrUpdate(order);
    }

    public List<Order> findAll() {
        return orderDao.findAll();
    }

    public Order findById(int id) {
        return orderDao.findById(id);
    }

    public List<Order> findByUserId(int userId) {
        return orderDao.findByUserId(userId);
    }
}
