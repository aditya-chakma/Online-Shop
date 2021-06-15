package net.therap.validator;

import net.therap.model.Product;
import net.therap.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * @author al.imran
 * @since 03/06/2021
 */
@Component
public class ProductValidator implements Validator {

    private static final double EPS = 0.0000000001;

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        if (product.isNew() && Objects.nonNull(productService.getProductByName(product.getName()))) {
            errors.rejectValue("name", "product.name",
                    messageSource.getMessage("message.productExist", null, null));
        } else if (Objects.isNull(product.getName())) {
            errors.rejectValue("name", "product.name",
                    messageSource.getMessage("message.invalidProduct", null, null));
        } else if (product.getQuantity() < 0) {
            errors.rejectValue("quantity", "product.quantity",
                    messageSource.getMessage("message.invalidQuantity", null, null));
        } else if (product.getPrice() < EPS) {
            errors.rejectValue("price", "product.price",
                    messageSource.getMessage("message.price", null, null));
        }
    }
}
