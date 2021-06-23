package net.therap.therapshop.service;

import net.therap.therapshop.dao.CartItemDao;
import net.therap.therapshop.dao.OrderDao;
import net.therap.therapshop.model.*;
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
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    public Order saveOrUpdate(Order order) {
        if (order.isNew()) {
            order.setCreatedAt(new Date());
        }

        order.setUpdatedAt(new Date());
        return orderDao.saveOrUpdate(order);
    }

    @Transactional
    public Order saveOrUpdateFromCart(User user) {
        Set<CartItem> cartItems = user.getCartItems();
        Set<OrderProduct> orderProducts = new HashSet<>();

        Order order = new Order();
        order.setUser(user);

        double price = 0.0;

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            OrderProduct orderProduct = new OrderProduct();

            orderProduct.setOrder(order);
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
            cartItemService.remove(item.getId());
        }

        user.getCartItems().clear();
        return saveOrUpdate(order);
    }

    public List<Order> findAll() {
        return orderDao.findAll("order.findAll", Order.class);
    }

    public Order findById(int id) {
        return orderDao.findById(id, Order.class);
    }

    public List<Order> findByUserId(int userId) {
        return orderDao.findByUserId(userId);
    }
}
