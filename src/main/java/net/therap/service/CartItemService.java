package net.therap.service;

import net.therap.dao.CartItemDao;
import net.therap.model.CartItem;
import net.therap.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author al.imran
 * @since 08/06/2021
 */
@Service
public class CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    public CartItem getCartItemById(int id) {
        return cartItemDao.findById(id);
    }

    public CartItem getCartItemByUserIdAndProductId(int userId, int productId) {
        return cartItemDao.findCartItemByUserIdAndProductId(userId, productId);
    }

    public Pair<List<CartItem>, Double> getCartItemByUserId(int userId) {
        double grandTotal = 0.0;
        List<CartItem> cartItems = cartItemDao.findCartItemByUserId(userId);

        for (CartItem cartItem : cartItems) {
            cartItem.setTotal(cartItem.getQuantity() * cartItem.getProduct().getPrice());
            grandTotal += cartItem.getTotal();
        }

        return new Pair<>(cartItems, grandTotal);
    }

    public void saveOrUpdate(CartItem cartItem) {
        if (cartItem.isNew()) {
            CartItem existedCartItem = getCartItemByUserIdAndProductId(
                    cartItem.getUser().getId(), cartItem.getProduct().getId());

            if (Objects.nonNull(existedCartItem)) {
                existedCartItem.setQuantity(existedCartItem.getQuantity() + 1);
                cartItem = existedCartItem;
            } else {
                cartItem.setQuantity(1);
            }
        }

        cartItemDao.saveOrUpdate(cartItem);
    }

    public void remove(int id) {
        cartItemDao.delete(id);
    }
}
