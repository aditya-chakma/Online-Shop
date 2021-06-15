package net.therap.validator;

import net.therap.model.CartItem;
import net.therap.service.CartItemService;
import net.therap.util.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author al.imran
 * @since 13/06/2021
 */
@Component
public class CartItemValidator implements Validator {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CartItem.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CartItem cartItem = (CartItem) target;

        CartItem existedCartItem = cartItemService.
                getCartItemByUserIdAndProductId(cartItem.getUser().getId(), cartItem.getProduct().getId());

        if (!(cartItem.getProduct().getStatus().equals(ProductStatus.IN_STOCK))) {
            errors.rejectValue("id", "cartItem.id",
                    messageSource.getMessage("message.noStock", null, null));
        } else if (cartItem.getProduct().getQuantity() < cartItem.getQuantity()) {
            errors.rejectValue("id", "cartItem.id",
                    messageSource.getMessage("message.quantityExceeded", null, null));
        } else if (Objects.nonNull(existedCartItem) && cartItem.isNew()) {
            if (existedCartItem.getQuantity() + 1 > cartItem.getProduct().getQuantity()) {
                errors.rejectValue("id", "cartItem.id",
                        messageSource.getMessage("message.quantityExceeded", null, null));
            }
        }
    }
}
